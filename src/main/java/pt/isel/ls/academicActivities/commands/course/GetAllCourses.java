package pt.isel.ls.academicActivities.commands.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.course.GetAllCoursesViewHtml;
import pt.isel.ls.academicActivities.view.model.course.CoursesView;
import pt.isel.ls.academicActivities.view.model.course.CoursesViewJson;

import static pt.isel.ls.academicActivities.database.data.DbCourse.selectCourses;

import java.sql.SQLException;

public class GetAllCourses extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetAllCourses.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        QueryResult courses;

        try {
            courses = new QueryResult(selectCourses(executionContext.getCon(), skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown getting all courses: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(courses);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetAllCoursesViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new CoursesView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new CoursesViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /courses - shows all courses.";
    }
}
