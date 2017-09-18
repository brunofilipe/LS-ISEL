package pt.isel.ls.academicActivities.response;

import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.utils.Writable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HttpResponse implements IResponse {
    private final Map<String, Function<QueryResult, Writable>> viewMap;
    private HttpServletResponse originalResponse;
    private Writable body;
    private QueryResult entities;
    private String fileName;
    private String header;
    private String location;

    public HttpResponse(HttpServletResponse resp) {
        this.originalResponse = resp;
        this.viewMap = new HashMap<>();
    }

    @Override
    public void processView() throws IOException {
        Writer output = new OutputStreamWriter(originalResponse.getOutputStream());
        if(viewMap.isEmpty())
            body.writeTo(output);
        else
            viewMap.get(header).apply(entities).writeTo(output);
        output.flush();
    }

    @Override
    public void addView(String header, Function<QueryResult, Writable> viewFunction) {
        viewMap.put(header, viewFunction);
    }

    @Override
    public void setBody(Writable body) throws IOException {
        this.body = body;
    }

    @Override
    public QueryResult getQueryResult() {
        return entities;
    }

    @Override
    public void setQueryResult(QueryResult entities) {
        this.entities = entities;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public void setStatusCode(int status) {
        originalResponse.setStatus(status);
    }

    @Override
    public void redirect() throws IOException {
        originalResponse.sendRedirect(location);
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }
}
