
package dtu.group08.data;

import dtu.group08.data.models.Role;
import dtu.group08.data.repositories.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author patri
 */
public class RoleRepositoryTest {
    
    private RoleRepository roleRepo;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager context;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("on-memory");
        context = entityManagerFactory.createEntityManager();
        roleRepo = new RoleRepository(context);
    }

    @AfterEach
    public void tearDown() {
        context.clear();
        entityManagerFactory.close();
    }
    
    @Test
    public void addRoleTest () {
        List<String> roles = new ArrayList<String>();
        roles.add(""); //we don't add anything here because the roles list is for INHERITED roles and the regular user doesn't inherit any
        List<String> permissions = new ArrayList<String>();
        permissions.add("print");
        permissions.add("show queue");

        Role role = new Role("regular",roles, permissions);
        
        int result = roleRepo.addRole(role);
        assertTrue(result >= 0);
    }
    
    @Test
    public void getRoleTest () {
        addRoleTest();
        Role result = roleRepo.getRole("regular");
        assertNotNull(result);
        
    }
    
    
}
