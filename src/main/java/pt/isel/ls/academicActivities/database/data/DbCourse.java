package pt.isel.ls.academicActivities.database.data;

import pt.isel.ls.academicActivities.database.DbModel;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.SelectException;
import pt.isel.ls.academicActivities.model.Course;
import pt.isel.ls.academicActivities.model.IEntity;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbCourse extends DbModel {

    public static int insertCourse(Connection connection, Course course) throws SQLException, CommandException {
        String insertCourse = "insert into Course values (?, ?, ?)";
        PreparedStatement psm = connection.prepareStatement(insertCourse, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, course.getCourseName());
        psm.setString(2, course.getCourseAcr());
        psm.setInt(3, course.getCoordinatorId());
        return psm.executeUpdate();
    }

    public static IEntity selectCourse(Connection connection, String acr) throws SQLException, CommandException {
        String selectCourse = "select * from Course where courseAcr = ?";
        PreparedStatement psm = connection.prepareStatement(selectCourse);
        psm.setString(1, acr);
        ResultSet resultSet = psm.executeQuery();
        if (resultSet.next())
            return instantiateCourse(connection, resultSet);
        else
            throw new SelectException("Course not found! You might be inputting bad parameters or the course might not exist in the database.");
    }

    public static List<IEntity> selectCourses(Connection connection, int skip, int top) throws SQLException, CommandException {
        return selectCourses(connection, skip, top, true);
    }

    public static List<IEntity> selectCourses(Connection connection, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAllCourses = "select top (?) courseAcr, courseName, coordinatorId " +
                "from " +
                "(select *, row_number() over (order by courseAcr) as row from Course) " +
                "Course " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectAllCourses);
        psm.setInt(1, top);
        psm.setInt(2, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> courses = new LinkedList<>();
        while (resultSet.next()) {
            courses.add(instantiateCourse(connection, resultSet));
        }
        if (forbidEmptyList && courses.isEmpty())
            throw new SelectException("Couldn't find any courses!");
        return courses;
    }

    public static List<IEntity> selectCoursesCoordinatedByTeacher(Connection connection, int teacherId, int skip, int top) throws SQLException, CommandException {
        return selectCoursesCoordinatedByTeacher(connection, teacherId, skip, top, true);
    }

    public static List<IEntity> selectCoursesCoordinatedByTeacher(Connection connection, int teacherId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectCoursesCoordinatedByTeacher = "select top (?) courseAcr, courseName, coordinatorId " +
                "from " +
                "(select *, row_number() over (order by courseAcr) as row " +
                "from Course " +
                "where coordinatorId = ?) " +
                "Course " +
                "where row>?";
        PreparedStatement psm = connection.prepareStatement(selectCoursesCoordinatedByTeacher);
        psm.setInt(1, top);
        psm.setInt(2, teacherId);
        psm.setInt(3, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> courses = new LinkedList<>();
        while (resultSet.next()) {
            courses.add(instantiateCourse(connection, resultSet));
        }
        if (forbidEmptyList && courses.isEmpty())
            throw new SelectException("Couldn't find any courses!");
        return courses;
    }

    public static List<String> selectCourseAcrsCoordinatedByTeacher(Connection connection, int teacherId, int skip, int top) throws SQLException, CommandException {
        return selectCourseAcrsCoordinatedByTeacher(connection, teacherId, skip, top, true);
    }

    public static List<String> selectCourseAcrsCoordinatedByTeacher(Connection connection, int teacherId, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectCoursesCoordinatedByTeacher = "select top (?) courseAcr, courseName, coordinatorId " +
                "from " +
                "(select *, row_number() over (order by courseAcr) as row " +
                "from Course " +
                "where coordinatorId = ?) " +
                "Course " +
                "where row>?";
        PreparedStatement psm = connection.prepareStatement(selectCoursesCoordinatedByTeacher);
        psm.setInt(1, top);
        psm.setInt(2, teacherId);
        psm.setInt(3, skip);
        ResultSet resultSet = psm.executeQuery();
        List<String> courses = new LinkedList<>();
        while (resultSet.next()) {
            courses.add(resultSet.getString("courseAcr"));
        }
        if (forbidEmptyList && courses.isEmpty())
            throw new SelectException("Couldn't find any courses!");
        return courses;
    }
}
