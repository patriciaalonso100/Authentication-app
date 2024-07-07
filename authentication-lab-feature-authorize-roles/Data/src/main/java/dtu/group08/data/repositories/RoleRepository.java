
package dtu.group08.data.repositories;

import dtu.group08.data.models.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author patri
 */
public class RoleRepository {
    
    private final EntityManager entityManager;
    
    public RoleRepository() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("group08");
        entityManager = entityManagerFactory.createEntityManager();
    }
        
    public RoleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public int addRole (Role role) {
        entityManager.getTransaction().begin();
        entityManager.persist(role);
        entityManager.getTransaction().commit();
        System.out.println("role added " + role.getName());
        return role.getId();
        
    }       
    
    public Role getRole (String rolename) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.nameNormalized = :rolename", Role.class);
        query.setParameter("rolename", rolename.toUpperCase());

        List<Role> roles = query.getResultList();
        if (roles.isEmpty()) {
            return null;
        } else if (roles.size() > 1) {
            throw new NonUniqueResultException();
        } else {
            return roles.get(0);
        }
        
    }
    
    public List<String> getPermissionsFromRole (String rolename) {
        
        Role role = getRole(rolename);
        
        if(role == null) {
            return null;
        }
        
        List<String> permissions = new ArrayList<>();
        
        permissions.addAll(role.getPermissions());
        
        if(role.getInheritRoles() == null) {
            return permissions;
        }
        
        for (String inheritRole: role.getInheritRoles()) {
            
            role = getRole(inheritRole);
            
            List<String> rolePermissions = role.getPermissions();
            
            if(rolePermissions == null){
                
                continue;
            }
            
            permissions.addAll(rolePermissions);
        }
        
        return permissions;
        
    }
        
    
}
