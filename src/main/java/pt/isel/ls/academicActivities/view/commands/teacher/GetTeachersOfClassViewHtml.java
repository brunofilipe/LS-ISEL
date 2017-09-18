package pt.isel.ls.academicActivities.view.commands.teacher;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Teacher;
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

public class GetTeachersOfClassViewHtml implements Writable {
    private List<Teacher> teachers;

    public GetTeachersOfClassViewHtml(List<IEntity> teachers){
        this.teachers = teachers
                .stream()
                .map(entity -> (Teacher)entity)
                .collect(Collectors.toList());
    }
    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Teachers of the specified class :";
        HtmlElem tableBody = new HtmlElem("tbody");
        teachers.forEach(t -> tableBody.withContent(
                tr(
                        td(a(UrlTo.teacher(t.getTeacherId()), t.getTeacherId() + "")),
                        td(text(t.getTeacherName())),
                        td(text(t.getTeacherEmail()))
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
                                                th(text("teacher ID")),
                                                th(text("teacher Name")),
                                                th(text("teacher Email"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1").withAttr("class","table")
                ).withAttr("class","container")
        );
        page.writeTo(w);
    }
}
