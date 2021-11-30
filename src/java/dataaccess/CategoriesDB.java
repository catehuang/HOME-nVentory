package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Categories;

public class CategoriesDB {
    public Categories get(int categoryID) {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Categories category = em.find(Categories.class, categoryID);
            return category;
        } finally {
            em.close();
        }
    } 
    
    public List<Categories> getAll() {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Categories> categories = em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
            return categories;
        } finally {
            em.close();
        }
    }
}
