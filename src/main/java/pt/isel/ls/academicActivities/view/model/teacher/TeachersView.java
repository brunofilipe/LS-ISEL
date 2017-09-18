package pt.isel.ls.academicActivities.view.model.teacher;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class TeachersView implements Writable {
    private List<Teacher> teachers;

    public TeachersView(List<IEntity> teachers){
        this.teachers = teachers
                .stream()
                .map(entity -> (Teacher)entity)
                .collect(Collectors.toList());
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        teachers.forEach(teacher -> {
            view.append(String.format(" %s: %s\n", "Teacher name", teacher.getTeacherName()));
            view.append(String.format(" %s: %s\n", "Teacher email", teacher.getTeacherEmail()));
            view.append(String.format(" %s: %d\n", "Teacher Id", teacher.getTeacherId()));
            view.append("\n");
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
