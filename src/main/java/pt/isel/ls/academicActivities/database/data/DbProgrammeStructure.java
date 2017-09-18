package pt.isel.ls.academicActivities.database.data;

import pt.isel.ls.academicActivities.database.DbModel;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.InsertException;
import pt.isel.ls.academicActivities.exceptions.SelectException;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.ProgrammeStructure;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbProgrammeStructure extends DbModel {

    public static int insertProgrammeStructure(Connection connection, ProgrammeStructure programmeStructure) throws SQLException, CommandException {
        String insertProgrammeStructure = "insert into Has values (?, ?, ?, ?)";
        PreparedStatement psm = connection.prepareStatement(insertProgrammeStructure, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, programmeStructure.getCourseAcr());
        psm.setString(2, programmeStructure.getProgrammeAcr());
        psm.setInt(3, programmeStructure.getCourseAvailabilitySemester());
        psm.setBoolean(4, programmeStructure.getHasMandatory());
        return psm.executeUpdate();
    }

    public static int insertCourseIntoProgrammeStructure(Connection connection, ProgrammeStructure programmeStructure) throws SQLException, CommandException {
        addSemesterToDatabaseIfDoesntExist(connection, programmeStructure.getCourseAvailabilitySemester());
        if (!programmeStructure.getHasMandatory() && !existsMandatoryCourseInProgramme(connection, programmeStructure.getCourseAcr(), programmeStructure.getProgrammeAcr())) {
            return insertProgrammeStructure(connection, programmeStructure);
        } else if (!existsCourseInProgramme(connection, programmeStructure.getCourseAcr(), programmeStructure.getProgrammeAcr())) {
                return insertProgrammeStructure(connection, programmeStructure);
        }
        throw new InsertException("Couldn't add the course to the specified programme! Make sure it doesn't exist one in the databse already.");
    }

    public static IEntity selectProgrammeStructure(Connection connection, String courseAcr, String programmeAcr, int courseAvailability, boolean mandatory) throws SQLException, CommandException {
        String selectHas = "select * from Has where (courseAcr = ? and programmeAcr = ? and courseAvailabilitySemester = ? and hasMandatory = ?)";
        PreparedStatement psm = connection.prepareStatement(selectHas);
        psm.setString(1, courseAcr);
        psm.setString(2, programmeAcr);
        psm.setInt(3, courseAvailability);
        psm.setBoolean(4, mandatory);
        ResultSet resultSet = psm.executeQuery();
        if (resultSet.next()) {
            return instantiateProgrammeStructure(resultSet);
        } else
            throw new SelectException("There isn't any association between the course and programme specified!");
    }

    public static List<IEntity> selectProgrammeStructures(Connection connection, int skip, int top) throws SQLException, CommandException {
        return selectProgrammeStructures(connection, skip, top, true);
    }

    public static List<IEntity> selectProgrammeStructures(Connection connection, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectHas = "select top (?) courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory " +
                "from " +
                    "(select *, row_number() over (order by courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory) as row from Has) " +
                "Has " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectHas);
        psm.setInt(1, top);
        psm.setInt(2, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> programmeStructures = new LinkedList<>();
        while (resultSet.next()) {
            programmeStructures.add(instantiateProgrammeStructure(resultSet));
        }
        if (forbidEmptyList && programmeStructures.isEmpty())
            throw new SelectException("There are no courses linked with any programme!");
        return programmeStructures;
    }

    public static List<IEntity> selectProgrammeStructuresOfSpecificProgramme(Connection connection, String programmeAcr, int skip, int top) throws SQLException, CommandException {
        return selectProgrammeStructuresOfSpecificProgramme(connection, programmeAcr, skip, top, true);
    }

    public static List<IEntity> selectProgrammeStructuresOfSpecificProgramme(Connection connection, String programmeAcr, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAProgramme = "select top (?) courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory " +
                "from " +
                    "(select *, row_number() over (order by courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory) as row " +
                    "from Has " +
                    "where programmeAcr = ?) " +
                "Has " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectAProgramme);
        psm.setInt(1, top);
        psm.setString(2, programmeAcr);
        psm.setInt(3, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> programmeStructures = new LinkedList<>();
        while (resultSet.next()) {
            programmeStructures.add(instantiateProgrammeStructure(resultSet));
        }
        if (forbidEmptyList && programmeStructures.isEmpty())
            throw new SelectException("There are no courses associated with the specified programme!");
        return programmeStructures;
    }

    public static List<IEntity> selectProgrammeStructuresOfSpecificCourse(Connection connection, String courseAcr, int skip, int top) throws SQLException, CommandException {
        return selectProgrammeStructuresOfSpecificCourse(connection, courseAcr, skip, top, true);
    }

    public static List<IEntity> selectProgrammeStructuresOfSpecificCourse(Connection connection, String courseAcr, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAProgramme = "select top (?) courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory " +
                "from " +
                    "(select *, row_number() over (order by courseAcr, programmeAcr, courseAvailabilitySemester, hasMandatory) as row " +
                    "from Has " +
                    "where courseAcr = ?) " +
                "Has " +
                "where row>(?)";
        PreparedStatement psm = connection.prepareStatement(selectAProgramme);
        psm.setInt(1, top);
        psm.setString(2, courseAcr);
        psm.setInt(3, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> programmeStructures = new LinkedList<>();
        while (resultSet.next()) {
            programmeStructures.add(instantiateProgrammeStructure(resultSet));
        }
        if (forbidEmptyList && programmeStructures.isEmpty())
            throw new SelectException("There are no programmes associated with the specified course!");
        return programmeStructures;
    }

    public static List<String> selectProgrammeAcronymsOfSpecificCourse(Connection connection, String courseAcr) throws SQLException, CommandException {
        return selectProgrammeAcronymsOfSpecificCourse(connection, courseAcr, true);
    }

    public static List<String> selectProgrammeAcronymsOfSpecificCourse(Connection connection, String courseAcr, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAProgramme = "select distinct ProgrammeAcr from Has where courseAcr = ? ";
        PreparedStatement psm = connection.prepareStatement(selectAProgramme);
        psm.setString(1, courseAcr);
        ResultSet resultSet = psm.executeQuery();
        List<String> acronyms = new LinkedList<>();
        while (resultSet.next()) {
            acronyms.add(resultSet.getString("programmeAcr"));
        }
        if (forbidEmptyList && acronyms.isEmpty())
            throw new SelectException("This course isn't registered in any programme!");
        return acronyms;
    }

    public static List<String> selectCourseAcronymOfSpecificProgramme(Connection connection, String programmeAcr) throws SQLException, CommandException {
        return selectCourseAcronymOfSpecificProgramme(connection, programmeAcr, true);
    }

    public static List<String> selectCourseAcronymOfSpecificProgramme(Connection connection, String programmeAcr, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAProgramme = "select distinct courseAcr from Has where programmeAcr = ? ";
        PreparedStatement psm = connection.prepareStatement(selectAProgramme);
        psm.setString(1, programmeAcr);
        ResultSet resultSet = psm.executeQuery();
        List<String> acronyms = new LinkedList<>();
        while (resultSet.next()) {
            acronyms.add(resultSet.getString("courseAcr"));
        }
        if (forbidEmptyList && acronyms.isEmpty())
            throw new SelectException("This programme doesn't have any courses registered to it!");
        return acronyms;
    }

    private static boolean existsCourseInProgramme(Connection connection, String courseAcr, String programmeAcr) throws SQLException {
        String selectHas = "select * from Has where (courseAcr = ? and programmeAcr = ?)";
        PreparedStatement psmSelectHas = connection.prepareStatement(selectHas);
        psmSelectHas.setString(1, courseAcr);
        psmSelectHas.setString(2, programmeAcr);
        ResultSet resultSet = psmSelectHas.executeQuery();
        return resultSet.next();
    }

    private static void addSemesterToDatabaseIfDoesntExist(Connection connection, int sem) throws SQLException {
        String selectCourseAvailabilitySemester = "select * from CourseAvailability where (courseAvailabilitySemester = ?) ";
        String insertCourseAvailabilitySemester = "insert into CourseAvailability values (?)";
        PreparedStatement psmCourseAvailability = connection.prepareStatement(selectCourseAvailabilitySemester);
        psmCourseAvailability.setInt(1, sem);
        ResultSet resultSet = psmCourseAvailability.executeQuery();
        if (!resultSet.next()) {
            psmCourseAvailability = connection.prepareStatement(insertCourseAvailabilitySemester);
            psmCourseAvailability.setInt(1, sem);
            psmCourseAvailability.executeUpdate();
        }
    }

    private static boolean existsMandatoryCourseInProgramme(Connection connection, String courseAcr, String programmeAcr) throws SQLException {
        String selectHas = "select * from Has where (courseAcr = ? and programmeAcr = ? and hasMandatory = ?)";
        PreparedStatement psmSelectHas = connection.prepareStatement(selectHas);
        psmSelectHas.setString(1, courseAcr);
        psmSelectHas.setString(2, programmeAcr);
        psmSelectHas.setBoolean(3, true);
        ResultSet resultSet = psmSelectHas.executeQuery();
        return resultSet.next();
    }
}
