package pt.isel.ls.academicActivities.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Course implements IEntity {
    private String courseName;
    private String courseAcr;
    private int coordinatorId;
    private List<String> programmes;
    private List<Class> classes;

    public Course(String courseName, String courseAcr, int coordinatorId) {
        this.courseName = courseName;
        this.courseAcr = courseAcr;
        this.coordinatorId = coordinatorId;
        this.programmes = new LinkedList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseAcr() {
        return courseAcr;
    }

    public void setCourseAcr(String courseAcr) {
        this.courseAcr = courseAcr;
    }

    public int getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(int coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public List<String> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(List<String> programmes) {
        this.programmes = programmes;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<IEntity> classes) {
        this.classes = classes
                .stream()
                .map(entity -> (Class) entity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object object) {
        Course course = (Course) object;
        return this.coordinatorId == course.coordinatorId &
                this.courseName.equals(course.courseName) &
                this.courseAcr.equals(course.courseAcr);
    }

}
