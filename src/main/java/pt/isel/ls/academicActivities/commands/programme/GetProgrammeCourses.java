package pt.isel.ls.academicActivities.commands.programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.programme.GetProgrammeCoursesViewHtml;
import pt.isel.ls.academicActivities.view.model.programmeStructure.ProgrammeStructuresView;
import pt.isel.ls.academicActivities.view.model.programmeStructure.ProgrammeStructuresViewJson;

import static pt.isel.ls.academicActivities.database.data.DbProgrammeStructure.selectProgrammeStructuresOfSpecificProgramme;

import java.sql.SQLException;

public class GetProgrammeCourses extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetProgrammeCourses.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        QueryResult programmeStructures;

        try {
            programmeStructures = new QueryResult(selectProgrammeStructuresOfSpecificProgramme(executionContext.getCon(),
                    executionContext.getParams().getString("pid").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'pid'! Have you written it correctly?")),
                    skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting programmeStructures: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(programmeStructures);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetProgrammeCoursesViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new ProgrammeStructuresView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new ProgrammeStructuresViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /programmes/{pid}/courses - shows the course structure of programme pid.";
    }
}