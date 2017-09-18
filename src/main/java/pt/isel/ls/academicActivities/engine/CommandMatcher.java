package pt.isel.ls.academicActivities.engine;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.database.DatabaseConnection;
import pt.isel.ls.academicActivities.exceptions.*;
import pt.isel.ls.academicActivities.request.Request;
import pt.isel.ls.academicActivities.response.HttpResponse;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.utils.http.FormUrlEncoded;
import pt.isel.ls.academicActivities.utils.http.HttpStatusCode;
import pt.isel.ls.academicActivities.view.ErrorView;
import pt.isel.ls.academicActivities.view.HomeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static pt.isel.ls.academicActivities.utils.http.HttpStatusCode.*;

public class CommandMatcher {
    private final Routes routes;
    private static SQLServerDataSource sqlServerDataSourceInstance = DatabaseConnection.createSQLServerDataSourceInstance();
    private static Connection connection;
    private static final Logger _logger = LoggerFactory.getLogger(CommandMatcher.class);

    public CommandMatcher(Routes routes) {
        this.routes = routes;
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            IResponse response = new HttpResponse(resp);
            if (req.getRequestURI().equals("/")) {
                resp.setStatus(Ok.valueOf());
                response.setBody(new HomeView());
                response.processView();
                return;
            }
            String params = "";
            if (FormUrlEncoded.canRetriveFrom(req)) {
                params = FormUrlEncoded.retrieveFrom(req);
            }
            Parameters parameters = Parameters.create(params);
            Request request = new Request(req.getMethod(), req.getRequestURI());
            RouteInfo routeInfo = routes.getRoute(request).orElseThrow(PathException::new);
            parameters.addParams(routeInfo.getVariablePaths());
            connection = DatabaseConnection.createConnection(sqlServerDataSourceInstance);
            routeInfo.getCommand().execute(new ExecutionContext(parameters, connection, false), response);
            if(req.getMethod().equals("POST")) {
                response.setStatusCode(SeeOther.valueOf());
                response.redirect();
            }else{
                response.setStatusCode(Ok.valueOf());
                response.processView();
            }

        } catch (AcademicActivitiesException e) {
            _logger.info("An app exception was thrown: " + e.getMessage());
            HttpStatusCode status = decideStatus(e);
            resp.setStatus(status.valueOf());
            IResponse response = new HttpResponse(resp);
            response.setBody(new ErrorView(e.getMessage(), status));
            response.processView();
        } catch (IOException e) {
            _logger.info("A sql or io exception was thrown: " + e.getMessage());
            resp.setStatus(InternalServerError.valueOf());
            IResponse response = new HttpResponse(resp);
            response.setBody(new ErrorView(e.getMessage(), InternalServerError));
            response.processView();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    _logger.info("The connection was closed in the CommandMatcher class");
                } catch (SQLException e) {
                    _logger.info("An error occured while closing the connection in the CommandMatcher");
                    resp.setStatus(InternalServerError.valueOf());
                    IResponse response = new HttpResponse(resp);
                    response.setBody(new ErrorView(e.getMessage(), InternalServerError));
                    response.processView();
                }
            }
        }
    }

    private HttpStatusCode decideStatus(AcademicActivitiesException e) {
        HttpStatusCode status;
        if(e instanceof ParameterException)
            status = BadRequest;
        else if (e instanceof SelectException || e instanceof PathException)
            status = NotFound;
        else if (e instanceof InsertException)
            status = Conflict;
        else
            status = InternalServerError;
        return status;
    }
}
