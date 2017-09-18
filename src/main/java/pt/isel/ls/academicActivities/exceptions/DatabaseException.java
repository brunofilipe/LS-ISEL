package pt.isel.ls.academicActivities.exceptions;

public class DatabaseException extends AcademicActivitiesException {

    public DatabaseException() {
        super("An error occurred while trying to establish a connection to the database (try checking your environment variables)");
    }
}
