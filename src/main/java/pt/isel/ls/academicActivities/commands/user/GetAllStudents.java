package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.user.GetAllStudentsViewHtml;
import pt.isel.ls.academicActivities.view.model.student.StudentsView;
import pt.isel.ls.academicActivities.view.model.student.StudentsViewJson;

import static pt.isel.ls.academicActivities.database.data.DbStudent.selectStudents;

import java.sql.SQLException;

public class GetAllStudents extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetAllStudents.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        QueryResult students;

        try {
            students = new QueryResult(selectStudents(executionContext.getCon(), skip, top));
        } catch (SQLException e) {
            _logger.info("exception thrown while getting all students: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(students);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetAllStudentsViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new StudentsView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new StudentsViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /students - shows all students.";
    }
}
