package pt.isel.ls.academicActivities.commands.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.model.Course;

import java.sql.SQLException;

import static pt.isel.ls.academicActivities.database.data.DbCourse.insertCourse;

public class PostCourse extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostCourse.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        Course course = new Course(executionContext.getParams().getString("name").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'name'! Have you written it correctly?")),
                executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?")),
                executionContext.getParams().getInt("teacher").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'teacher'! Have you written it correctly?"))
        );
        int id;
        try {
            id = insertCourse(executionContext.getCon(), course);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting a course: " + e.getMessage());
            throw new InsertException("An error occurred while inserting the course! Are you sure it isn't in the database already or if the teacher exists?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/courses/" + course.getCourseAcr());

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted course with acr %s",course.getCourseAcr()));
    }

    @Override
    public String toString() {
        return "POST /courses - creates a new course, given the following parameters:\n" +
                "\tname - course name.\n" +
                "\tacr - course acronym.\n" +
                "\tteacher - number of the coordinator teacher.";
    }
}
