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

public class GetClassesOfCourseOnSemesterViewHtml implements Writable {
    private List<Class> classes;

    public GetClassesOfCourseOnSemesterViewHtml(List<IEntity> classes) {
        this.classes = classes
                .stream()
                .map(entity -> (Class)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Classes of the course " +
                classes.get(0).getCourseAcr() + " on " +
                classes.get(0).getAcademicSemesterYear() + " " +
                classes.get(0).getAcademicSemesterType();
        HtmlElem tableBody = new HtmlElem("tbody");
        classes.forEach(academicClass -> tableBody.withContent(
                tr(
                        td(a(UrlTo.academicClass(academicClass.getClassId(), academicClass.getCourseAcr(), academicClass.getSemester()), academicClass.getClassId())),
                        td(a(UrlTo.course(academicClass.getCourseAcr()), academicClass.getCourseAcr())),
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
                                            th(text("course acronym")),
                                            th(text("Semester year")),
                                            th(text("Semester type"))
                                    )
                            ),
                            tableBody
                    ).withAttr("border","3").withAttr("class", "table")
                ).withAttr("class","container")

        );
        page.writeTo(w);
    }
}
