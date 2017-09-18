package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

import static pt.isel.ls.academicActivities.database.data.DbTeacher.updateTeacher;

import java.sql.SQLException;

public class UpdateTeacher extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(UpdateTeacher.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String name = executionContext.getParams().getString("teacherName").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'teacherName'! Have you written it correctly?"));
        String email = executionContext.getParams().getString("teacherEmail").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'teacherEmail'! Have you written it correctly?"));
        int num = executionContext.getParams().getInt("teacherId").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'teacherId'! Have you written it correctly?"));

        int id;
        try {
            id = updateTeacher(executionContext.getCon(), num, name, email);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while updating teacher" + e.getMessage());
            throw new CommandException();
        }
        System.out.println("The command was successfully executed. " + id);
    }

    @Override
    public String toString() {
        return "PUT /teachers/{num} - updates an existent teacher, given all required parameters.";
    }
}
