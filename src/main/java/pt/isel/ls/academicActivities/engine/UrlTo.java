package pt.isel.ls.academicActivities.engine;

public class UrlTo {

    public static String homepage() {
        return "/";
    }

    public static String allProgrammes() {
        return "/programmes";
    }

    public static String allStudents() {
        return "/students";
    }

    public static String allTeachers() {
        return "/teachers";
    }

    public static String allUsers() {
        return "/users";
    }

    public static String allCourses() {
        return "/courses";
    }

    public static String course(String courseAcr) {
        return "/courses/" + courseAcr;
    }

    public static String teacher(int coordinatorId) {
        return "/teachers/" + coordinatorId;
    }

    public static String academicClass(String classId, String courseAcr, String sem) {
        return "/courses/" + courseAcr + "/classes/" + sem + "/" + classId;
    }

    public static String programme(String programmeAcr) {
        return "/programmes/" + programmeAcr;
    }

    public static String student(int studentId) {
        return "/students/" + studentId;
    }

    public static String allAcademicClassesFromCourse(String courseAcr) {
        return "/courses/" + courseAcr + "/classes";
    }

    public static String postCourse() {
        return "/courses";
    }

    public static String postStudent() {
        return "/students";
    }

    public static String postTeacher() {
        return "/teachers";
    }

    public static String postProgramme() {
        return "/programmes";
    }

    public static String postClassIntoCourse(String courseAcr) {
        return "/courses/" + courseAcr + "/classes";
    }

    public static String postCourseIntoProgramme(String programmePid) {
        return "/programmes/" + programmePid + "/courses";
    }

    public static String postTeacherIntoClass(String classId, String courseAcr, String sem) {
        return "/courses/" + courseAcr + "/classes/" + sem + "/" + classId + "/teachers";
    }

    public static String postStudentIntoClass(String classId, String courseAcr, String sem) {
        return "/courses/" + courseAcr + "/classes/" + sem + "/" + classId + "/students";
    }
}
