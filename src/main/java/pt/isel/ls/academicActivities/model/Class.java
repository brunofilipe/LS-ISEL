package pt.isel.ls.academicActivities.model;

import java.util.LinkedList;
import java.util.List;

public class Class implements IEntity {
    private String classId;
    private String courseAcr;
    private String semester;
    private String academicSemesterYear;
    private String academicSemesterType;
    private List<Integer> attendingStudents;
    private List<Integer> teachers;

    public Class(String classId, String courseAcr, String academicSemesterYear, String academicSemesterType) {
        this.classId = classId;
        this.courseAcr = courseAcr;
        this.academicSemesterYear = academicSemesterYear;
        this.academicSemesterType = academicSemesterType;
        this.semester = concatSemesters(academicSemesterYear, academicSemesterType);
        this.attendingStudents = new LinkedList<>();
        this.teachers = new LinkedList<>();
    }

    private String concatSemesters(String academicSemesterYear, String academicSemesterType) {
        StringBuilder semester = new StringBuilder();
        semester.append(academicSemesterYear);
        semester.append((academicSemesterType.equals("winter")) ? "i" : "v");
        return semester.toString();
    }

    public List<Integer> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Integer> teachers) {
        this.teachers = teachers;
    }

    public List<Integer> getAttendingStudents() {
        return attendingStudents;
    }

    public void setAttendingStudents(List<Integer> attendingStudents) {
        this.attendingStudents = attendingStudents;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseAcr() {
        return courseAcr;
    }

    public void setCourseAcr(String courseAcr) {
        this.courseAcr = courseAcr;
    }

    public String getAcademicSemesterYear() {
        return academicSemesterYear;
    }

    public void setAcademicSemesterYear(String academicSemesterYear) {
        this.academicSemesterYear = academicSemesterYear;
    }

    public String getAcademicSemesterType() {
        return academicSemesterType;
    }

    public void setAcademicSemesterType(String academicSemesterType) {
        this.academicSemesterType = academicSemesterType;
    }

    @Override
    public boolean equals(Object obj) {
        Class cl = (Class) obj;
        return this.academicSemesterType.equals(cl.academicSemesterType) &
                this.academicSemesterYear.equals(cl.academicSemesterYear) &
                this.classId.equals(cl.classId) &
                this.courseAcr.equals(cl.courseAcr);
    }
}
