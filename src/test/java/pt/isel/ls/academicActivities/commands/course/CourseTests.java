package pt.isel.ls.academicActivities.commands.course;

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
import pt.isel.ls.academicActivities.exceptions.PathException;
import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.ConsoleResponse;

public class CourseTests {
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
    public void testGetAllCourses() throws Exception {
        Request req = new Request("GET","/courses");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).orElseThrow(PathException::new);
        Course expected = new Course("Ambientes Virtuais de Execução","AVE",2);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Course res = (Course) actual.get(0);
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetSpecificCourse() throws Exception {
        Request req = new Request("GET","/courses/LS");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).orElseThrow(PathException::new);
        parameters.addParams(routeInfo.getVariablePaths());
        Course expected = new Course("Laboratorio de Software","LS",1);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        IEntity actual = response.getQueryResult().getEntity();

        Course res = (Course) actual;
        assertTrue(expected.equals(res));
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
