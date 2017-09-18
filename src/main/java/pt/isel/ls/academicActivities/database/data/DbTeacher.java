package pt.isel.ls.academicActivities.database.data;

import pt.isel.ls.academicActivities.database.DbModel;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.SelectException;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Teacher;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbTeacher extends DbModel {

    public static int insertTeacher(Connection connection, Teacher teacher) throws SQLException, CommandException {
        String insertTeacher = "insert into Teacher values (? , ?, ?)";
        PreparedStatement psm = connection.prepareStatement(insertTeacher, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, teacher.getTeacherName());
        psm.setString(2, teacher.getTeacherEmail());
        psm.setInt(3, teacher.getTeacherId());
        return psm.executeUpdate();
    }

    public static int insertTeacherInClass(Connection connection, int teacherId, String classId, String courseAcr, String semYear, String semType) throws SQLException, CommandException {
        selectTeacher(connection, teacherId); //confirming if the teacher exists in the database
        String insertTeacher = "insert into Teaches values (?, ?, ?, ?, ?)";
        PreparedStatement psm = connection.prepareStatement(insertTeacher, Statement.RETURN_GENERATED_KEYS);
        psm.setInt(1, teacherId);
        psm.setString(2, classId);
        psm.setString(3, courseAcr);
        psm.setString(4, semYear);
        psm.setString(5, semType);
        return psm.executeUpdate();
    }

    public static int updateTeacher(Connection connection, int id, String name, String email) throws SQLException, CommandException {
        String updateStudent = "update Teacher set teacherName = ? , teacherEmail = ? where teacherId = ?";
        PreparedStatement psm = connection.prepareStatement(updateStudent, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, name);
        psm.setString(2, email);
        psm.setInt(3, id);
        int status = psm.executeUpdate();
        if (status == 0)
            throw new SelectException("Couldn't update the specified teacher! Are you sure it exists in the database?");
        return status;
    }

    public static IEntity selectTeacher(Connection connection, int id) throws SQLException, CommandException {
        String selectTeachers = "select * from Teacher where teacherId = ?";
        PreparedStatement psm = connection.prepareStatement(selectTeachers);
        psm.setInt(1, id);
        ResultSet resultSet = psm.executeQuery();
        if (resultSet.next()) {
            return instantiateTeacher(connection, resultSet);
        }
        throw new SelectException("Couldn't find a teacher with the specified ID!");
    }

    public static List<IEntity> selectTeachers(Connection connection, int skip, int top) throws SQLException, CommandException {
        return selectTeachers(connection, skip, top, true);
    }

    public static List<IEntity> selectTeachers(Connection connection, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAllTeachers = "select top (?) teacherName, teacherEmail, teacherId " +
                "from ( " +
                    "select *, row_number() over (order by teacherId) as row from Teacher) " +
                "Teacher " +
                "where row > (?)";
        PreparedStatement psm = connection.prepareStatement(selectAllTeachers);
        psm.setInt(1, top);
        psm.setInt(2, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> teachers = new LinkedList<>();
        while (resultSet.next()) {
            teachers.add(instantiateTeacher(connection, resultSet));
        }
        if (forbidEmptyList && teachers.isEmpty())
            throw new SelectException("No teachers found!");
        return teachers;
    }

    public static List<IEntity> selectTeachersFromClass(Connection connection, String courseAcr, String semYear, String semType, String classId, int skip, int top) throws SQLException, CommandException {
        return selectTeachersFromClass(connection, courseAcr, semYear, semType, classId, skip, top, true);
    }

    public static List<IEntity> selectTeachersFromClass(Connection connection, String courseAcr, String semYear, String semType, String classId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAllTeachersForAClass = "select top (?) teacherName, teacherEmail, teacherId " +
                "from ( " +
                    "select *, row_number() over (order by teacherId) as row " +
                    "from Teacher " +
                    "where teacherId in ( " +
                        "select teacherId " +
                        "from Teaches " +
                        "where (courseAcr = ? and academicSemesterYear = ? and academicSemesterType = ? and classId = ? ))) " +
                "Teacher " +
                "where row > (?)";
        
        PreparedStatement psm = connection.prepareStatement(selectAllTeachersForAClass);
        psm.setInt(1, top);
        psm.setString(2, courseAcr);
        psm.setString(3, semYear);
        psm.setString(4, semType);
        psm.setString(5, classId);
        psm.setInt(6, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> teachers = new LinkedList<>();
        while (resultSet.next()) {
            teachers.add(instantiateTeacher(connection, resultSet));
        }
        if (teachers.isEmpty())
            throw new SelectException("The specified class isn't being lectured by any teacher!");
        return teachers;
    }

    public static List<Integer> selectTeacherIdsFromClass(Connection connection, String courseAcr, String semYear, String semType, String classId) throws SQLException, CommandException {
        return selectTeacherIdsFromClass(connection, courseAcr, semYear, semType, classId, true);
    }

    public static List<Integer> selectTeacherIdsFromClass(Connection connection, String courseAcr, String semYear, String semType, String classId, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAllTeachersforAClass = "select teacherId from Teacher where teacherId in ( " +
                "select teacherId from Teaches where (   courseAcr = ? and " +
                "academicSemesterYear = ? and " +
                "academicSemesterType = ? and " +
                "classId = ?)) ";
        PreparedStatement psm = connection.prepareStatement(selectAllTeachersforAClass);
        psm.setString(1, courseAcr);
        psm.setString(2, semYear);
        psm.setString(3, semType);
        psm.setString(4, classId);
        ResultSet resultSet = psm.executeQuery();
        List<Integer> teachers = new LinkedList<>();
        while (resultSet.next()) {
            teachers.add(resultSet.getInt("teacherId"));
        }
        return teachers;
    }
}
