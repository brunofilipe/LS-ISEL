package pt.isel.ls.academicActivities.commands.functional;

import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;

public class Exit extends Command {

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        System.out.print("Your app is closing...");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "EXIT /" + " - ends the application.";
    }
}
