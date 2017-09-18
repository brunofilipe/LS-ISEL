package pt.isel.ls.academicActivities.commands;

import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

public abstract class Command {

    public abstract void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException;

    protected final String[] translateSemester(String semAcr) throws ParameterException {
        if (!semAcr.endsWith("i") && !semAcr.endsWith("v"))
            throw new ParameterException("the semester inputted in the parameter 'sem' is invalid! make sure you're following the format {year}{i/v}");
        String sem = semAcr.endsWith("i") ? "winter" : "summer";
        String year = semAcr.substring(0, semAcr.length() - 1);
        return new String[]{year, sem};
    }
}
