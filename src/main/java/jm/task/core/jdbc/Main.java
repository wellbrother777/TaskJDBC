package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        User user1 = new User("Li", "Wong", (byte)18);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        User user2 = new User("Li2", "Wong2", (byte)19);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        User user3 = new User("Li3", "Wong3", (byte)20);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        User user4 = new User("Li4", "Wong4", (byte)21);
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> users = new ArrayList<>();
        users = userService.getAllUsers();
        for(User user: users){
            System.out.println(user.toString());
        }

        userService.removeUserById(2);

        List<User> users2 = new ArrayList<>();
        users2 = userService.getAllUsers();
        for(User user: users2){
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
