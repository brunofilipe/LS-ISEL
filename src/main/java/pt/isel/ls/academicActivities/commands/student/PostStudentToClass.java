package pt.isel.ls.academicActivities.commands.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;

import java.sql.SQLException;
import java.util.List;

import static pt.isel.ls.academicActivities.database.data.DbStudent.insertStudentIntoClass;

public class PostStudentToClass extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostStudentToClass.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        String[] sem = translateSemester(executionContext.getParams().getString("sem").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'sem'! Have you written it correctly?")));
        String num = executionContext.getParams().getString("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?"));
        String acr = executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?"));
        List<Integer> stdList = executionContext.getParams().getListOfInts("numStu").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'numStu'! Have you written it correctly?"));

        int id = 0;
        try {
            for (Integer studentId : stdList) {
                id = insertStudentIntoClass(executionContext.getCon(), studentId, num, acr, sem[0], sem[1]);
            }
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting student into class: " + e.getMessage());
            throw new InsertException("An error occured while inserting one of the students in the class! Are you sure one of them doesn't exist in the database already?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/students/" + stdList.get(0));

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted student with id %s in class %s in semester %s %s",stdList.get(0),num,sem[0],sem[1]));
    }

    @Override
    public String toString() {
        return "POST /courses/{acr}/classes/{sem}/{num}/students - adds a new student to a class, given the following parameter that can occur multiple times: \n" +
                "\tnumStu -  student number.";
    }
}
