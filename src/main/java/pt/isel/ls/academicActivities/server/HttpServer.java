package pt.isel.ls.academicActivities.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class HttpServer {
    private Server server;
    private ServletHandler handler;

    public Server getServer() {
        return server;
    }

    public HttpServer(int port) {
        server = new Server(port);
        handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(MainServlet.class, "/");
    }
}
