package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
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
}
