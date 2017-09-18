package pt.isel.ls.academicActivities.database.data;

import pt.isel.ls.academicActivities.database.DbModel;
import pt.isel.ls.academicActivities.exceptions.CommandException;
import pt.isel.ls.academicActivities.exceptions.SelectException;
import pt.isel.ls.academicActivities.model.IEntity;
import pt.isel.ls.academicActivities.model.Programme;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DbProgramme extends DbModel {

    public static int insertProgramme(Connection connection, Programme programme) throws SQLException, CommandException {
        String insertProgramme = "insert into Programme values (?, ?, ?)";
        PreparedStatement psm = connection.prepareStatement(insertProgramme, Statement.RETURN_GENERATED_KEYS);
        psm.setString(1, programme.getProgrammeName());
        psm.setString(2, programme.getProgrammeAcr());
        psm.setInt(3, programme.getProgrammeSemesters());
        return psm.executeUpdate();
    }

    public static IEntity selectProgramme(Connection connection, String acr) throws SQLException, CommandException {
        String selectAProgramme = "select * from Programme where programmeAcr = ?";
        PreparedStatement psm = connection.prepareStatement(selectAProgramme);
        psm.setString(1, acr);
        ResultSet resultSet = psm.executeQuery();
        if (resultSet.next()) {
            return instantiateProgramme(connection, resultSet);
        } else
            throw new SelectException("Programme not found! You might be inputting bad parameters or the class might not exist in the database.");
    }

    public static List<IEntity> selectProgrammes(Connection connection, int skip, int top) throws SQLException, CommandException {
        return selectProgrammes(connection, skip, top, true);
    }

    public static List<IEntity> selectProgrammes(Connection connection, int skip, int top, boolean forbidEmptyList) throws SQLException, CommandException {
        String selectAllProgrammes = "select top (?) programmeName, programmeAcr, programmeSemesters " +
                "from " +
                "(select *, row_number() over (order by programmeAcr) as row from Programme ) " +
                "Programme " +
                "where row>(?) ";
        PreparedStatement psm = connection.prepareStatement(selectAllProgrammes);
        psm.setInt(1, top);
        psm.setInt(2, skip);
        ResultSet resultSet = psm.executeQuery();
        List<IEntity> programmes = new LinkedList<>();
        while (resultSet.next()) {
            programmes.add(instantiateProgramme(connection, resultSet));
        }
        if (forbidEmptyList && programmes.isEmpty())
            throw new SelectException("No programmes found!");
        return programmes;
    }
}
