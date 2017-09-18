package pt.isel.ls.academicActivities.view;

import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;
import pt.isel.ls.academicActivities.utils.http.HttpStatusCode;

import java.io.IOException;
import java.io.Writer;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class ErrorView implements Writable {
    private String message;
    private HttpStatusCode status;


    public ErrorView(String message, HttpStatusCode status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        HtmlPage page = new HtmlPage(status+" - an error occured",
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h1(text(status.valueOf() + " - " + status)),
                        p(text(message)),
                        p(text("Let's return to the "),
                                a("/","homepage")
                        )
                ).withAttr("style", "text-align:center").withAttr("class", "container")
        );
        page.writeTo(w);
    }
}
