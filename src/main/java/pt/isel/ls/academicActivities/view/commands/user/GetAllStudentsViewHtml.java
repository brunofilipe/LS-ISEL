package pt.isel.ls.academicActivities.view.commands.user;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
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

public class GetAllStudentsViewHtml implements Writable{
    private List<Student> students;

    public GetAllStudentsViewHtml(List<IEntity> students) {
        this.students = students
                .stream()
                .map(entity -> (Student)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Existing students :  ";
        HtmlElem tableBody = new HtmlElem("tbody");
        students.forEach(student -> tableBody.withContent(
                tr(
                        td(a( UrlTo.student(student.getStudentId()), student.getStudentId() +"")),
                        td(text(student.getStudentName())),
                        td(text(student.getStudentEmail())),
                        td(a(UrlTo.programme(student.getProgrammeAcr()), student.getProgrammeAcr()))
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
                                                th(text("student ID")),
                                                th(text("student Name")),
                                                th(text("student Email")),
                                                th(text("programme Acr"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1").withAttr("class","table"),
                        br(),
                        formWithCss("POST", UrlTo.postStudent(),
                                fieldset(
                                        legend(text("Insert a new Student")),
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
                                        text("Student ID"),
                                        br(),
                                        div(
                                            inputWithCss("num", "number")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Programme acr"),
                                        br(),
                                        div(
                                            textInputWithCss("pid")
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
