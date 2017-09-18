package pt.isel.ls.academicActivities.commands.teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.teacher.GetTeachersOfClassViewHtml;
import pt.isel.ls.academicActivities.view.model.teacher.TeachersView;
import pt.isel.ls.academicActivities.view.model.teacher.TeachersViewJson;

import static pt.isel.ls.academicActivities.database.data.DbTeacher.selectTeachersFromClass;

import java.sql.SQLException;

public class GetTeachersOfClass extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetTeachersOfClass.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        String acr = executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?"));
        String num = executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"));
        QueryResult teachers;

        try {
            teachers = new QueryResult(selectTeachersFromClass(executionContext.getCon(), acr, sem[0], sem[1], num, skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting teachers of a class: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(teachers);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetTeachersOfClassViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new TeachersView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new TeachersViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /courses/{acr}/classes/{sem}/{num}/teachers - shows all teachers for a class.";
    }
}
