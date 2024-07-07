package dtu.group08.data.repositories;

import dtu.group08.data.models.RbacRecord;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import dtu.group08.data.interfaces.IAccessControlRepository;


public class AccessRepository_RBAC implements IAccessControlRepository{

    private final EntityManager entityManager;
    private final RoleRepository roleRepo;
    
    public AccessRepository_RBAC(EntityManager entityManager){
        this.entityManager = entityManager;
        roleRepo = new RoleRepository(entityManager);
    }

    @Override
    public void addPermission(String userId, String permission) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void addPermissions(String userId, String[] permission) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void setRole(String userId, String role){
        this.deleteUserPermissions(userId);
        RbacRecord rbac = new RbacRecord(userId, role);
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(rbac);
        entityManager.getTransaction().commit();
    }
    
    @Override
    public String getRole(String userId) {
        String queryString = "SELECT r.roleName FROM RbacRecord r WHERE r.userId = :userId";
        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("userId", userId.toUpperCase());
        return query.getSingleResult();
    }

    @Override
    public List<String> getPermissions(String userId)
    {
        String role = getRole(userId);
        if(role == null)
            return new LinkedList<>();
        return roleRepo.getPermissionsFromRole(role);
    }

    @Override
    public Boolean hasPermission(String userId, String permission) {
        return getPermissions(userId).contains(permission);
    }

    @Override
    public void deletePermission(String userId, String permission) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUserPermissions(String userId) {
        entityManager.getTransaction().begin();

        // Retrieve the record
        String queryString = "SELECT r FROM RbacRecord r WHERE r.userId = :userId";
        TypedQuery<RbacRecord> query = entityManager.createQuery(queryString, RbacRecord.class);
        query.setParameter("userId", userId.toUpperCase());
        List<RbacRecord> records = query.getResultList();

        // Delete the records
        for (RbacRecord record : records) {
            entityManager.remove(record);
        }

        entityManager.getTransaction().commit();
    }
}
