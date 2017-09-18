package pt.isel.ls.academicActivities.view.commands.teacher;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
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

public class GetClassesTaughtByTeacherViewHtml implements Writable{
    private List<Class> classes;

    public GetClassesTaughtByTeacherViewHtml(List<IEntity> classes){
        this.classes = classes
                .stream()
                .map(entity -> (Class)entity)
                .collect(Collectors.toList());
    }
    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Classes taught by the teacher specified in the parameters :";
        HtmlElem tableBody = new HtmlElem("tbody");
        classes.forEach(c -> tableBody.withContent(
                tr(
                        td(a( UrlTo.academicClass(c.getClassId(), c.getCourseAcr(), c.getSemester()), c.getClassId())),
                        td(a( UrlTo.course(c.getCourseAcr()), c.getCourseAcr())),
                        td(text(c.getAcademicSemesterType())),
                        td(text(c.getAcademicSemesterYear()))
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
                                                th(text("academicClass ID")),
                                                th(text("academicClass Acr")),
                                                th(text("Semester type")),
                                                th(text("Semester year"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1").withAttr("class","table")
                ).withAttr("class","container")


        );
        page.writeTo(w);
    }
}
