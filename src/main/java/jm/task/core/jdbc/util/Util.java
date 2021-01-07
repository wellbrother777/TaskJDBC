package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/MyDataBase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "21083";

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
            try {
                Configuration configuration = new Configuration().setProperty("connection.driver_class","com.mysql.jdbc.Driver")
                        .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/MyDataBase?serverTimezone=PST&allowPublicKeyRetrieval=true&useSSL=false")
                        .setProperty("hibernate.connection.username", "root")
                        .setProperty("hibernate.connection.password", "21083")
                        .addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        return sessionFactory;
    }

    public static Connection dataBaseConnection() throws SQLException {
        Connection connection = null;
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение с БД установлено");
        } catch (SQLException e) {
            System.out.println("Ошибка при соединении с БД");
        }
        return connection;
    }





}
