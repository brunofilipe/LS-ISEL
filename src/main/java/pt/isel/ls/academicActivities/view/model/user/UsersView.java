package pt.isel.ls.academicActivities.view.model.user;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class UsersView implements Writable {
    private List<IEntity> users;

    public UsersView(List<IEntity> users) {
        this.users = users;
    }

    private String getView() {
        StringBuilder view = new StringBuilder();
        users.forEach(user -> {
            if ( user instanceof Teacher) {
                Teacher teacher = (Teacher)user;
                view.append(String.format(" %s: %s\n", "Teacher name", teacher.getTeacherName()));
                view.append(String.format(" %s: %s\n", "Teacher email", teacher.getTeacherEmail()));
                view.append(String.format(" %s: %d\n", "Teacher Id", teacher.getTeacherId()));
                view.append("\n");
            } else {
                Student student = (Student)user;
                view.append(String.format(" %s: %s\n", "Student Name", student.getStudentName()));
                view.append(String.format(" %s: %s\n", "Student Email", student.getStudentEmail()));
                view.append(String.format(" %s: %s\n", "Student ID", student.getStudentId()));
                view.append(String.format(" %s: %s\n", "Programme Acr", student.getProgrammeAcr()));
                view.append("\n");
            }
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
