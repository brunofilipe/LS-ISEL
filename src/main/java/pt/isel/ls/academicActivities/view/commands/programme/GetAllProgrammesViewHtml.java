package pt.isel.ls.academicActivities.view.commands.programme;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import static pt.isel.ls.academicActivities.utils.html.Html.*;
import static pt.isel.ls.academicActivities.utils.html.Html.text;
import static pt.isel.ls.academicActivities.utils.html.Html.th;

public class GetAllProgrammesViewHtml implements Writable {
    private List<Programme> programmes;

    public GetAllProgrammesViewHtml(List<IEntity> programmes) {
        this.programmes = programmes
                .stream()
                .map(entity -> (Programme)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Existing programmes :  ";
        HtmlElem tableBody = new HtmlElem("tbody");
        programmes.forEach(programme -> tableBody.withContent(
                tr(
                        td(text(programme.getProgrammeName())),
                        td(a(UrlTo.programme(programme.getProgrammeAcr()), programme.getProgrammeAcr())),
                        td(text(programme.getProgrammeSemesters()+""))
                )
        ));
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h3(text(header)),
                        table(
                                new HtmlElem("thead",
                                        tr(
                                                th(text("Name")),
                                                th(text("Acronym")),
                                                th(text("Number of semesters"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1").withAttr("class","table"),
                        br(),
                        formWithCss("POST", UrlTo.postProgramme(),
                                fieldset(
                                        legend(text("Insert a new Programme")),
                                        text("Name"),
                                        br(),
                                        div(
                                         textInputWithCss("name")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("PID"),
                                        br(),
                                        div(
                                            textInputWithCss("pid")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Semesters"),
                                        br(),
                                        div(
                                            inputWithCss("length","number")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        br(),
                                        div(
                                            submitInputWithCss("submit")
                                        ).withAttr("class","form-group")
                                )
                        ),
                        br(),
                        p(text("Return to the "), a(UrlTo.homepage(),"Homepage"))
                ).withAttr("class","container")
        );
        page.writeTo(w);
    }
}
