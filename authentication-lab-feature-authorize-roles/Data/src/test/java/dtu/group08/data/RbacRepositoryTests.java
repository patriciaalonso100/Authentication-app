package dtu.group08.data;

import dtu.group08.data.repositories.AccessRepository_RBAC;
import dtu.group08.data.repositories.DataSeeder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RbacRepositoryTests {
    private AccessRepository_RBAC repo;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager context;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("on-memory");
        context = entityManagerFactory.createEntityManager();
        DataSeeder.SeedContext(context);
        repo = new AccessRepository_RBAC(context);
    }

    @AfterEach
    public void tearDown() {
        context.clear();
        entityManagerFactory.close();
    }
    
    @Test
    public void addGetPermissionsTest () {
        System.out.println("test getting RBAC permissions for user");
        List<String> result = repo.getPermissions("alice@company.com");
        assertNotNull(result);
        assertEquals(true, result.size() > 10);
    }
    
}
