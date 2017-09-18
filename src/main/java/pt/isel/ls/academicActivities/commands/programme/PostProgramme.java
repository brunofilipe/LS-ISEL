package pt.isel.ls.academicActivities.commands.programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.model.Programme;

import java.sql.SQLException;

import static pt.isel.ls.academicActivities.database.data.DbProgramme.insertProgramme;

public class PostProgramme extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostProgramme.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        Programme programme = new Programme(executionContext.getParams().getString("name").orElseThrow(ParameterException::new),
                executionContext.getParams().getString("pid").orElseThrow(ParameterException::new),
                executionContext.getParams().getInt("length").orElseThrow(ParameterException::new)
        );
        int id;
        try {
            id = insertProgramme(executionContext.getCon(), programme);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting programming: " + e.getMessage());
            throw new InsertException("An error occurred while inserting the programme! Are you sure it isn't in the database already?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/programmes/" + programme.getProgrammeAcr());

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted programme with acr %s ",programme.getProgrammeAcr()));
    }

    @Override
    public String toString() {
        return "POST /programmes - creates a new programme, given the following parameters: \n" +
                "\tpid - programme acronym.\n" +
                "\tname  - programme name.\n" +
                "\tlength  - number of semesters.";
    }
}