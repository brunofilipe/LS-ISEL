package pt.isel.ls.academicActivities.exceptions;

public class CommandException extends AcademicActivitiesException {

    public CommandException() {
        super("An error occurred while executing the command (your data might be conflicting with other existing data in the database)");
    }

    public CommandException(String message) {
        super(message);
    }
}
