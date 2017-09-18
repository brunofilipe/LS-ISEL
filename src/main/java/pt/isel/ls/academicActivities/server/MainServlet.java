package pt.isel.ls.academicActivities.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.engine.CommandMatcher;
import pt.isel.ls.academicActivities.app.AcademicActivitiesApp;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final Logger _logger = LoggerFactory.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        parse(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        parse(req, resp);
    }

    private void parse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        _logger.info(String.format("Received %s request for %s", req.getMethod(), req.getRequestURI()));
        CommandMatcher commandMatcher = new CommandMatcher(AcademicActivitiesApp.getRoutes());
        commandMatcher.execute(req, resp);
    }
}
