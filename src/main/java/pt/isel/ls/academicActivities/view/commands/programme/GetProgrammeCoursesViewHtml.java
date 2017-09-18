package pt.isel.ls.academicActivities.view.commands.programme;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;
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

public class GetProgrammeCoursesViewHtml implements Writable{
    private List<ProgrammeStructure> programmeStructures;

    public GetProgrammeCoursesViewHtml(List<IEntity> programmeStructures) {
        this.programmeStructures = programmeStructures
                .stream()
                .map(entity -> (ProgrammeStructure)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Programmes from the course " + programmeStructures.get(0).getCourseAcr();
        HtmlElem tableBody = new HtmlElem("tbody");
        programmeStructures.forEach(ps -> tableBody.withContent(
                tr(
                        td(a(UrlTo.programme(ps.getProgrammeAcr()), ps.getProgrammeAcr())),
                        td(a( UrlTo.course(ps.getCourseAcr()), ps.getCourseAcr())),
                        td(text(ps.getCourseAvailabilitySemester()+"")),
                        td(text(ps.getHasMandatory()+""))
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
                                                th(text("programme Acr")),
                                                th(text("course acronym")),
                                                th(text("course Availability")),
                                                th(text("Mandatory"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1")
                         .withAttr("class","table")
                ).withAttr("class","container")
        );
        page.writeTo(w);
    }
}
