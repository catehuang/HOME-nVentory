package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Items;
import models.Users;

public class ItemsDB 
{
    public Items get(int itemID) 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try 
        {
            Items item = em.find(Items.class, itemID);
            return item;
        } 
        finally 
        {
            em.close();
        }
    }
    
    public List<Items> getAll(String owner) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try 
        {
            Users users = em.find(Users.class, owner);
            return users.getItemsList();
        } 
        finally 
        {
            em.close();
        }
    }
    
    public void insert(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            Users user = item.getOwner();
            user.getItemsList().add(item);
            trans.begin();
            em.persist(item);
            em.merge(user);
            trans.commit();
        } 
        catch (Exception ex) 
        {
            trans.rollback();
        } 
        finally 
        {
            em.close();
        }
    }

    public void update(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            trans.begin();
            em.merge(item);
            trans.commit();
        } 
        catch (Exception ex) 
        {
            trans.rollback();
        } 
        finally 
        {
            em.close();
        }
    }

    public void delete(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            Users user = item.getOwner();
            user.getItemsList().remove(item);
            trans.begin();
            em.remove(em.merge(item));
            em.merge(user);
            trans.commit();
        } 
        catch (Exception ex) 
        {
            trans.rollback();
        } 
        finally 
        {
            em.close();
        }
    }
}
