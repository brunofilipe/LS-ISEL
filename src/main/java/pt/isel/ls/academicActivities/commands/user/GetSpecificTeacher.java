package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.user.GetSpecificTeacherViewHtml;
import pt.isel.ls.academicActivities.view.model.teacher.SingleTeacherView;
import pt.isel.ls.academicActivities.view.model.teacher.SingleTeacherViewJson;

import static pt.isel.ls.academicActivities.database.data.DbTeacher.selectTeacher;

import java.sql.SQLException;

public class GetSpecificTeacher extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetSpecificTeacher.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        QueryResult teacher;

        try {
            teacher = new QueryResult(selectTeacher(executionContext.getCon(), executionContext.getParams().getInt("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"))));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting a teacher: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(teacher);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetSpecificTeacherViewHtml(queryResult.getEntity()));
        response.addView("text/plain", queryResult -> new SingleTeacherView(queryResult.getEntity()));
        response.addView("application/json", queryResult -> new SingleTeacherViewJson(queryResult.getEntity()));
    }

    @Override
    public String toString() {
        return "GET /teachers/{num} - shows the teacher with the number num.";
    }
}
