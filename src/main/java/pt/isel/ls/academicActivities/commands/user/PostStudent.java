package pt.isel.ls.academicActivities.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

import static pt.isel.ls.academicActivities.database.data.DbStudent.insertStudent;

import java.sql.SQLException;

public class PostStudent extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostStudent.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        Student student = new Student(executionContext.getParams().getString("name").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'name'! Have you written it correctly?")),
                executionContext.getParams().getString("email").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'email'! Have you written it correctly?")),
                executionContext.getParams().getInt("num").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'num'! Have you written it correctly?")),
                executionContext.getParams().getString("pid").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'pid'! Have you written it correctly?"))
        );
        int id;
        try {
            id = insertStudent(executionContext.getCon(), student);
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting student: " + e.getMessage());
            throw new InsertException("An error occured while inserting the student! Are you sure it isn't the database already?");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/students/" + student.getStudentId());

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted student with id %s",student.getStudentId()));
    }

    @Override
    public String toString() {
        return "POST /students - creates a new student, given the following parameters:\n" +
                "\tnum - student number.\n" +
                "\tname - student name.\n" +
                "\temail - student email.\n" +
                "\tpid - programme acronym.";
    }
}
