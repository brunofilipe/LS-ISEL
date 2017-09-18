package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.user.GetUsersViewHtml;
import pt.isel.ls.academicActivities.view.model.user.UsersView;
import pt.isel.ls.academicActivities.view.model.user.UsersViewJson;

import static pt.isel.ls.academicActivities.database.data.DbStudent.selectStudents;
import static pt.isel.ls.academicActivities.database.data.DbTeacher.selectTeachers;

import java.sql.SQLException;

public class GetUsers extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetUsers.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        QueryResult users = null;

        try {
            try {
                users = new QueryResult(selectStudents(executionContext.getCon(), skip, top));
            } catch (CommandException e) {
                _logger.info("exception thrown while getting teachers for users: " + e.getMessage());
            } finally {
                if (users == null)
                    users = new QueryResult(selectTeachers(executionContext.getCon(), skip, top));
                else
                    users.getEntities().addAll(selectTeachers(executionContext.getCon(), skip, top));
            }
        } catch (SQLException e) {
            _logger.info("exception thrown while getting users: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(users);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetUsersViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new UsersView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new UsersViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /users - shows all users.";
    }
}
