package pt.isel.ls.academicActivities.view.model.academicClass;

import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;
import pt.isel.ls.academicActivities.model.Class;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class SingleClassView implements Writable {
    private Class aClass;

    public SingleClassView(IEntity aClass) {
        this.aClass = (Class)aClass;
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append(String.format(" %s: %s\n", "Class Id", aClass.getClassId()));
        view.append(String.format(" %s: %s\n", "Course Acronym", aClass.getCourseAcr()));
        view.append(String.format(" %s: %s\n", "Academic Year", aClass.getAcademicSemesterYear()));
        view.append(String.format(" %s: %s\n", "Academic Semester", aClass.getAcademicSemesterType()));
        view.append("\n");
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
