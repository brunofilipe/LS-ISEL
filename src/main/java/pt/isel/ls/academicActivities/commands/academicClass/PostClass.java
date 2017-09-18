package pt.isel.ls.academicActivities.commands.academicClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.model.Class;

import java.sql.SQLException;

import static pt.isel.ls.academicActivities.database.data.DbClass.insertClass;

public class PostClass extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostClass.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        Class academicClass = new Class(executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?")),
                executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?")),
                sem[0],
                sem[1]
        );
        int id;
        try {
            id = insertClass(executionContext.getCon(), academicClass);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown posting class: " + e.getMessage());
            throw new InsertException("An error occurred while inserting the class! Are you sure it isn't in the database already?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/courses/" + academicClass.getCourseAcr() + "/classes/" + academicClass.getSemester() + "/" + academicClass.getClassId());

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted class with id %s",academicClass.getClassId()));
    }

    @Override
    public String toString() {
        return "POST /courses/{acr}/classes - creates a new class on the course with acr acronym, given the following parameters:\n" +
                "\tsem - semester identifier.\n" +
                "\tnum - class number.";
    }

}
