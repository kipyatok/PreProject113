package jm.task.core.jdbc.util;

//import com.mysql.cj.xdevapi.SessionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class Util {
    //private static final String URL = "jdbc:mysql://localhost:3306/pr-113";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASS = "rootroot";
    private static final String URL = "jdbc:mysql://localhost:3306/pr-113?serverTimezone=UTC";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    public static SessionFactory getFactorySession(){

        SessionFactory factory = null;
        //Session session = null;
        try{
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.setProperty(Environment.DRIVER, DRIVER);
            properties.setProperty(Environment.URL, URL);
            properties.setProperty(Environment.USER, USERNAME);
            properties.setProperty(Environment.PASS, PASS);
            properties.setProperty(Environment.DIALECT, DIALECT);
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            factory = configuration.buildSessionFactory();
            //session = factory.openSession();
            System.out.println("Подключение создано");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Подключение не создано!");
        }
        return factory;
    }

    public static Connection getSQLConnection() {
        Connection connection = null;
        try {
            //Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Подключение успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Подключение не выполнено!");
        }
        return connection;
    }
}
