package pt.isel.ls.academicActivities.commands.teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

import static pt.isel.ls.academicActivities.database.data.DbTeacher.insertTeacherInClass;

import java.sql.SQLException;

public class PostTeacherToClass extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostTeacherToClass.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        int numDoc = executionContext.getParams().getInt("numDoc").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'numDoc'! Have you written it correctly?"));
        String acr = executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?"));
        String num = executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"));

        int id;
        try {
            id = insertTeacherInClass(executionContext.getCon(), numDoc, num, acr, sem[0], sem[1]);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting teacher to class: " + e.getMessage());
            throw new InsertException("An error occurred while inserting the teacher into the class! Are you sure the class exists in the database?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/teachers/" + numDoc);

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted teacher with id %s in class %s in semester %s %s",numDoc,num,sem[0],sem[1]));
    }

    @Override
    public String toString() {
        return "POST /courses/{acr}/classes/{sem}/{num}/teachers - adds a new teacher to a class, given the following parameters:\n" + "\tnumDoc - teacher number. ";
    }
}
