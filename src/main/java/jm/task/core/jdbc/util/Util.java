package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    //private static final String URL = "jdbc:mysql://localhost:3306/pr-113";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASS = "rootroot";
    private static final String URL = "jdbc:mysql://localhost:3306/pr-113?serverTimezone=UTC";

    public static Connection getSQLConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Подключение успешно!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Подключение не выполнено!");
        }
        return connection;
    }
}
