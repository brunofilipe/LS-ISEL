package pt.isel.ls.academicActivities.response;

import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConsoleResponse implements IResponse {
    private final Map<String, Function<QueryResult, Writable>> viewMap;
    private QueryResult entities;
    private String fileName;
    private String header;

    public ConsoleResponse() {
        viewMap = new HashMap<>();
    }

    public ConsoleResponse(QueryResult entities, String header, String fileName) {
        viewMap = new HashMap<>();
        this.entities = entities;
        this.header = header;
        this.fileName = fileName;
    }

    @Override
    public void addView(String header, Function<QueryResult, Writable> viewFunction) {
        viewMap.put(header, viewFunction);
    }

    @Override
    public void setBody(Writable view) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void processView() throws IOException {
        if (viewMap.isEmpty())
            return;
        Writer outputTypeWriter = fileName.isEmpty() ? new PrintWriter(System.out) : new FileWriter(new File(fileName));
        viewMap.get(header).apply(entities).writeTo(outputTypeWriter);
        outputTypeWriter.flush();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void redirect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocation(String s) {
        throw new UnsupportedOperationException();
    }

    public static ConsoleResponse emptyResponse() {
        return new ConsoleResponse();
    }
}
