package pt.isel.ls.academicActivities.commands.teacher;

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
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.ConsoleResponse;

public class TeacherTests {
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
    public void testGetClassesTaughtByTeacher() throws Exception {
        Request req = new Request("GET","/teachers/1/classes");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Class expected = new Class("41D","LS","1617","summer");

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Class res = (Class) actual.get(0);
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetTeachersOfClass() throws Exception {
        Request req = new Request("GET","/courses/LS/classes/1617v/41D/teachers");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Teacher expected = new Teacher("Jose Manuel","zemanuel@cc.isel.pt",1);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Teacher res = (Teacher) actual.get(0);
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