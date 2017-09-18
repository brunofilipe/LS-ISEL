package pt.isel.ls.academicActivities.view.commands.programme;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlElem;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class GetSpecificProgrammeViewHtml implements Writable {
    private Programme programme;

    public GetSpecificProgrammeViewHtml(IEntity programme) {
        this.programme = (Programme)programme;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        Writable courseDivOrHeading = getCourseDivOrHeading();
        HtmlPage page = new HtmlPage(
                title,
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                    h1(text(programme.getProgrammeName())),
                    h3(text("Acronym - " + programme.getProgrammeAcr())),
                    h3(text("Number of Semesters - " + programme.getProgrammeSemesters())),
                    courseDivOrHeading,
                    formWithCss("POST", UrlTo.postCourseIntoProgramme(programme.getProgrammeAcr()),
                            fieldset(
                                    legend(text("Insert a Course into this Programme")),
                                    text("Course Acronym"),
                                    br(),
                                    div(
                                        textInputWithCss("acr")
                                    ).withAttr("class","form-group")),
                                    br(),
                                    text("Course Availability"),
                                    br(),
                                    div(
                                        textInputWithCss("semesters")
                                    ).withAttr("class","form-group"),
                                    br(),
                                    br(),
                                    text("Mandatory"),
                                    br(),
                                    div(
                                            radioInput("mandatory","True")
                                    ).withAttr("class", "radio"),
                                    text("Yes"),
                                    br(),
                                    div(
                                            radioInput("mandatory", "False")
                                    ).withAttr("class", "radio"),
                                    text("No"),
                                    br(),
                                    br(),
                                    div(
                                        submitInputWithCss("submit")
                                    ).withAttr("class","form-group")
                            ),
                    br(),
                    p(text("Return to the "), a(UrlTo.allProgrammes(),"Programme list"))
                ).withAttr("class","container")
        );
        page.writeTo(w);
    }

    private Writable getCourseDivOrHeading(){
        List<ProgrammeStructure> courses = programme.getCourses();
        if ( courses.isEmpty() )
            return h3(text("There aren't any courses registered to this programme!"));
        HtmlElem div = div();
        div.withContent(h3(text("Courses lectured in this programme")));
        HtmlElem tableBody = new HtmlElem("tbody");
        courses.forEach(course -> tableBody.withContent(
                tr(
                        td(a(UrlTo.course(course.getCourseAcr()), course.getCourseAcr())),
                        td(text(course.getCourseAvailabilitySemester()+"")),
                        td(text(course.getHasMandatory()+""))
                )
        ));
        div.withContent(table(
                new HtmlElem("thead",
                        tr(
                                th(text("CourseAcr")),
                                th(text("Semester lectured")),
                                th(text("Mandatory"))
                        )
                ),
                tableBody
        ).withAttr("border","1").withAttr("class","table"));
        /*HtmlElem list = (HtmlElem)ul();
        for (String courseAcr : courses){
            list.withContent(li(a(UrlTo.course(courseAcr),courseAcr)));
        }
        div.withContent(list);*/
        return div;
    }
}
