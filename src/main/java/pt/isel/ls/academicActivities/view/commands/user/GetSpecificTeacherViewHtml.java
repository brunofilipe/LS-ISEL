package pt.isel.ls.academicActivities.view.commands.user;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class GetSpecificTeacherViewHtml implements Writable {
    private Teacher teacher;

    public GetSpecificTeacherViewHtml(IEntity teacher) {
        this.teacher = (Teacher) teacher;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        Writable courseDivOrHeading = getCourseDivOrHeading();
        Writable classDivOrHeading = getClassDivOrHeading();
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h1(text(teacher.getTeacherName())),
                        h3(text("ID - " + teacher.getTeacherId())),
                        h3(text("Email - " + teacher.getTeacherEmail())),
                        courseDivOrHeading,
                        classDivOrHeading,
                        br(),
                        p(text("Return to the "),
                                a(UrlTo.allTeachers(), "Teacher list"),
                                text(" or the "),
                                a(UrlTo.allUsers(), "User list")
                        )
                ).withAttr("class", "container")
        );
        page.writeTo(w);
    }

    private Writable getClassDivOrHeading() {
        List<Class> classesTaught = teacher.getClassesTaught();
        if (classesTaught.isEmpty())
            return h3(text("This teacher isn't lecturing any classes!"));
        HtmlElem div = div();
        div.withContent(h3(text("Classes lectured by this teacher: ")));
        HtmlElem tableBody = new HtmlElem("tbody");
        classesTaught.forEach(aClass -> tableBody.withContent(
                tr(
                        td(a(UrlTo.academicClass(aClass.getClassId(), aClass.getCourseAcr(), aClass.getSemester()), aClass.getClassId())),
                        td(text((aClass.getCourseAcr()))),
                        td(text((aClass.getAcademicSemesterYear()))),
                        td(text(aClass.getAcademicSemesterType()))
                )
        ));
        div.withContent(
                table(
                        new HtmlElem("thead",
                                tr(
                                        th(text("id")),
                                        th(text("course")),
                                        th(text("year")),
                                        th(text("semester"))
                                )
                        ),
                        tableBody
                ).withAttr("border", "1").withAttr("class", "table")
        );
        return div;
    }

    private Writable getCourseDivOrHeading() {
        List<String> courses = teacher.getCoursesCoordinated();
        if (courses.isEmpty())
            return h3(text("This teacher doesn't coordinate any courses!"));
        HtmlElem div = div();
        div.withContent(h3(text("Courses coordinated by this teacher: ")));
        HtmlElem list = (HtmlElem) ul();
        for (String courseAcr : courses) {
            list.withContent(li(a(UrlTo.course(courseAcr), courseAcr)));
        }
        div.withContent(list);
        return div;
    }
}
