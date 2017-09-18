package pt.isel.ls.academicActivities.commands.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.view.commands.student.GetStudentsOfClassOrderedByNumberViewHtml;
import pt.isel.ls.academicActivities.view.model.student.StudentsView;
import pt.isel.ls.academicActivities.view.model.student.StudentsViewJson;

import static pt.isel.ls.academicActivities.database.data.DbStudent.selectStudentsOfClassOrderedByNumber;

import java.sql.SQLException;

public class GetStudentsOfClassOrderedByNumber extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(GetStudentsOfClassOrderedByNumber.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int skip = executionContext.getParams().getInt("skip").orElse(0);
        int top = executionContext.getParams().getInt("top").orElse(Integer.MAX_VALUE);
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        QueryResult students;

        try {
            students = new QueryResult(selectStudentsOfClassOrderedByNumber(executionContext.getCon(),
                    executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?")),
                    sem[0],
                    sem[1],
                    executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?")),
                    skip, top
            ));
        } catch (SQLException e) {
            _logger.info("exception thrown while gettting students of class ordered by number: " + e.getMessage());
            throw new CommandException();
        }
        response.setQueryResult(students);
        response.setFileName(executionContext.getParams().getString("file-name").orElse(""));
        response.setHeader(executionContext.getParams().getString("accept").orElse("text/html"));

        response.addView("text/html", queryResult -> new GetStudentsOfClassOrderedByNumberViewHtml(queryResult.getEntities()));
        response.addView("text/plain", queryResult -> new StudentsView(queryResult.getEntities()));
        response.addView("application/json", queryResult -> new StudentsViewJson(queryResult.getEntities()));
    }

    @Override
    public String toString() {
        return "GET /courses/{acr}/classes/{sem}/{num}/students/sorted - returns a list with all students of the class, ordered by increasing student number.";
    }
}