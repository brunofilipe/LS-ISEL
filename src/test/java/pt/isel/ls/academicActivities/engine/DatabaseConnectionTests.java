package pt.isel.ls.academicActivities.engine;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.junit.*;
import pt.isel.ls.academicActivities.database.DatabaseConnection;

import java.sql.Connection;

public class DatabaseConnectionTests {
    private static SQLServerDataSource db;
    private static Connection con;

    @Test
    public void test_if_Con_of_testDatabase_is_not_null_if_connected() throws SQLServerException {
        db = DatabaseConnection.createTestSQLServerDataSourceInstance();
        con = db.getConnection();
        Assert.assertNotNull(con);
    }

    @Test
    public void test_if_Con_of_Database_is_not_null_if_connected() throws SQLServerException {
        db = DatabaseConnection.createSQLServerDataSourceInstance();
        con = db.getConnection();
        Assert.assertNotNull(con);
    }
}
