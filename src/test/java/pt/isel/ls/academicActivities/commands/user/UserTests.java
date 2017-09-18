package pt.isel.ls.academicActivities.commands.user;

import static org.junit.Assert.*;
import static pt.isel.ls.academicActivities.commands.CommandTests.clearDatabase;
import static pt.isel.ls.academicActivities.commands.CommandTests.rebootDatabase;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.isel.ls.academicActivities.database.DatabaseConnection;
import pt.isel.ls.academicActivities.engine.*;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.ConsoleResponse;

public class UserTests {
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
    public void testGetAllStudents() throws Exception {
        Request req = new Request("GET","/students");
        Parameters parameters =Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        Student expected = new Student("Bruno Filipe","brunofilipe@alunos.isel.pt", 41484, "LEIC");

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Student res = (Student) actual.get(1);
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetAllTeachers() throws Exception{
        Request req = new Request("GET","/teachers");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        Teacher expected = new Teacher("Andre Sousa","andresousa@cc.isel.pt", 4);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Teacher res = (Teacher) actual.get(3);
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetSpecificStudent() throws Exception{
        Request req = new Request("GET","/students/41484");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Student expected = new Student("Bruno Filipe","brunofilipe@alunos.isel.pt", 41484, "LEIC");
        List<IEntity> classes = new LinkedList<>();
        classes.add(new Class("41D", "LS", "1617", "summer"));
        expected.setAttendingClasses(classes);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        IEntity actual = response.getQueryResult().getEntity();

        Student res = (Student) actual;
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetSpecificTeacher() throws Exception{
        Request req = new Request("GET","/teachers/4");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Teacher expected = new Teacher("Andre Sousa","andresousa@cc.isel.pt", 4);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        IEntity actual = response.getQueryResult().getEntity();

        Teacher res = (Teacher) actual;
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetUsers() throws Exception{
        Request req = new Request("GET","/users");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        Student expected = new Student("Joao Gameiro","joaogameiro@alunos.isel.pt", 41893, "LEIC");

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Student res = (Student) actual.get(3);
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
