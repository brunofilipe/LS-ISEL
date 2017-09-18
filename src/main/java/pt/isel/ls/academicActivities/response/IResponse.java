package pt.isel.ls.academicActivities.response;

import pt.isel.ls.academicActivities.engine.QueryResult;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.util.function.Function;

public interface IResponse {

    void processView() throws IOException;

    void addView(String header, Function<QueryResult, Writable> viewFunction);

    void setBody(Writable view) throws IOException;

    QueryResult getQueryResult();

    void setQueryResult(QueryResult entities);

    void setFileName(String fileName);

    void setHeader(String header);

    void setStatusCode(int status);

    void redirect() throws IOException;

    void setLocation(String s);
}
