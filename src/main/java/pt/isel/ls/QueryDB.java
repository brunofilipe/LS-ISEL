package pt.isel.ls;

import java.sql.*;

public class QueryDB {

    public static String readStudent(Connection con, int studentID) throws SQLException {
        String std = null;
        String query = "select * from Students where studentID = ?";
        try {
            PreparedStatement psm = con.prepareStatement(query);
            psm.setInt(1, studentID);
            ResultSet res = psm.executeQuery();
            if ( res.next() )
                std = res.getString("studentName");
            con.commit();
        } catch ( SQLException e ) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
        return std;
    }

    public static String removeStudent(Connection con, int studentID) throws SQLException {
        String std = null;
        String getStudent = "select * from Students where studentID = ?";
        try {
            PreparedStatement statement = con.prepareStatement(getStudent);
            statement.setInt(1, studentID);
            ResultSet aResultSet = statement.executeQuery();

            if ( aResultSet.next() ) {
                std = aResultSet.getString("studentName");
                String query = "delete from Students where studentID = ?";
                PreparedStatement psm = con.prepareStatement(query);
                psm.setInt(1, studentID);
                psm.executeUpdate();
                con.commit();
            }
            else
                throw new SQLException("Student doesn't exists!");

        } catch ( SQLException e ) {
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
        return std;
    }

    public static boolean insertStudent(Connection con, int studentID, String name, int matriculations) throws SQLException {
        boolean res = true;
        String insertStudent = "insert into Students values ( ? , ? , ? )";
        try {
            PreparedStatement statement = con.prepareStatement(insertStudent);
            statement.setInt(1, studentID);
            statement.setString(2, name);
            statement.setInt(3, matriculations);
            statement.executeUpdate();
            con.commit();
        } catch ( SQLException e ) {
            res=false;
            con.rollback();
            e.printStackTrace();

        } finally {
            con.close();
        }
        return res;
    }

    public static boolean updateStudent (Connection con, int studentID, int matriculations) throws SQLException {
        boolean res = true;
        String insertStudent = "update Students set studentMatriculations = ? where studentID = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(insertStudent);
            statement.setInt(1, matriculations);
            statement.setInt(2, studentID);
            statement.executeUpdate();
            con.commit();
        } catch ( SQLException e ) {
            res=false;
            con.rollback();
            e.printStackTrace();
        } finally {
            con.close();
        }
        return res;
    }
}
