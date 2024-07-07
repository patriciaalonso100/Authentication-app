
package dtu.group08.data.repositories;

import dtu.group08.data.interfaces.IAccessControlRepository;
import dtu.group08.data.models.AclRecord;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AccessRepository_ACL implements IAccessControlRepository{
    
    private final EntityManager entityManager;
        
    public AccessRepository_ACL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addPermission(String userId, String permission) {
        if(hasPermission(userId, permission))
            return;
        entityManager.getTransaction().begin();
        AclRecord acl = new AclRecord(userId, permission);
        entityManager.persist(acl);
        entityManager.getTransaction().commit();
    }
    

    @Override
    public void setRole(String userId, String role) { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public String getRole(String userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void addPermissions(String userId, String[] permissions){
        for(String permission : permissions)
            addPermission(userId, permission);
    }

    @Override
    public List<String> getPermissions(String userId) {
        String queryString = "SELECT r.permission FROM AclRecord r WHERE r.userId = :userId";
        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("userId", userId.toUpperCase());
        return query.getResultList();
    }

    @Override
    public Boolean hasPermission(String userId, String permission) {
        return getPermissions(userId).contains(permission);
    }

    @Override
    public void deletePermission(String userId, String permission) {
        entityManager.getTransaction().begin();

        // Retrieve the record
        String queryString = "SELECT r FROM AclRecord r WHERE r.userId = :userId AND r.permission = :permission";
        TypedQuery<AclRecord> query = entityManager.createQuery(queryString, AclRecord.class);
        query.setParameter("userId", userId.toUpperCase());
        query.setParameter("permission", permission);
        List<AclRecord> records = query.getResultList();

        // Delete the records
        for (AclRecord record : records) {
            entityManager.remove(record);
        }

        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteUserPermissions(String userId) {
        entityManager.getTransaction().begin();

        // Retrieve the record
        String queryString = "SELECT r FROM AclRecord r WHERE r.userId = :userId";
        TypedQuery<AclRecord> query = entityManager.createQuery(queryString, AclRecord.class);
        query.setParameter("userId", userId.toUpperCase());
        List<AclRecord> records = query.getResultList();

        // Delete the records
        for (AclRecord record : records) {
            entityManager.remove(record);
        }

        entityManager.getTransaction().commit();
    }
}
