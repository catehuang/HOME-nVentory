package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

public class RoleDB {
    public Role get(int role_id) {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role role = em.find(Role.class, role_id);
            return role;
        } finally {
            em.close();
        }
    } 
    
    public List<Role> getAll() {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Role> categories = em.createNamedQuery("Category.findAll", Role.class).getResultList();
            return categories;
        } finally {
            em.close();
        }
    }
}
