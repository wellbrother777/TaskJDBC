package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/MyDataBase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "21083";

    public static Connection dataBaseConnection() throws SQLException {
        Driver driver = new FabricMySQLDriver();
        DriverManager.registerDriver(driver);
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
