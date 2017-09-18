package pt.isel.ls.academicActivities.view.commands.student;

import pt.isel.ls.academicActivities.engine.UrlTo;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
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

public class GetStudentsOfClassOrderedByNumberViewHtml implements Writable {
    private List<Student> students;

    public GetStudentsOfClassOrderedByNumberViewHtml(List<IEntity> students){
        this.students = students
                .stream()
                .map(entity -> (Student)entity)
                .collect(Collectors.toList());
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        String title = "LS - AcademicActivities";
        String header = "Existing students, sorted by their ID, that belong to the class given in the parameters :  ";
        HtmlElem tableBody = new HtmlElem("tbody");
        students.forEach(st -> tableBody.withContent(
                tr(
                        td(a( UrlTo.student(st.getStudentId()), st.getStudentId()+"")),
                        td(text(st.getStudentName())),
                        td(text(st.getStudentEmail()+"")),
                        td(a( UrlTo.programme(st.getProgrammeAcr()), st.getProgrammeAcr()))
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
                                                th(text("student ID")),
                                                th(text("student Name")),
                                                th(text("student Email")),
                                                th(text("Programm Acronym"))
                                        )
                                ),
                                tableBody
                        ).withAttr("border","1").withAttr("class","table")
                ).withAttr("class","container")

        );
        page.writeTo(w);
    }
}
