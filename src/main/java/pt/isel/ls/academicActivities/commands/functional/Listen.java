package pt.isel.ls.academicActivities.commands.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.server.HttpServer;
import pt.isel.ls.academicActivities.commands.Command;
import pt.isel.ls.academicActivities.engine.ExecutionContext;
import pt.isel.ls.academicActivities.response.IResponse;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.ParameterException;

public class Listen extends Command {
    private static final Logger _logger = LoggerFactory.getLogger(Listen.class);

    @Override
    public void execute(ExecutionContext executionContext, IResponse response) throws CommandException, ParameterException {
        int port = executionContext.getParams().getInt("port").orElse(8080);
        _logger.info(String.format("server starting on port %s", port));
        HttpServer server = new HttpServer(port);
        try {
            server.getServer().start();
            _logger.info("Started Server");
        } catch (Exception e) {
            _logger.info("error while starting server with the listen command");
            throw new CommandException("Error in server");
        }
    }

    @Override
    public String toString() {
        return "LISTEN /" + " - starts the HTTP server, given the following parameter:\n" +
                "\tport - TCP port where the server should listen for requests.";
    }
}
