package pt.isel.ls.academicActivities.commands.teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.teacher.GetClassesTaughtByTeacherViewHtml;
import pt.isel.ls.academicActivities.view.model.teacher.TeachersView;
import pt.isel.ls.academicActivities.view.model.teacher.TeachersViewJson;

import static pt.isel.ls.academicActivities.database.data.DbClass.selectClassesTaughtByTeacher;

import java.sql.SQLException;

public class GetClassesTaughtByTeacher extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetClassesTaughtByTeacher.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        QueryResult classes;

        try {
            classes = new QueryResult(selectClassesTaughtByTeacher(executionContext.getCon(),
                    executionContext.getParams().getInt("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?")),
                    skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown while removing student from class: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(classes);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetClassesTaughtByTeacherViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new TeachersView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new TeachersViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /teachers/{num}/classes - shows all classes for the teacher with num number.";
    }
}
