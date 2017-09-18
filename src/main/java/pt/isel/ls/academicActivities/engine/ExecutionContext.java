package pt.isel.ls.academicActivities.engine;

import java.sql.Connection;

public class ExecutionContext {
    private Parameters params;
    private Connection con;
    private boolean isConsoleRequest;

    public ExecutionContext(Parameters parameters, Connection con, boolean isConsoleRequest) {
        this.params = parameters;
        this.con = con;
        this.isConsoleRequest = isConsoleRequest;
    }

    public Parameters getParams() {
        return params;
    }

    public void setParams(Parameters params) {
        this.params = params;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public boolean isConsoleRequest() {
        return isConsoleRequest;
    }

    public void setConsoleRequest(boolean consoleRequest) {
        isConsoleRequest = consoleRequest;
    }
}

