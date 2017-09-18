package pt.isel.ls.academicActivities.view.commands.user;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static pt.isel.ls.academicActivities.utils.html.Html.*;
import static pt.isel.ls.academicActivities.utils.html.Html.text;
import static pt.isel.ls.academicActivities.utils.html.Html.th;

public class GetUsersViewHtml implements Writable {
    private List<IEntity> users;

    public GetUsersViewHtml(List<IEntity> users) {
        this.users = users;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Existing users :";
        HtmlElem tableBodyUsers = new HtmlElem("tbody");
        users.forEach(user -> {
            if ( user instanceof Teacher ) {
                Teacher teacher = (Teacher)user;
                tableBodyUsers.withContent(
                        tr(
                                td(a(UrlTo.teacher(teacher.getTeacherId()), teacher.getTeacherId() +"")),
                                td(text(teacher.getTeacherName())),
                                td(text(teacher.getTeacherEmail()))
                        )
                );
            } else {
                Student student = (Student)user;
                tableBodyUsers.withContent(
                        tr(
                                td(a(UrlTo.student(student.getStudentId()),student.getStudentId() +"")),
                                td(text(student.getStudentName())),
                                td(text(student.getStudentEmail()))
                        )
                );
            }
        });
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h3(text(header)),
                        table(
                                new HtmlElem("thead",
                                        tr(
                                                th(text("User ID")),
                                                th(text("User Name")),
                                                th(text("User Email"))
                                        )
                                ),
                                tableBodyUsers
                        ).withAttr("border","4").withAttr("class","table"),
                        p(text("return to the "), a(UrlTo.homepage(),"Homepage"))
                ).withAttr("class","container")

        );
        page.writeTo(w);
    }
}
