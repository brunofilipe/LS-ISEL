package pt.isel.ls.academicActivities.view.commands.user;

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

public class GetAllTeachersViewHtml implements Writable {
    private List<Teacher> teachers;

    public GetAllTeachersViewHtml(List<IEntity> teachers) {
        this.teachers = teachers
                .stream()
                .map(entity -> (Teacher)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Existing teachers :  ";
        HtmlElem tableBody = new HtmlElem("tbody");
        teachers.forEach(teacher -> tableBody.withContent(
                tr(
                        td(a( UrlTo.teacher(teacher.getTeacherId()), teacher.getTeacherId() +"")),
                        td(text(teacher.getTeacherName())),
                        td(text(teacher.getTeacherEmail()))
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
                        ).withAttr("border","1").withAttr("class","table"),
                        br(),
                        formWithCss("POST", UrlTo.postTeacher(),
                                fieldset(
                                        legend(text("Insert a new Teacher")),
                                        text("Name"),
                                        br(),
                                        div(
                                            textInputWithCss("name")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Email"),
                                        br(),
                                        div(
                                            textInputWithCss("email")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Teacher ID"),
                                        br(),
                                        div(
                                            inputWithCss("num", "number")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        br(),
                                        div(
                                            submitInputWithCss("submit")
                                        ).withAttr("class","form-group"),
                                        br()
                                )
                        ),
                        br(),
                        p(text("Return to the "), a(UrlTo.homepage(),"Homepage"))
                ).withAttr("class","container")

        );
        page.writeTo(w);
    }
}
