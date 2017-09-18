package pt.isel.ls.academicActivities.engine;

import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class RouteInfo {
    private List<Pair<String, String>> variablePaths;
    private Command command;

    public RouteInfo() {
        this.variablePaths = new ArrayList<>();
    }

    public void addToVarPathList(Pair<String, String> pair) {
        variablePaths.add(pair);
    }

    public List<Pair<String, String>> getVariablePaths() {
        return variablePaths;
    }

    public void setVariablePaths(List<Pair<String, String>> variablePaths) {
        this.variablePaths = variablePaths;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
