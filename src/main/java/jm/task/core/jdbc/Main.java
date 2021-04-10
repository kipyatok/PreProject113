package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        //userService.dropUsersTable();
        userService.createUsersTable();

        userService.saveUser("Joy", "Key", (byte) 22);
        userService.saveUser("Steve", "Lanch", (byte) 55);
        userService.saveUser("Govn", "Pechal", (byte) 16);
        userService.saveUser("Rustam", "Vodka", (byte) 29);

        //userService.removeUserById(2);

        List<User> users = userService.getAllUsers();
        for(User u : users){
            System.out.println(u.toString());
        }

       userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
