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
import java.util.stream.Collectors;

import static pt.isel.ls.academicActivities.utils.html.Html.*;
import static pt.isel.ls.academicActivities.utils.html.Html.text;
import static pt.isel.ls.academicActivities.utils.html.Html.th;

public class GetAllCoursesViewHtml implements Writable{
    private List<Course>courseList;

    public GetAllCoursesViewHtml(List<IEntity> courseList){
        this.courseList = courseList
                .stream()
                .map(entity -> (Course)entity)
                .collect(Collectors.toList());
    }
    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Existing courses :  ";
        HtmlElem tableBody = new HtmlElem("tbody");
        courseList.forEach(course -> tableBody.withContent(
                tr(
                        td(text(course.getCourseName())),
                        td(a(UrlTo.course(course.getCourseAcr()), course.getCourseAcr())),
                        td(text(course.getCoordinatorId()+""))
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
                                                th(text("Name")),
                                                th(text("Acronym")),
                                                th(text("Coordinator ID"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1")
                        .withAttr("class","table"),
                        br(),
                        formWithCss("POST", UrlTo.postCourse(),
                                fieldset(
                                        legend(text("Insert a new Course")),
                                        text("Name"),
                                        br(),
                                        div(
                                            textInputWithCss("name")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Acronym"),
                                        br(),
                                        div(
                                            textInputWithCss("acr")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Coordinator ID"),
                                        br(),
                                        div(
                                            inputWithCss("teacher", "number")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        br(),
                                        div(
                                            submitInputWithCss("submit")
                                        ).withAttr("class","form-group")
                                )
                        ),
                        br(),
                        p(text("Return to the "), a(UrlTo.homepage(),"Homepage"))
                ).withAttr("class","container")
        );
        page.writeTo(w);
    }
}
