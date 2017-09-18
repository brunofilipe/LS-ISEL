package pt.isel.ls.academicActivities.commands.academicClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.academicClass.GetSpecificClassOfCourseViewHtml;
import pt.isel.ls.academicActivities.view.model.academicClass.SingleClassView;
import pt.isel.ls.academicActivities.view.model.academicClass.SingleClassViewJson;

import static pt.isel.ls.academicActivities.database.data.DbClass.selectClass;

import java.sql.SQLException;

public class GetSpecificClassOfCourse extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetSpecificClassOfCourse.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        QueryResult aClass;

        try {
            aClass = new QueryResult(selectClass(executionContext.getCon(),
                    executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?")),
                    executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?")),
                    sem[0], sem[1]
            ));
        } catch (SQLException e) {
            _logger.info("exception thrown getting a class of a course: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(aClass);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetSpecificClassOfCourseViewHtml(queryResult.getEntity()));
        response.addView("text/plain", queryResult -> new SingleClassView(queryResult.getEntity()));
        response.addView("application/json", queryResult -> new SingleClassViewJson(queryResult.getEntity()));
    }

    @Override
    public String toString() {
        return "GET /courses/{acr}/classes/{sem}/{num} - shows the classes of the acr course on the sem semester and with num number.";
    }
}
