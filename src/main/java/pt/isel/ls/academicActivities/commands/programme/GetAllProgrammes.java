package pt.isel.ls.academicActivities.commands.programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.programme.GetAllProgrammesViewHtml;
import pt.isel.ls.academicActivities.view.model.programme.ProgrammesView;
import pt.isel.ls.academicActivities.view.model.programme.ProgrammesViewJson;

import static pt.isel.ls.academicActivities.database.data.DbProgramme.selectProgrammes;

import java.sql.SQLException;

public class GetAllProgrammes extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetAllProgrammes.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        QueryResult programmes;

        try {
            programmes = new QueryResult(selectProgrammes(executionContext.getCon(), skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting all programmes: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(programmes);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetAllProgrammesViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new ProgrammesView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new ProgrammesViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /programmes - list all the programmes.";
    }
}