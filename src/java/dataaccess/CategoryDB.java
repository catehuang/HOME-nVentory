package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

public class CategoryDB {
    public Category get(int category_id) {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Category category = em.find(Category.class, category_id);
            return category;
        } finally {
            em.close();
        }
    } 
    
    public List<Category> getAll() {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Category> categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return categories;
        } finally {
            em.close();
        }
    }
    
    public void insert(Category category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        if (category.getCategoryName() == null) 
        {
            throw new Exception("empty_category_name");
        }
        
        try 
        {
            CategoryDB categoryDB = new CategoryDB();
            List<Category> categories = categoryDB.getAll();
            categories.add(category);
            trans.begin();
            em.persist(category);
            trans.commit();
        } 
        catch (Exception ex) 
        {
            trans.rollback();
            throw new Exception(ex);
        } 
        finally 
        {
            em.close();
        }
    }

    public void update(Category category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
  
        try 
        {
            trans.begin();
            em.merge(category);
            trans.commit();
        } 
        catch (Exception ex) 
        {
            trans.rollback();
            throw new Exception(ex);
        } 
        finally 
        {
            em.close();
        }
    }
}
