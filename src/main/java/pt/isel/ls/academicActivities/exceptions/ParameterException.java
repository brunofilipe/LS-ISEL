package pt.isel.ls.academicActivities.exceptions;

public class ParameterException extends AcademicActivitiesException {

    public ParameterException() {
        super("An error occurred while accessing the parameters (have you inputted them correctly?)");
    }

    public ParameterException(String message) {
        super(message);
    }
}
