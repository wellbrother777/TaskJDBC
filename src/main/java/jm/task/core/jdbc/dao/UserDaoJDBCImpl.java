package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public static Connection connection;

    static {
        try {
            connection = Util.dataBaseConnection();
            System.out.println("Соединение с БД установлено");
        } catch (SQLException e) {
            System.out.println("Ошибка при соединении с БД");
        }
    }

    public UserDaoJDBCImpl() { }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String s = "CREATE TABLE IF NOT EXISTS user1(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(128), lastName VARCHAR(128), age TINYINT)";
            statement.executeUpdate(s);
            System.out.println("Таблица успешно создана");
        } catch(SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String s = "DROP TABLE user1";
            statement.execute(s);
        }catch (SQLException e){
            System.out.println("Ошибка при удалении таблицы");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO user1 (name, lastName, age) VALUES ('%s', '%s', %s)", name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании user");
        }
    }

    public void removeUserById(long id) {
        try {
            String s = "DELETE FROM user1 WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setLong(1, id);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении user");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM user1");
            while (res.next()) {
                Long id = res.getLong("id");
                String name = res.getString("name");
                String lastName = res.getString("lastName");
                Byte age = res.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении всех user");
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String s = "TRUNCATE TABLE user1";
            statement.execute(s);
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы");
        }
    }
}
