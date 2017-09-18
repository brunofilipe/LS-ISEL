package pt.isel.ls.academicActivities.commands.functional;

import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.engine.Routes;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.utils.Pair;

public class Option extends Command {

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        for (Pair<String, Command> pair : Routes.cmdList) {
            System.out.println(pair.getValue());
        }
    }

    @Override
    public String toString() {
        return "OPTION /" + " - presents a list of available commands and their characteristics.";
    }
}
