package pt.isel.ls.academicActivities.view.commands.course;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class GetSpecificCourseViewHtml implements Writable {
    private Course course;

    public GetSpecificCourseViewHtml(IEntity course) {
        this.course = (Course) course;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        Writable programmeDivOrHeading = getProgrammeDivOrHeading();
        Writable classDivOrHeading = getClassDivOrHeading();
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h1(text(course.getCourseName())),
                        h3(text("Acronym - " + course.getCourseAcr())),
                        h3(text("Coordinator ID - "), a(UrlTo.teacher(course.getCoordinatorId()), course.getCoordinatorId() + "")),
                        classDivOrHeading,
                        programmeDivOrHeading,
                        p(text("Return to the "), a(UrlTo.allCourses(), "Course list"))
                ).withAttr("class", "container")
        );
        page.writeTo(w);
    }

    private Writable getProgrammeDivOrHeading() {
        List<String> programmes = course.getProgrammes();
        if (programmes.isEmpty())
            return h3(text("This course isn't registered in any programmes!"));
        HtmlElem div = div();
        div.withContent(h3(text("Programmes where the course is taught")));
        HtmlElem list = (HtmlElem) ul();
        for (String programmeAcr : programmes) {
            list.withContent(li(a(UrlTo.programme(programmeAcr), programmeAcr)));
        }
        div.withContent(list);
        return div;
    }

    private Writable getClassDivOrHeading() {
        if (course.getClasses().isEmpty()) {
            HtmlElem div = div();
            div.withContent(h3(text("There are no classes lecturing this course!")));
            div.withContent(
                    formWithCss("POST", UrlTo.postClassIntoCourse(course.getCourseAcr()),
                            fieldset(
                                    legend(text("Insert a new Class to this Course")),
                                    text("Class ID"),
                                    br(),
                                    div(
                                            textInputWithCss("num")
                                    ).withAttr("class", "form-group"),
                                    br(),
                                    text("Semester"),
                                    br(),
                                    div(
                                            textInputWithCss("sem")
                                    ).withAttr("class", "form-group"),
                                    br(),
                                    br(),
                                    div(
                                            submitInputWithCss("submit")
                                    ).withAttr("class", "form-group")
                            )
                    )
            );
            return div;
        } else
            return h3(a(UrlTo.allAcademicClassesFromCourse(course.getCourseAcr()), "Classes"), text("that lecture this course"));
    }
}
