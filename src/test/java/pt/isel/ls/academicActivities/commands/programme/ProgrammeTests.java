package pt.isel.ls.academicActivities.commands.programme;

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
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.ConsoleResponse;

public class ProgrammeTests {
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
    public void testGetAllProgrammes() throws Exception {
        Request req = new Request("GET","/programmes");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        Programme expected = new Programme("Licenciatura Engenharia Informatica e Computadores","LEIC",6);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();

        Programme res = (Programme) actual.get(0);
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetSpecificProgramme() throws Exception {
        Request req = new Request("GET","/programmes/LEIC");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        Programme expected = new Programme("Licenciatura Engenharia Informatica e Computadores","LEIC",6);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        IEntity actual = response.getQueryResult().getEntity();

        Programme res = (Programme) actual;
        assertTrue(expected.equals(res));
    }

    @Test
    public void testGetProgrammeCourses() throws Exception {
        Request req = new Request("GET","/programmes/LEIC/courses");
        Parameters parameters = Parameters.create("");
        RouteInfo routeInfo = routes.getRoute(req).get();
        parameters.addParams(routeInfo.getVariablePaths());
        ProgrammeStructure expected = new ProgrammeStructure("LS","LEIC",4,true);

        ConsoleResponse response = ConsoleResponse.emptyResponse();
        routeInfo.getCommand().execute(new ExecutionContext(parameters, con, true),response);
        List<IEntity> actual = response.getQueryResult().getEntities();
        ProgrammeStructure res = (ProgrammeStructure) actual.get(1);
        assertTrue(expected.equals(res));
    }

    @After
    public void endTest() {
        try {
            clearDatabase(db);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
}