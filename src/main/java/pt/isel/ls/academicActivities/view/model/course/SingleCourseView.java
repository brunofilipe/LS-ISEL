package pt.isel.ls.academicActivities.view.model.course;

import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.stream.Collectors;

public class SingleCourseView implements Writable {
    private Course course;

    public SingleCourseView(IEntity course) {
        this.course = (Course)course;
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        view.append(String.format(" %s: %s\n", "Name", course.getCourseName()));
        view.append(String.format(" %s: %s\n", "Course Acronym", course.getCourseAcr()));
        view.append(String.format(" %s: %d\n", "Coordinator Id", course.getCoordinatorId()));
        view.append("\n");
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
