package pt.isel.ls.academicActivities.model;

import java.util.List;
import java.util.stream.Collectors;

public class Student implements IEntity {
    private String studentName;
    private String studentEmail;
    private int studentId;
    private String programmeAcr;
    private List<Class> attendingClasses;

    public Student(String studentName, String studentEmail, int studentId, String programmeAcr) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentId = studentId;
        this.programmeAcr = programmeAcr;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getProgrammeAcr() {
        return programmeAcr;
    }

    public void setProgrammeAcr(String programmeAcr) {
        this.programmeAcr = programmeAcr;
    }

    public List<Class> getAttendingClasses() {
        return attendingClasses;
    }

    public void setAttendingClasses(List<IEntity> attendingClasses) {
        this.attendingClasses = attendingClasses
                .stream()
                .map(entity -> (Class) entity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        Student std = (Student) obj;
        return this.studentName.equals(std.studentName) &
                this.programmeAcr.equals(std.programmeAcr) &
                this.studentEmail.equals(std.studentEmail) &
                this.studentId == std.studentId;
    }
}
