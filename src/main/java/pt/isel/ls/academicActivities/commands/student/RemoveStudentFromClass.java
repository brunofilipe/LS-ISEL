package pt.isel.ls.academicActivities.commands.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

import static pt.isel.ls.academicActivities.database.data.DbStudent.deleteStudentFromClass;

import java.sql.SQLException;

public class RemoveStudentFromClass extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(RemoveStudentFromClass.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        int numStud = executionContext.getParams().getInt("numStu").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'numStu'! Have you written it correctly?"));
        String num = executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"));
        String acr = executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?"));
        int id;
        try {
            id = deleteStudentFromClass(executionContext.getCon(), numStud, num, acr, sem[0], sem[1]);
        } catch (SQLException e) {
            _logger.info("exception thrown while removing student from class: " + e.getMessage());
            throw new CommandException();
        }
        System.out.println("The command was successfully executed. " + id);
    }

    @Override
    public String toString() {
        return "DELETE /courses/{acr}/classes/{sem}/{num}/students/{numStu} - removes a student from a class.";
    }
}
