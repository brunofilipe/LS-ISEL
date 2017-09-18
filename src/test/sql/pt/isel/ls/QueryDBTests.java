package pt.isel.ls;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class QueryDBTests {
    private static SQLServerDataSource db;
    private static Connection con;

    @BeforeClass
    public static void init(){
        db = new SQLServerDataSource();
        db.setUser("sa");
        db.setPassword("isel");
        db.setInstanceName("MSSQLSERVER");
        db.setDatabaseName("Students");
        db.setServerName("DESKTOP-417ERI1");
    }

    @Before
    public void startCon() throws SQLException {
        con = db.getConnection();
        con.setAutoCommit(false);
    }

    @Test
    public void testCon_is_not_null_if_connected() {
        assertNotNull(con);
    }

    @Test
    public void insertStudent_returns_true_if_insert_successfully() throws SQLException {
        boolean std = QueryDB.insertStudent(con,4444, "Ze Manuel", 1);
        assertEquals(true,std);
    }

    @Test
    public void updateStudent_returns_true_if_update_successfully() throws SQLException {
        boolean std = QueryDB.updateStudent(con,41529,4);
        assertEquals(true,std);
    }

    @Test
    public void readStudent_returns_name_if_found_id() throws SQLException {
        String std = QueryDB.readStudent(con,41484);
        assertEquals("Bruno Filipe",std);
    }

    @Test
    public void removeStudent_returns_name_if_removed_successfully() throws SQLException {
        String std = QueryDB.removeStudent(con,41893);
        assertEquals("Joao Gameiro", std);
    }
}
