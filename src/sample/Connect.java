package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Connect {

    static Connection connection = null;

    //Generates Connection obj
    public static Connection connect(){
        final String URL = "jdbc:postgresql://localhost:5432/Arenda";
        final String username = "postgres";
        final String password = "12345";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Нет коннекта к базе: " + ex);
        }
        return connection;
    }

    //returns Connection obj
    public static Connection getConnection() {
        return connection;
    }
}
