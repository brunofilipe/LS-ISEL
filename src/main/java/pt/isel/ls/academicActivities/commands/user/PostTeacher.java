package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.model.Teacher;

import java.sql.SQLException;

import static pt.isel.ls.academicActivities.database.data.DbTeacher.insertTeacher;

public class PostTeacher extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostTeacher.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        Teacher teacher = new Teacher(executionContext.getParams().getString("name").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'name'! Have you written it correctly?")),
                executionContext.getParams().getString("email").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'email'! Have you written it correctly?")),
                executionContext.getParams().getInt("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"))
        );

        int id;
        try {
            id = insertTeacher(executionContext.getCon(), teacher);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting teacher" + e.getMessage());
            throw new InsertException("An error occurred while inserting the teacher! Are you sure it doesn't exist the database already?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/teachers/" + teacher.getTeacherId());

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted teacher with id %s",teacher.getTeacherId()));
    }

    @Override
    public String toString() {
        return "POST /teachers - creates a new teacher, given the following parameters:\n" +
                "\tnum - teacher number.\n" +
                "\tname - teacher name.\n" +
                "\temail - teacher email.";
    }
}
