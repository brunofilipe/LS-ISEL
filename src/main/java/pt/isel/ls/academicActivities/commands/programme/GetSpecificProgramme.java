package pt.isel.ls.academicActivities.commands.programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.programme.GetSpecificProgrammeViewHtml;
import pt.isel.ls.academicActivities.view.model.programme.SingleProgrammeView;
import pt.isel.ls.academicActivities.view.model.programme.SingleProgrammeViewJson;

import static pt.isel.ls.academicActivities.database.data.DbProgramme.selectProgramme;

import java.sql.SQLException;

public class GetSpecificProgramme extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetSpecificProgramme.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        QueryResult programme;

        try {
            programme = new QueryResult(selectProgramme(executionContext.getCon(), executionContext.getParams().getString("pid").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'pid'! Have you written it correctly?"))));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting specific programme: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(programme);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetSpecificProgrammeViewHtml(queryResult.getEntity()));
        response.addView("text/plain", queryResult -> new SingleProgrammeView(queryResult.getEntity()));
        response.addView("application/json", queryResult -> new SingleProgrammeViewJson(queryResult.getEntity()));
    }

    @Override
    public String toString() {
        return "GET /programmes/{pid} - shows the details of programme with pid acronym.";
    }
}