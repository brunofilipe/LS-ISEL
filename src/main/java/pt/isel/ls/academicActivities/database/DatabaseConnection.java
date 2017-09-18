package pt.isel.ls.academicActivities.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.academicActivities.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    @SuppressWarnings("unchecked")
    public static SQLServerDataSource createSQLServerDataSourceInstance() {
        SQLServerDataSource db = new SQLServerDataSource();
        db.setUser(System.getenv("LS_DB_USER"));
        db.setPassword(System.getenv("LS_DB_PW"));
        db.setDatabaseName(System.getenv("LS_DB_DATABASE"));
        db.setServerName(System.getenv("LS_DB_SERVER"));
        return db;
    }

    public static SQLServerDataSource createTestSQLServerDataSourceInstance() {
        SQLServerDataSource db = new SQLServerDataSource();
        db.setUser(System.getenv("LS_DB_USER"));
        db.setPassword(System.getenv("LS_DB_PW"));
        db.setDatabaseName(System.getenv("LS_DB_DATABASE_TEST"));
        db.setServerName(System.getenv("LS_DB_SERVER"));
        return db;
    }

    public static Connection createConnection(SQLServerDataSource sqlServerDataSource) throws DatabaseException {
        try {
            Connection res = sqlServerDataSource.getConnection();
            res.setAutoCommit(false);
            return res;
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }
}
