package pt.isel.ls.academicActivities.database.data;

import pt.isel.ls.academicActivities.database.DbModel;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.SelectException;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Student;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbStudent extends DbModel {

    public static int insertStudent(Connection connection, Student student) throws SQLException, CommandException {
        String insertStudent = "insert into Student values (? , ?, ?, ?)";
        PreparedStatement psm = connection.prepareStatement(insertStudent, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, student.getStudentName());
        psm.setString(2, student.getStudentEmail());
        psm.setInt(3, student.getStudentId());
        psm.setString(4, student.getProgrammeAcr());
        return psm.executeUpdate();
    }

    public static int insertStudentIntoClass(Connection connection, int studentId, String classId, String courseAcr, String semYear, String semType) throws SQLException, CommandException {
        selectStudent(connection, studentId);
        String insertStudent = "insert into Belongs values (?, ?, ?, ?, ?)";
        PreparedStatement psmToInsert = connection.prepareStatement(insertStudent);
        psmToInsert.setString(1, classId);
        psmToInsert.setString(2, courseAcr);
        psmToInsert.setString(3, semYear);
        psmToInsert.setString(4, semType);
        psmToInsert.setInt(5, studentId);
        return psmToInsert.executeUpdate();
    }

    public static int deleteStudentFromClass(Connection connection, int studentId, String classId, String courseAcr, String semYear, String semType) throws SQLException, CommandException {
        String deleteStd = "delete from Belongs where ( classId = ? and courseAcr = ? and academicSemesterYear = ? and academicSemesterType = ? and studentId = ?)";
        PreparedStatement psm = connection.prepareStatement(deleteStd, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, classId);
        psm.setString(2, courseAcr);
        psm.setString(3, semYear);
        psm.setString(4, semType);
        psm.setInt(5, studentId);
        int status = psm.executeUpdate();
        if (status == 0)
            throw new SelectException("Couldn't delete the specified student! Are you sure it exists in the database?");
        return status;
    }

    public static int updateStudent(Connection connection, int id, String name, String email, String programmeAcr) throws SQLException, CommandException {
        String updateStudent = "update Student set studentName = ? , studentEmail = ? , programmeAcr = ? where studentId = ?";
        PreparedStatement psm = connection.prepareStatement(updateStudent, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, name);
        psm.setString(2, email);
        psm.setString(3, programmeAcr);
        psm.setInt(4, id);
        int status = psm.executeUpdate();
        if (status == 0)
            throw new SelectException("Couldn't update the specified student! Are you sure it exists in the database?");
        return status;
    }

    public static IEntity selectStudent(Connection connection, int id) throws SQLException, CommandException {
        String selectStudent = "select * from Student where studentId = ?";
        PreparedStatement psm = connection.prepareStatement(selectStudent);
        psm.setInt(1, id);
        ResultSet resultSet = psm.executeQuery();
        if (resultSet.next()) {
            return instantiateStudent(connection, resultSet);
        }
        throw new SelectException("Couldn't find a student with the specified ID!");
    }

    public static List<IEntity> selectStudents(Connection connection, int skip, int top) throws SQLException, CommandException {
        return selectStudents(connection, skip, top, true);
    }

    public static List<IEntity> selectStudents(Connection connection, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectStudents = "select top (?) studentName, studentEmail, studentId, programmeAcr " +
                "from ( " +
                "select *, row_number() over (order by studentId) as row from Student ) " +
                "Student " +
                "where row> (?) ";
        PreparedStatement psm = connection.prepareStatement(selectStudents);
        psm.setInt(1, top);
        psm.setInt(2, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> students = new LinkedList<>();
        while (resultSet.next()) {
            students.add(instantiateStudent(connection, resultSet));
        }
        if (forbidEmptyList && students.isEmpty())
            throw new SelectException("No students found!");
        return students;
    }

    public static List<IEntity> selectStudentsOfClass(Connection connection, String courseAcr, String semYear, String semType, String classId, int skip, int top) throws SQLException, CommandException {
        return selectStudentsOfClass(connection, courseAcr, semYear, semType, classId, skip, top, true);
    }

    public static List<IEntity> selectStudentsOfClass(Connection connection, String courseAcr, String semYear, String semType, String classId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectStudents = "select top (?) studentName, studentEmail, studentId, programmeAcr " +
                "from ( " +
                    "select *, row_number() over (order by studentId) as row " +
                    "from Student " +
                    "where studentId in ( " +
                        "select studentId from Belongs where (courseAcr = ? and academicSemesterYear = ? " +
                        "and academicSemesterType = ? and classId = ? ) ) ) " +
                "Student " +
                "where row > (?) ";
        PreparedStatement psm = connection.prepareStatement(selectStudents);
        psm.setInt(1, top);
        psm.setString(2, courseAcr);
        psm.setString(3, semYear);
        psm.setString(4, semType);
        psm.setString(5, classId);
        psm.setInt(6, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> students = new LinkedList<>();
        while (resultSet.next()) {
            students.add(instantiateStudent(connection, resultSet));
        }
        if (forbidEmptyList && students.isEmpty())
            throw new SelectException("No students found in this class!");
        return students;
    }

    public static List<Integer> selectStudentIdsOfClass(Connection connection, String courseAcr, String semYear, String semType, String classId) throws SQLException, CommandException {
        return selectStudentIdsOfClass(connection, courseAcr, semYear, semType, classId, true);
    }

    public static List<Integer> selectStudentIdsOfClass(Connection connection, String courseAcr, String semYear, String semType, String classId, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectStudents = "select studentId " +
                "from Student " +
                "where studentId in ( " +
                    "select studentId from Belongs where (courseAcr = ? and  " +
                    "academicSemesterYear = ? and " +
                    "academicSemesterType = ? and classId = ?))";
        PreparedStatement psm = connection.prepareStatement(selectStudents);
        psm.setString(1, courseAcr);
        psm.setString(2, semYear);
        psm.setString(3, semType);
        psm.setString(4, classId);
        ResultSet resultSet = psm.executeQuery();
        List<Integer> students = new LinkedList<>();
        while (resultSet.next()) {
            students.add(resultSet.getInt("studentId"));
        }
        if (forbidEmptyList && students.isEmpty())
            throw new SelectException("No students found in this class!");
        return students;
    }

    public static List<IEntity> selectStudentsOfClassOrderedByNumber(Connection connection, String courseAcr, String semYear, String semType, String classId, int skip, int top) throws SQLException, CommandException {
        return selectStudentsOfClassOrderedByNumber(connection, courseAcr, semYear, semType, classId, skip, top, true);
    }

    public static List<IEntity> selectStudentsOfClassOrderedByNumber(Connection connection, String courseAcr, String semYear, String semType, String classId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectStudents = "select top (?) studentName, studentEmail, studentId, programmeAcr " +
                "from ( " +
                    "select *, row_number() over (order by studentId) as row " +
                    "from Student " +
                    "where studentId in ( " +
                        "select studentId from Belongs where (courseAcr = ? and academicSemesterYear = ? " +
                        "and academicSemesterType = ? and classId = ? ) ) ) " +
                "Student " +
                "where row >(?) ";
        PreparedStatement psm = connection.prepareStatement(selectStudents);
        psm.setInt(1, top);
        psm.setString(2, courseAcr);
        psm.setString(3, semYear);
        psm.setString(4, semType);
        psm.setString(5, classId);
        psm.setInt(6, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> students = new LinkedList<>();
        while (resultSet.next()) {
            students.add(instantiateStudent(connection, resultSet));
        }
        if (forbidEmptyList && students.isEmpty())
            throw new SelectException("No students found!");
        return students;
    }
}
