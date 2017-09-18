package pt.isel.ls.academicActivities.view.commands.academicClass;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class GetSpecificClassOfCourseViewHtml implements Writable {
    private Class aClass;

    public GetSpecificClassOfCourseViewHtml(IEntity aClass) {
        this.aClass = (Class)aClass;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        Writable teacherDivOrHeading = getTeacherDivOrHeading();
        Writable studentDivOrHeading = getStudentDivOrHeading();
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h1(text(aClass.getClassId())),
                        h3(text("Curricular year - " + aClass.getAcademicSemesterYear())),
                        h3(text("Curricular semester - " + aClass.getAcademicSemesterType())),
                        h3(text("Course Acronym - " + aClass.getCourseAcr())),
                        studentDivOrHeading,
                        formWithCss("POST", UrlTo.postStudentIntoClass(aClass.getClassId(), aClass.getCourseAcr(), aClass.getSemester()),
                                fieldset(
                                        legend(text("Insert a Student into this Class")),
                                        text("Student ID"),
                                        br(),
                                        div(
                                            textInputWithCss("numStu")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        br(),
                                        div(
                                            submitInputWithCss("submit")
                                        ).withAttr("class","form-group")

                                )
                        ),
                        teacherDivOrHeading,
                        formWithCss("POST", UrlTo.postTeacherIntoClass(aClass.getClassId(), aClass.getCourseAcr(), aClass.getSemester()),
                                fieldset(
                                        legend(text("Insert a Teacher into this Class")),
                                        text("Teacher ID"),
                                        br(),
                                        div(
                                                textInputWithCss("numDoc")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        br(),
                                        div(
                                            submitInputWithCss("submit")
                                        ).withAttr("class","form-group")
                                )
                        ),
                        br(),
                        p(
                                text("Return to all Classes of "),
                                a(UrlTo.allAcademicClassesFromCourse(aClass.getCourseAcr()),aClass.getCourseAcr())
                        )
                ).withAttr("class","container")

        );
        page.writeTo(w);
    }

    private Writable getTeacherDivOrHeading() {
        List<Integer> lecturingTeachers = aClass.getTeachers();
        if (lecturingTeachers.isEmpty())
            return h3(text("There aren't any teachers lecturing this class!"));
        HtmlElem div = div();
        div.withContent(h3(text("Teachers lecturing this class: ")));
        HtmlElem list = (HtmlElem)ul();
        for (int id : lecturingTeachers){
            list.withContent(li(a(UrlTo.teacher(id), id+"")));
        }
        div.withContent(list);
        return div;
    }

    private Writable getStudentDivOrHeading() {
        List<Integer> registeredStudents = aClass.getAttendingStudents();
        if (registeredStudents.isEmpty())
            return h3(text("There aren't any students registered in this class!"));
        HtmlElem div = div();
        div.withContent(h3(text("Students registered in this class: ")));
        HtmlElem list = (HtmlElem)ul();
        for (int id : registeredStudents){
            list.withContent(li(a(UrlTo.student(id), id+"")));
        }
        div.withContent(list);
        return div;
    }
}
