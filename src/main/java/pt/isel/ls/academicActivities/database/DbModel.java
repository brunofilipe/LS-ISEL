package pt.isel.ls.academicActivities.database;

import pt.isel.ls.academicActivities.database.data.*;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.model.*;
import pt.isel.ls.academicActivities.model.Class;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbModel {
    protected static final int DEFAULT_TOP = 10000;
    protected static final int DEFAULT_SKIP = 0;

    protected static Programme instantiateProgramme(Connection connection, ResultSet resultSet) throws SQLException, CommandException {
        Programme programme = new Programme(resultSet.getString("programmeName"),
                resultSet.getString("programmeAcr"),
                resultSet.getInt("programmeSemesters")
        );
        programme.setCourses(DbProgrammeStructure.selectProgrammeStructuresOfSpecificProgramme(connection, programme.getProgrammeAcr(), DEFAULT_SKIP, DEFAULT_TOP, false));
        return programme;
    }

    protected static Teacher instantiateTeacher(Connection connection, ResultSet resultSet) throws SQLException, CommandException {
        Teacher teacher = new Teacher(resultSet.getString("teacherName"),
                resultSet.getString("teacherEmail"),
                resultSet.getInt("teacherId"));
        teacher.setClassesTaught(DbClass.selectClassesTaughtByTeacher(connection, teacher.getTeacherId(), DEFAULT_SKIP, DEFAULT_TOP, false));
        teacher.setCoursesCoordinated(DbCourse.selectCourseAcrsCoordinatedByTeacher(connection, teacher.getTeacherId(),DEFAULT_SKIP, DEFAULT_TOP, false));
        return teacher;
    }

    protected static Student instantiateStudent(Connection connection, ResultSet resultSet) throws SQLException, CommandException {
        Student student = new Student(resultSet.getString("studentName"),
                resultSet.getString("studentEmail"),
                resultSet.getInt("studentId"),
                resultSet.getString("programmeAcr"));
        student.setAttendingClasses(DbClass.selectClassesAttendedByStudent(connection, student.getStudentId(), DEFAULT_SKIP, DEFAULT_TOP, false));
        return student;
    }

    protected static Course instantiateCourse(Connection connection, ResultSet resultSet) throws SQLException, CommandException {
        Course course = new Course(resultSet.getString("courseName"),
                resultSet.getString("courseAcr"),
                resultSet.getInt("coordinatorId")
        );
        course.setProgrammes(DbProgrammeStructure.selectProgrammeAcronymsOfSpecificCourse(connection, course.getCourseAcr(), false));
        course.setClasses(DbClass.selectClassesOfCourse(connection, course.getCourseAcr(), DEFAULT_SKIP, DEFAULT_TOP, false));
        return course;
    }

    protected static Class instantiateClass(Connection connection, ResultSet resultSet) throws SQLException, CommandException {
        Class academicClass = new Class(resultSet.getString("classId"),
                resultSet.getString("courseAcr"),
                resultSet.getString("academicSemesterYear"),
                resultSet.getString("academicSemesterType")
        );
        academicClass.setAttendingStudents(DbStudent.selectStudentIdsOfClass(connection, academicClass.getCourseAcr(), academicClass.getAcademicSemesterYear(),
                academicClass.getAcademicSemesterType(), academicClass.getClassId(), false));
        academicClass.setTeachers(DbTeacher.selectTeacherIdsFromClass(connection, academicClass.getCourseAcr(), academicClass.getAcademicSemesterYear(),
                academicClass.getAcademicSemesterType(), academicClass.getClassId(), false));
        return academicClass;
    }

    protected static ProgrammeStructure instantiateProgrammeStructure(ResultSet resultSet) throws SQLException {
        return new ProgrammeStructure(resultSet.getString("courseAcr"),
                resultSet.getString("programmeAcr"),
                resultSet.getInt("courseAvailabilitySemester"),
                resultSet.getBoolean("hasMandatory"));
    }
}
