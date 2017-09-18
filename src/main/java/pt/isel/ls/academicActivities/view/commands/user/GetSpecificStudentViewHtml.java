package pt.isel.ls.academicActivities.view.commands.user;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class GetSpecificStudentViewHtml implements Writable {
    private Student student;

    public GetSpecificStudentViewHtml(IEntity student) {
        this.student = (Student) student;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        Writable classDivOrHeading = getClassDivOrHeading();
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h1(text(student.getStudentName())),
                        h3(text("ID - " + student.getStudentId())),
                        h3(text("Email - " + student.getStudentEmail())),
                        h3(text("Registered in the programme "), a(UrlTo.programme(student.getProgrammeAcr()), student.getProgrammeAcr())),
                        classDivOrHeading,
                        p(text("Return to the "),
                                a(UrlTo.allStudents(), "Student list"),
                                text(" or the "),
                                a(UrlTo.allUsers(), "User list")
                        )
                ).withAttr("class", "container")

        );
        page.writeTo(w);
    }

    private Writable getClassDivOrHeading() {
        List<Class> classesAttended = student.getAttendingClasses();
        if (classesAttended.isEmpty())
            return h3(text("This student isn't registered in any classes!"));
        HtmlElem div = div();
        div.withContent(h3(text("Classes attended by this student: ")));
        HtmlElem tableBody = new HtmlElem("tbody");
        classesAttended.forEach(aClass -> tableBody.withContent(
                tr(
                        td(a(UrlTo.academicClass(aClass.getClassId(), aClass.getCourseAcr(), aClass.getSemester()), aClass.getClassId())),
                        td(text((aClass.getCourseAcr()))),
                        td(text((aClass.getAcademicSemesterYear()))),
                        td(text(aClass.getAcademicSemesterType()))
                )
        ));
        div.withContent(table(
                new HtmlElem("thead",
                        tr(
                                th(text("id")),
                                th(text("course")),
                                th(text("year")),
                                th(text("semester"))
                        )
                ), tableBody).withAttr("border", "1").withAttr("class", "table")
        );
        return div;
    }
}
