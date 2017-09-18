package pt.isel.ls.academicActivities.view.model.student;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class SingleStudentView implements Writable {
    private Student student;

    public SingleStudentView(IEntity student){
        this.student = (Student)student;    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append(String.format(" %s: %s\n", "Student Name", student.getStudentName()));
        view.append(String.format(" %s: %s\n", "Student Email", student.getStudentEmail()));
        view.append(String.format(" %s: %s\n", "Student ID", student.getStudentId()));
        view.append(String.format(" %s: %s\n", "Programme Acr", student.getProgrammeAcr()));
        view.append("\n");
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
