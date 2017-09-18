package pt.isel.ls.academicActivities.app;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.academicActivities.database.DatabaseConnection;
import pt.isel.ls.academicActivities.engine.*;
import pt.isel.ls.academicActivities.exceptions.AcademicActivitiesException;
import pt.isel.ls.academicActivities.exceptions.PathException;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.ConsoleResponse;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Scanner;

public class AcademicActivitiesApp {
    private static Routes routes = Routes.create();
    private static SQLServerDataSource sqlServerDataSourceInstance = DatabaseConnection.createSQLServerDataSourceInstance();
    private static Connection connection = null;
    private static Parameters parameters = null;
    private static Request request = null;

    private static void run(String[] args) throws Exception {
        try {
            request = new Request(args[0], args[1]);
            parameters = Parameters.create(args);
            RouteInfo routeInfo = routes.getRoute(request).orElseThrow(PathException::new);
            parameters.addParams(routeInfo.getVariablePaths());
            connection = DatabaseConnection.createConnection(sqlServerDataSourceInstance);
            ExecutionContext executionContext = new ExecutionContext(parameters, connection, true);
            ConsoleResponse response = ConsoleResponse.emptyResponse();
            routeInfo.getCommand().execute(executionContext, response);
            response.processView();
        } catch (AcademicActivitiesException e) {
            if (connection != null)
                connection.rollback();
            System.out.println(e.getMessage());
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    private static void runAppIfValidArguments(String[] arguments) throws Exception {
        if (arguments.length >= 2 && !Arrays.asList(arguments).contains("")) {
            run(arguments);
        } else
            System.out.println("incorrect usage, please try: {method} {path} {headers} {parameters}");
    }

    private static void interactiveMode() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Please insert a command:(type OPTION / for a list of the available commands)");
        String input = "";
        while (true) {
            input = in.nextLine();
            String[] arguments = input.split(" ");
            runAppIfValidArguments(arguments);
            System.out.println("\nPlease input another command:(type OPTION / for a list of the available commands)");
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0)
                interactiveMode();
            else
                runAppIfValidArguments(args);
        } catch (Exception e) {
            System.out.println("A critical error occurred");
            e.printStackTrace();
        }
    }

    public static Routes getRoutes() {
        return routes;
    }
}
