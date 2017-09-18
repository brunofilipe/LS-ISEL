package pt.isel.ls.academicActivities.view;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.utils.html.HtmlPage;

import java.io.IOException;
import java.io.Writer;

import static pt.isel.ls.academicActivities.utils.html.Html.*;

public class HomeView implements Writable {

    @Override
    public void writeTo(Writer w) throws IOException {
        HtmlPage page = new HtmlPage("LS - AcademicActivities",
                "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css",
                div(
                        h1(text("Academic Activities Homepage")),
                        p(text("Please choose one of the following options to navigate through the system")),
                        ul(
                                li(a(UrlTo.allCourses(), "Courses")),
                                li(a(UrlTo.allProgrammes(), "Programmes")),
                                li(a(UrlTo.allStudents(), "Students")),
                                li(a(UrlTo.allTeachers(), "Teachers")),
                                li(a(UrlTo.allUsers(), "Users"))
                        ),
                        div(
                                p(text("Web Application Developed in Software Laboratory by:")),
                                ul(
                                        li(text("Bruno Filipe - 41484")),
                                        li(text("Nuno Pinto - 41529")),
                                        li(text("João Gameiro - 41893"))
                                )
                        ).withAttr("style", "position:fixed;bottom:30px;right:30px")
                ).withAttr("class", "container"));
        page.writeTo(w);
    }
}
