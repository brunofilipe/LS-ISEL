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
import java.util.stream.Collectors;

import static pt.isel.ls.academicActivities.utils.html.Html.*;
import static pt.isel.ls.academicActivities.utils.html.Html.text;
import static pt.isel.ls.academicActivities.utils.html.Html.th;

public class GetClassesOfCourseViewHtml implements Writable {
    private List<Class> classes;

    public GetClassesOfCourseViewHtml(List<IEntity> classes) {
        this.classes = classes
                .stream()
                .map(entity -> (Class)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Classes from the course " + classes.get(0).getCourseAcr();
        HtmlElem tableBody = new HtmlElem("tbody");
        classes.forEach(academicClass -> tableBody.withContent(
                tr(
                        td(a(UrlTo.academicClass(academicClass.getClassId(), academicClass.getCourseAcr(), academicClass.getSemester()), academicClass.getClassId())),
                        td(text(academicClass.getAcademicSemesterYear())),
                        td(text(academicClass.getAcademicSemesterType()))
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
                                                th(text("ClassId")),
                                                th(text("Semester year")),
                                                th(text("Semester type"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1").withAttr("class","table"),
                        br(),
                        formWithCss("POST", UrlTo.postClassIntoCourse(classes.get(0).getCourseAcr()),
                                fieldset(
                                        legend(text("Insert a new Class to this Course")),
                                        text("Class ID"),
                                        br(),
                                        div(
                                                textInputWithCss("num")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        text("Semester"),
                                        br(),
                                        div(
                                                textInputWithCss("sem")
                                        ).withAttr("class","form-group"),
                                        br(),
                                        br(),
                                        div(
                                                submitInputWithCss("submit")
                                        ).withAttr("class","form-group")
                                )
                        ),
                        br(),
                        p(text("Return to "), a(UrlTo.course(classes.get(0).getCourseAcr()), classes.get(0).getCourseAcr()))
                ).withAttr("class","container")
        );
        page.writeTo(w);
    }
}
