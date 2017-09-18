package pt.isel.ls.academicActivities.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.academicActivities.server.HttpServer;
import pt.isel.ls.academicActivities.exceptions.CommandException;

public class HerokuLauncher {
    private static Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws CommandException {
        int port = Integer.parseInt(System.getenv("PORT"));
        logger.info("server starting on port %s", port);
        HttpServer server = new HttpServer(port);
        try {
            server.getServer().start();
            logger.info("Started Server");
        } catch (Exception e) {
            logger.info("Error in server on port %s",port);
            throw new CommandException("Error in server");
        }
    }
}
