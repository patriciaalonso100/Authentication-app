
package dtu.group08.data;

import dtu.group08.data.repositories.AccessRepository_ACL;
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
public class AclRepositoryTest {
    
    private AccessRepository_ACL repo;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager context;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("on-memory");
        context = entityManagerFactory.createEntityManager();
        repo = new AccessRepository_ACL(context);
    }

    @AfterEach
    public void tearDown() {
        context.clear();
        entityManagerFactory.close();
    }
    
    @Test
    public void addRecordTest () {
        System.out.println("test adding ACL record");
        
        String username = "bob@dtu.dk";
        assertEquals(0, repo.getPermissions(username).size());
        repo.addPermissions(username, new String[]{"print", "showQueue"});
        assertEquals(2, repo.getPermissions(username).size());
    }
    
    @Test
    public void getPermissionsTest () {
        
        repo.addPermissions("bob@dtu.dk", new String[]{"print", "showQueue"});
        List<String> result = repo.getPermissions("bob@dtu.dk");
        assertNotNull(result);
        assertEquals("print", result.get(0));
        assertEquals("showQueue", result.get(1));
    }
    
    @Test
    public void deleteOnePermissionTest () {
        repo.addPermissions("bob@dtu.dk", new String[]{"print", "showQueue"});
        assertEquals(2,repo.getPermissions("bob@dtu.dk").size());
        repo.deletePermission("bob@dtu.dk", "showQueue");
        assertEquals(1,repo.getPermissions("bob@dtu.dk").size());
        assertFalse(repo.getPermissions("bob@dtu.dk").contains("showQueue"));
    }
    
    @Test
    public void deleteAclRecordTest () {
        String username = "user@dtu.dk";
        // add the user
        repo.addPermissions(username, new String[]{"test"});
        // see if the user exists
        assertEquals(1, repo.getPermissions(username).size());
        // delete user
        repo.deleteUserPermissions(username);
        // check if the user is deleted
        assertEquals(0, repo.getPermissions(username).size());
    }
    
}
