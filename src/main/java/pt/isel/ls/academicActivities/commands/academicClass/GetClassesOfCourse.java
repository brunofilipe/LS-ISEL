package pt.isel.ls.academicActivities.commands.academicClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.academicClass.GetClassesOfCourseViewHtml;
import pt.isel.ls.academicActivities.view.model.academicClass.ClassesView;
import pt.isel.ls.academicActivities.view.model.academicClass.ClassesViewJson;

import static pt.isel.ls.academicActivities.database.data.DbClass.selectClassesOfCourse;

import java.sql.SQLException;

public class GetClassesOfCourse extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetClassesOfCourse.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        String acr = executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?"));
        QueryResult classes;

        try {
            classes = new QueryResult(selectClassesOfCourse(executionContext.getCon(), acr, skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown getting classes of course: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(classes);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetClassesOfCourseViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new ClassesView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new ClassesViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /courses/{acr}/classes - shows all classes for a course.";
    }
}
