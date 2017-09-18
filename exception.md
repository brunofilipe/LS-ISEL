In this package we have our exceptions that are thrown when conflicts are detected.

They are:
* `AcademicActivitiesException` - includes all the exceptions of our app and extends directly from Exception;
* `CommandException` - it's thrown by the commands and serves as a representation of both InsertException and SelectException;
* `DatabaseException` - this exception is thrown when there are problems connecting to the database that we are using;
* `InsertException` - exception thrown when occurs a error inserting data;
* `ParameterException` - thrown when the parameters were incorrectly inputted by the user and/or are missing;
* `PathException`- it's thrown when the path isn't translated in a command;
* `SelectException` - this exception is thrown when occurs a error while performing select statements.