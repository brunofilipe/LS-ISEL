package pt.isel.ls.academicActivities.view.model.student;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsView implements Writable {
    private List<Student> students;

    public StudentsView(List<IEntity> students){
        this.students = students
                .stream()
                .map(entity -> (Student)entity)
                .collect(Collectors.toList());    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        students.forEach(student -> {
            view.append(String.format(" %s: %s\n", "Student Name", student.getStudentName()));
            view.append(String.format(" %s: %s\n", "Student Email", student.getStudentEmail()));
            view.append(String.format(" %s: %s\n", "Student ID", student.getStudentId()));
            view.append(String.format(" %s: %s\n", "Programme Acr", student.getProgrammeAcr()));
            view.append("\n");
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
