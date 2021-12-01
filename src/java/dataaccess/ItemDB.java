package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

public class ItemDB 
{
    public Item get(int item_id) 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try 
        {
            Item item = em.find(Item.class, item_id);
            return item;
        } 
        finally 
        {
            em.close();
        }
    }
    
    public List<Item> getAll(String owner) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try 
        {
            User users = em.find(User.class, owner);
            return users.getItemList();
        } 
        finally 
        {
            em.close();
        }
    }
    
    public void insert(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            User user = item.getOwner();
            user.getItemList().add(item);
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

    public void update(Item item) throws Exception {
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

    public void delete(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try 
        {
            User user = item.getOwner();
            user.getItemList().remove(item);
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
