package pt.isel.ls.academicActivities.request;

public class Request {
    private String method;
    private String[] path;

    public Request(String method, String path) {
        this.method = method.toUpperCase();
        this.path = path.split("/");
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }

}

