package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection sqlConnection = Util.getSQLConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE users(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age SMALLINT NOT NULL, PRIMARY KEY(id))";
        try (PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlCommand)){
            preparedStatement.executeUpdate(sqlCommand);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE users";
        try(Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate(sqlCommand);
            System.out.println("Удаление таблицы успешно");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Удалить таблицу не удалось");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlCommand)){
            sqlConnection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            sqlConnection.commit();
            sqlConnection.setAutoCommit(true);
            System.out.println("Пользователь добавлен " + name + " " + lastName);
        } catch (SQLException e){
            e.printStackTrace();
            try {
                sqlConnection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("Не удалось добавить пользователя");
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM users WHERE id =";
        try(Statement statement = sqlConnection.createStatement()){
            sqlConnection.setAutoCommit(false);
            statement.execute(sqlCommand + id);
            sqlConnection.commit();
            sqlConnection.setAutoCommit(true);
            System.out.println("Пользователь удален");
        } catch (SQLException e){
            e.printStackTrace();
            try {
                sqlConnection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("Пользователь не найден");
        }
    }

    public List<User> getAllUsers() {
        List<User> lUser = new ArrayList<>();
        String sqlCommand = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlCommand)){
            ResultSet resultSet = preparedStatement.executeQuery(sqlCommand);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                lUser.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return lUser;
    }

    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM users";
        try (Statement statement = sqlConnection.createStatement()){
            statement.execute(sqlCommand);
            System.out.println("Таблица очищена");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Таблица не очистилась");
        }
    }
}
