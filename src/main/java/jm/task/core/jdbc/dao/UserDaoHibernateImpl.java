package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getFactorySession();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE users(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age SMALLINT NOT NULL, PRIMARY KEY(id))";
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sqlCommand);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e){
            e.printStackTrace();
            System.out.println("Таблица не создана");
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE IF EXISTS users";
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sqlCommand);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e){
            e.printStackTrace();
            System.out.println("Таблица не удалена или отсуствует");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("Пользователь добавлен");
        } catch (HibernateException e){
            e.printStackTrace();
            System.out.println("Пользователь не добавлен");
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete((User) session.load(User.class, id));
            transaction.commit();
            System.out.println("Пользователь удален");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Пользователь не удален");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> lUser = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            lUser = (List<User>) session.createQuery("FROM " + User.class.getSimpleName()).list();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return lUser;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException e){
            e.printStackTrace();
            System.out.println("Таблица не очистилась");
        }
    }
}
