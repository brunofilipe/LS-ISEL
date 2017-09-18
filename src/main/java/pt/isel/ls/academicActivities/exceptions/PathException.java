package pt.isel.ls.academicActivities.exceptions;

public class PathException extends AcademicActivitiesException {

    public PathException() {
        super("The command was not found (have you inputted the path correctly?)");
    }

}
