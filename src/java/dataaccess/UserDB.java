package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Users;

public class UserDB {

    public Users get(String username) {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Users user = em.find(Users.class, username);
            return user;
        } finally {
            em.close();
        }
    }

    public List<Users> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public void insert(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        boolean status = true;
        
        try {
            UserDB userDB = new UserDB();
            List<Users> users = userDB.getAll();
            users.add(user);
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void update(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            UserDB userDB = new UserDB();
            List<Users> users = userDB.getAll();
            users.remove(user);
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
