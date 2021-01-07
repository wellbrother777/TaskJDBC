package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(128), lastName VARCHAR(128), age TINYINT);").executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            Session session = Util.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try {
                session.save(user);
                t.commit();
            } catch (HibernateException e) {
                t.rollback();
            } finally {
                session.close();
            }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        User user = (User) session.get(User.class, id);
        Transaction t = session.beginTransaction();
        try {
            session.delete(user);
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> users = session.createQuery("FROM User").list();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.createSQLQuery("TRUNCATE TABLE users;").executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
        } finally {
            session.close();
        }
    }
}

