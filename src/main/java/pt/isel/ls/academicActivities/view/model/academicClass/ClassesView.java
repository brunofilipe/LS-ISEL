package pt.isel.ls.academicActivities.view.model.academicClass;

import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class ClassesView implements Writable {
    private List<Class> classes;

    public ClassesView(List<IEntity> classes) {
        this.classes = classes
                .stream()
                .map(entity -> (Class)entity)
                .collect(Collectors.toList());
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        classes.forEach(cl->{
            view.append(String.format(" %s: %s\n", "Class Id", cl.getClassId()));
            view.append(String.format(" %s: %s\n", "Course Acronym", cl.getCourseAcr()));
            view.append(String.format(" %s: %s\n", "Academic Year", cl.getAcademicSemesterYear()));
            view.append(String.format(" %s: %s\n", "Academic Semester", cl.getAcademicSemesterType()));
            view.append("\n");
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
