package pt.isel.ls.academicActivities.commands.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.course.GetSpecificCourseViewHtml;
import pt.isel.ls.academicActivities.view.model.course.SingleCourseView;
import pt.isel.ls.academicActivities.view.model.course.SingleCourseViewJson;

import static pt.isel.ls.academicActivities.database.data.DbCourse.selectCourse;

import java.sql.SQLException;

public class GetSpecificCourse extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetSpecificCourse.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        QueryResult course;

        try {
            course = new QueryResult(selectCourse(executionContext.getCon(), executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?"))));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting a course: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(course);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetSpecificCourseViewHtml(queryResult.getEntity()));
        response.addView("text/plain", queryResult -> new SingleCourseView(queryResult.getEntity()));
        response.addView("application/json", queryResult -> new SingleCourseViewJson(queryResult.getEntity()));
    }

    @Override
    public String toString() {
        return "GET /courses/{acr} - shows the course with the acr acronym.";
    }
}
