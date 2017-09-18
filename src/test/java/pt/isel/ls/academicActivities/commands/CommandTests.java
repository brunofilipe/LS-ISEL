package pt.isel.ls.academicActivities.commands;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandTests {

    public static void clearDatabase(SQLServerDataSource db) throws SQLException {
        Connection con = db.getConnection();
        String clearTableQueries =
                " delete Has\n" +
                " delete Teaches\n" +
                " delete Belongs\n"+
                " delete Class\n" +
                " delete Course\n" +
                " delete Student\n" +
                " delete Teacher\n" +
                " delete CourseAvailability\n" +
                " delete Programme";

        PreparedStatement psm = con.prepareStatement(clearTableQueries);
        psm.executeUpdate();
        con.commit();
        con.close();
    }

    public static void rebootDatabase(SQLServerDataSource db) throws SQLException {
        Connection con = db.getConnection();
        String inserts =
                "insert into Teacher values ('Jose Manuel', 'zemanuel@cc.isel.pt', 1);\n" +
                "insert into Teacher values ('Carlos Fonseca', 'carlosfonseca@cc.isel.pt', 2);\n" +
                "insert into Teacher values ('Vitor Oliveira', 'vitoroliveira@cc.isel.pt', 3);\n" +
                "insert into Teacher values ('Andre Sousa', 'andresousa@cc.isel.pt', 4);\n" +
                "insert into Programme values('Licenciatura Engenharia Informatica e Computadores', 'LEIC', 6);\n" +
                "insert into Programme values('Licenciatura Engenharia Informatica e Multimedia', 'LEIM', 6);\n" +
                "insert into Programme values('Licenciatura Engenharia Quimica', 'LEQ', 6);\n" +
                "insert into Student values('Joao Gameiro','joaogameiro@alunos.isel.pt', 41893, 'LEIC');\n" +
                "insert into Student values('Bruno Filipe','brunofilipe@alunos.isel.pt', 41484, 'LEIC');\n" +
                "insert into Student values('Nuno Pinto','nunopinto@alunos.isel.pt', 41529, 'LEIC');\n" +
                "insert into Student values('Ana Teixeira','anateixeira@alunos.isel.pt', 41480, 'LEQ');\n" +
                "insert into Course values('Laboratorio de Software', 'LS', 1);\n" +
                "insert into Course values('Ambientes Virtuais de Execução', 'AVE', 2);\n" +
                "insert into Course values('Programacao Internet', 'PI', 2);\n" +
                "insert into Course values('Matematica 1', 'M1', 3);\n" +
                "insert into Course values('Modelação Padrões Desenho', 'MPD', 4)\n" +
                "insert into Class values('41D', 'LS', '1617', 'summer');\n" +
                "insert into Class values('42D', 'AVE', '1617', 'summer');\n" +
                "insert into Class values('41N', 'LS', '1617', 'summer');\n" +
                "insert into Class values('42N', 'PI', '1516', 'winter');\n" +
                "insert into CourseAvailability values(1);\n" +
                "insert into CourseAvailability values(4);\n" +
                "insert into CourseAvailability values(5);\n" +
                "insert into CourseAvailability values(3)\n;" +
                "insert into Teaches values (1,'41D','LS','1617','summer');\n" +
                "insert into Teaches values(2,'42D','AVE','1617','summer');\n" +
                "insert into Teaches values(3,'41N','LS','1617','summer');\n" +
                "insert into Teaches values(1,'42N','PI','1516','winter')\n;" +
                "insert into Has values('LS', 'LEIC', 4, 1);\n" +
                "insert into Has values('AVE', 'LEIC', 3, 1);\n" +
                "insert into Has values('PI', 'LEIC', 5, 1);\n"+
                "insert into Belongs values ('41D', 'LS', '1617', 'summer', 41893);\n" +
                "insert into Belongs values ('41D', 'LS', '1617', 'summer', 41484);\n" +
                "insert into Belongs values ('41N', 'LS', '1617', 'summer', 41529);";
        PreparedStatement psm = con.prepareStatement(inserts);
        psm.executeUpdate();
        con.commit();
        con.close();
    }
}
