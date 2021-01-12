package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() { }

    Connection connect = Util.dataBaseConnection();

    public void createUsersTable() {
        try {
            Statement statement = connect.createStatement();
            String s = "CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(128), lastName VARCHAR(128), age TINYINT)";
            statement.executeUpdate(s);
            System.out.println("Таблица успешно создана");
        } catch(SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connect.createStatement();
            String s = "DROP TABLE users";
            statement.execute(s);
        }catch (SQLException e){
            System.out.println("Ошибка при удалении таблицы");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate(String.format("INSERT INTO users (name, lastName, age) VALUES ('%s', '%s', %s)", name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании user");
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement ps = connect.prepareStatement("DELETE FROM users WHERE ID = ?");) {
            connect.setAutoCommit(false);
            ps.setLong(1, id);
            ps.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении user");
        } finally {
            connect.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connect.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM users");
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
            Statement statement = connect.createStatement();
            String s = "TRUNCATE TABLE users";
            statement.execute(s);
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы");
        }
    }
}
