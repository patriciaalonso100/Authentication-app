
/*package dtu.group08.app;

import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.data.repositories.DataSeeder;
import dtu.group08.data.repositories.RoleRepository;
import dtu.group08.data.repositories.UserRepository;
import dtu.group08.server.services.AuthenticationService;
import dtu.group08.server.services.WorkerManagementService;
import java.rmi.RemoteException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class WorkerManagementServiceTest {

    private static AuthenticationService authService;
    private static WorkerManagementService workerManagementService;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager context;
    private static UserRepository userRepo;
    private static RoleRepository roleRepo;

    @BeforeAll
    public static void setUp() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("on-memory");
            context = entityManagerFactory.createEntityManager();
            userRepo = new UserRepository(context);
            roleRepo = new RoleRepository(context);
            authService = new AuthenticationService(userRepo, roleRepo);
            workerManagementService = new WorkerManagementService(authService, userRepo);
            DataSeeder.SeedRoles(roleRepo);
            DataSeeder.SeedUsers(userRepo);
            
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        context.clear();
        entityManagerFactory.close();
        authService = null;
    }

    @Test
    public void testFailedAddWorker() {
        try {
            IActionResult<String> result = workerManagementService.deleteWorker("e1a0b900-3a84-491d-ad39-da4440878888", "");
            assertFalse(result.isSuccessfull());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    public void testSuccessfulLoginForAlice() {
        try {
            IActionResult<String> result = authService.login("alice@company.com", "testpassword");
            assertTrue(result.isSuccessfull());
            assertNotNull(result.getData());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }*/