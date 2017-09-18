package pt.isel.ls.academicActivities.commands.academicClass;

import static org.junit.Assert.*;
import static pt.isel.ls.academicActivities.commands.CommandTests.clearDatabase;
import static pt.isel.ls.academicActivities.commands.CommandTests.rebootDatabase;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.isel.ls.academicActivities.database.DatabaseConnection;
import pt.isel.ls.academicActivities.engine.*;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.ConsoleResponse;

public class AcademicClassTests {
    private static Routes routes;
    private static SQLServerDataSource db;
    private Connection con;

    @BeforeClass
    public static void initClass() throws Exception {
        db = DatabaseConnection.createTestSQLServerDataSourceInstance();
        routes = Routes.create();
        clearDatabase(db);
    }

    @Before
    public void init() {
        try {
            rebootDatabase(db);
            con = db.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetClassesOfCourse() throws Exception {
        Request req = new Request("GET","/courses/LS/classes");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Class expected = new Class("41N","LS","1617","summer");

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Class result = (Class)actual.get(1);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testGetClassesOfCourseOnSemester() throws Exception {
        Request req = new Request("GET","/courses/LS/classes/1617v");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Class expected = new Class("41D","LS","1617","summer");

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Class result = (Class)actual.get(0);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testGetSpecificClassOfCourse() throws Exception {
        Request req = new Request("GET","/courses/LS/classes/1617v/41D");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Class expected = new Class("41D","LS","1617","summer");

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
       IEntity actual = response.getQueryResult().getEntity();

        Class result =(Class)actual;
        assertTrue(expected.equals(result));
    }

    @After
    public void endTest() {
        try {
            con.close();
            clearDatabase(db);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
}
