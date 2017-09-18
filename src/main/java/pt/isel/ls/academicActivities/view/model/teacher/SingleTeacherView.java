package pt.isel.ls.academicActivities.view.model.teacher;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Teacher;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class SingleTeacherView implements Writable {
    private Teacher teacher;

    public SingleTeacherView(IEntity teacher){
        this.teacher = (Teacher)teacher;
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append(String.format(" %s: %s\n", "Teacher name", teacher.getTeacherName()));
        view.append(String.format(" %s: %s\n", "Teacher email", teacher.getTeacherEmail()));
        view.append(String.format(" %s: %d\n", "Teacher Id", teacher.getTeacherId()));
        view.append("\n");
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
