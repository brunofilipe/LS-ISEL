package pt.isel.ls.academicActivities.view.model.course;

import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class CoursesView implements Writable {
    private List<Course> courses;

    public CoursesView(List<IEntity> courses) {
        this.courses = courses
                .stream()
                .map(entity -> (Course)entity)
                .collect(Collectors.toList());
    }

    public String getView() {
        StringBuilder view = new StringBuilder();
        courses.forEach(course -> {
            view.append(String.format(" %s: %s\n", "Name", course.getCourseName()));
            view.append(String.format(" %s: %s\n", "Course Acronym", course.getCourseAcr()));
            view.append(String.format(" %s: %d\n", "Coordinator Id", course.getCoordinatorId()));
            view.append("\n");
        });
        return view.toString();
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(getView());
    }
}
