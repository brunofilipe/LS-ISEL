package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

import static pt.isel.ls.academicActivities.database.data.DbStudent.updateStudent;

import java.sql.SQLException;

public class UpdateStudent extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(UpdateStudent.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String name = executionContext.getParams().getString("studentName").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'studentName'! Have you written it correctly?"));
        String email = executionContext.getParams().getString("studentEmail").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'studentEmail'! Have you written it correctly?"));
        String acr = executionContext.getParams().getString("programmeAcr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'programmeAcr'! Have you written it correctly?"));
        int num = executionContext.getParams().getInt("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"));

        int id;
        try {
            id = updateStudent(executionContext.getCon(), num, name, email, acr);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while updating student" + e.getMessage());
            throw new CommandException();
        }
        System.out.println("The command was successfully executed. " + id);
    }

    @Override
    public String toString() {
        return "PUT /students/{num} - updates an existent student, given all required parameters.";
    }
}
