package pt.isel.ls.academicActivities.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Teacher implements IEntity {
    private String teacherName;
    private String teacherEmail;
    private int teacherId;
    private List<String> coursesCoordinated;
    private List<Class> classesTaught;

    public Teacher(String teacherName, String teacherEmail, int teacherId) {
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherId = teacherId;
        this.coursesCoordinated = new LinkedList<>();
        this.classesTaught = new LinkedList<>();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<String> getCoursesCoordinated() {
        return coursesCoordinated;
    }

    public void setCoursesCoordinated(List<String> coursesCoordinated) {
        this.coursesCoordinated = coursesCoordinated;
    }

    public List<Class> getClassesTaught() {
        return classesTaught;
    }

    public void setClassesTaught(List<IEntity> classesTaught) {
        this.classesTaught = classesTaught
                .stream()
                .map(entity -> (Class) entity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        Teacher teacher = (Teacher) obj;
        return this.teacherEmail.equals(teacher.teacherEmail) &
                this.teacherName.equals(teacher.teacherName) &
                this.teacherId == teacher.teacherId;
    }
}
