package pt.isel.ls.academicActivities.commands.programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;

import java.sql.SQLException;
import java.util.Arrays;

import static pt.isel.ls.academicActivities.database.data.DbProgrammeStructure.insertCourseIntoProgrammeStructure;

public class PostCourseIntoProgramme extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(PostCourseIntoProgramme.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int[] splitedSemesters = Arrays.stream(executionContext.getParams().getString("semesters").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'semesters'! Have you written it correctly?"))
                .split(",")).mapToInt(Integer::parseInt).toArray();
        int id = 0;
        ProgrammeStructure programmeStructure = null;
        try {
            for (int sem : splitedSemesters) {
                programmeStructure = new ProgrammeStructure(executionContext.getParams().getString("acr").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'acr'! Have you written it correctly?")),
                        executionContext.getParams().getString("pid").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'pid'! Have you written it correctly?")),
                        sem,
                        executionContext.getParams().getBool("mandatory").orElseThrow(() -> new ParameterException("Couldn't find the parameter 'mandatory'! Have you written it correctly?"))
                );
                id = insertCourseIntoProgrammeStructure(executionContext.getCon(), programmeStructure);
            }
            executionContext.getCon().commit();
        } catch (SQLException e) {
            _logger.info("exception thrown while posting course into programme: " + e.getMessage());
            throw new InsertException("Couldn't add course to the specified programme! Make sure it doesn't exist one in the databse already.");
        }
        if(!executionContext.isConsoleRequest())
            response.setLocation("/courses/" + programmeStructure.getCourseAcr());

        _logger.info("The command was successfully executed. " + id);
        _logger.info(String.format("Successfully inserted course with acr %s in %s",programmeStructure.getCourseAcr(),programmeStructure.getProgrammeAcr()));
    }

    @Override
    public String toString() {
        return "POST /programmes/{pid}/courses - adds a new course to the programme pid, given the following parameters: \n" +
                "\tacr - the course acronym.\n" +
                "\tmandatory  - true if the course is mandatory.\n" +
                "\tsemesters  - comma separated list of curricular semesters.";
    }
}
