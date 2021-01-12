package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() { }

    SessionFactory sFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        Session session = sFactory.openSession();
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
        Session session = sFactory.openSession();
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
            Session session = sFactory.openSession();
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
        Session session = sFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            Query query = session.createSQLQuery("DELETE FROM users WHERE ID=?;").addEntity(User.class);
            query.setParameter(0, id);
            query.executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public List<User> getAllUsers() {
        Session session = sFactory.openSession();
        List<User> users = session.createQuery("FROM User").list();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sFactory.openSession();
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

