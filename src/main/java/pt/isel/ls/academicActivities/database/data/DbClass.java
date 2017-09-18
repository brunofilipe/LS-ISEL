package pt.isel.ls.academicActivities.database.data;

import pt.isel.ls.academicActivities.database.DbModel;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.SelectException;
import pt.isel.ls.academicActivities.model.Class;
import pt.isel.ls.academicActivities.model.IEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbClass extends DbModel {

    public static int insertClass(Connection connection, Class academicClass) throws SQLException, CommandException {
        String insertClass = "insert into Class values (? , ? , ? , ?)";
        PreparedStatement psmClass = connection.prepareStatement(insertClass, Statement.RETURN_GENERATED_KEYS);
        psmClass.setString(1, academicClass.getClassId());
        psmClass.setString(2, academicClass.getCourseAcr());
        psmClass.setString(3, academicClass.getAcademicSemesterYear());
        psmClass.setString(4, academicClass.getAcademicSemesterType());
        return psmClass.executeUpdate();
    }

    public static IEntity selectClass(Connection connection, String id, String acr, String semYear, String semType) throws SQLException, CommandException {
        String selectClass = "select * from Class where ( courseAcr = ? and academicSemesterYear = ? and academicSemesterType = ? and classId = ? )";
        PreparedStatement psm = connection.prepareStatement(selectClass);
        psm.setString(1, acr);
        psm.setString(2, semYear);
        psm.setString(3, semType);
        psm.setString(4, id);
        ResultSet resultSet = psm.executeQuery();
        if (resultSet.next()) {
            return instantiateClass(connection, resultSet);
        } else
            throw new SelectException("Class not found! You might be inputting bad parameters or the class might not exist in the database.");
    }

    public static List<IEntity> selectClasses(Connection connection, int skip, int top) throws SQLException, CommandException {
        return selectClasses(connection, skip, top, true);
    }

    public static List<IEntity> selectClasses(Connection connection, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectClasses = "select top (?) classId, courseAcr, academicSemesterYear, academicSemesterType " +
                "from " +
                    "(select *, row_number() over (order by classId) as row " +
                    "from Class) " +
                "Class " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectClasses);
        psm.setInt(1, top);
        psm.setInt(2, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> classes = new LinkedList<>();
        while (resultSet.next()) {
            classes.add(instantiateClass(connection, resultSet));
        }
        if (forbidEmptyList && classes.isEmpty())
            throw new SelectException("Couldn't find any classes!");
        return classes;
    }

    public static List<IEntity> selectClassesOfCourse(Connection connection, String acr, int skip, int top) throws SQLException, CommandException {
        return selectClassesOfCourse(connection, acr, skip, top, true);
    }

    public static List<IEntity> selectClassesOfCourse(Connection connection, String acr, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectClass = "select top (?) classId, courseAcr, academicSemesterYear, academicSemesterType " +
                "from " +
                    "(select *, row_number() over (order by classId) as row " +
                    "from Class " +
                    "where courseAcr = ?) " +
                "Class " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectClass);
        psm.setInt(1, top);
        psm.setString(2, acr);
        psm.setInt(3, skip);

        ResultSet resultSet = psm.executeQuery();
        List<IEntity> classes = new LinkedList<>();
        while (resultSet.next()) {
            classes.add(instantiateClass(connection, resultSet));
        }
        if (forbidEmptyList && classes.isEmpty())
            throw new SelectException("No classes found! Are you sure the course specified has any classes associated with it?");
        return classes;
    }

    public static List<IEntity> selectClassesOfCourseOnSemester(Connection connection, String acr, String semYear, String semType, int skip, int top) throws SQLException, CommandException {
        return selectClassesOfCourseOnSemester(connection, acr, semYear, semType, skip, top, true);
    }

    public static List<IEntity> selectClassesOfCourseOnSemester(Connection connection, String acr, String semYear, String semType, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectClass = "select top (?) classId, courseAcr, academicSemesterYear, academicSemesterType " +
                "from " +
                    "(select *, row_number() over (order by classId) as row " +
                    "from Class " +
                    "where courseAcr = ? and academicSemesterYear = ? and academicSemesterType = ?) " +
                "Class " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectClass);
        psm.setInt(1, top);
        psm.setString(2, acr);
        psm.setString(3, semYear);
        psm.setString(4, semType);
        psm.setInt(5, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> classes = new LinkedList<>();
        while (resultSet.next()) {
            classes.add(instantiateClass(connection, resultSet));
        }
        if (forbidEmptyList && classes.isEmpty())
            throw new SelectException("No classes found! Are you sure the course specified has any classes associated with it?");
        return classes;
    }

    public static List<IEntity> selectClassesTaughtByTeacher(Connection connection, int teacherId, int skip, int top) throws SQLException, CommandException {
        return selectClassesTaughtByTeacher(connection, teacherId, skip, top, true);
    }

    public static List<IEntity> selectClassesTaughtByTeacher(Connection connection, int teacherId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectClass = "select top (?) classId, courseAcr, academicSemesterYear, academicSemesterType " +
                "from " +
                    "(select C.classId, C.courseAcr, C.academicSemesterType, C.academicSemesterYear, T.teacherId, row_number() over (order by C.classId) as row " +
                    "from Class as C  " +
                        "inner join Teaches as T " +
                        "on C.classId = T.classId and " +
                        "C.courseAcr = T.courseAcr and " +
                        "C.academicSemesterType = T.academicSemesterType and " +
                        "C.academicSemesterYear = T.academicSemesterYear " +
                    "where T.teacherId = ?) " +
                "Class " +
                "where row>(?)";

        PreparedStatement psm = connection.prepareStatement(selectClass);
        psm.setInt(1, top);
        psm.setInt(2, teacherId);
        psm.setInt(3, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> classes = new LinkedList<>();
        while (resultSet.next()) {
            classes.add(instantiateClass(connection, resultSet));
        }
        if (forbidEmptyList && classes.isEmpty())
            throw new SelectException("The specified teacher isn't lecturing any classes!");
        return classes;
    }

    public static List<IEntity> selectClassesAttendedByStudent(Connection connection, int studentId, int skip, int top) throws SQLException, CommandException {
        return selectClassesAttendedByStudent(connection, studentId, skip, top, true);
    }

    public static List<IEntity> selectClassesAttendedByStudent(Connection connection, int studentId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectClass = "select top (?) classId, courseAcr, academicSemesterYear, academicSemesterType " +
                "from " +
                    "(select *, row_number() over (order by classId) as row " +
                    "from Class " +
                    "where classId in ( " +
                        "select classId " +
                        "from Belongs " +
                        "where studentId = ?)) " +
                "Class " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectClass);
        psm.setInt(1, top);
        psm.setInt(2, studentId);
        psm.setInt(3, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> classes = new LinkedList<>();
        while (resultSet.next()) {
            classes.add(instantiateClass(connection, resultSet));
        }
        if (forbidEmptyList && classes.isEmpty())
            throw new SelectException("The specified student isn't registered any classes!");
        return classes;
    }

}
