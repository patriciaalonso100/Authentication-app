package dtu.group08.app;

import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.data.repositories.DataSeeder;
import dtu.group08.data.repositories.RoleRepository;
import dtu.group08.data.repositories.UserRepository;
import dtu.group08.server.services.AuthenticationService;

import java.rmi.RemoteException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class AuthenticationServiceTest {

    private static AuthenticationService authService;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager context;

    @BeforeAll
    public static void setUp() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("on-memory");
            context = entityManagerFactory.createEntityManager();
            authService = new AuthenticationService(context);
            DataSeeder.SeedRoles(context);
            DataSeeder.SeedUsers(context);
            
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
    public void testFailedLoginForUnknownUser() {
        try {
            IActionResult<String> result = authService.login("patri", "hola");
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
    }

    @Test
    public void testSuccessfulLoginForBob() {
        try {
            IActionResult<String> result = authService.login("alice@company.com", "testpassword");
            assertTrue(result.isSuccessfull());
            assertNotNull(result.getData());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    public void testSuccessfulLoginForCecilia() {
        try {
            IActionResult<String> result = authService.login("cecilia@company.com", "testpassword");
            assertTrue(result.isSuccessfull());
            assertNotNull(result.getData());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    public void testSuccessfulLoginForDavid() {
        try {
            IActionResult<String> result = authService.login("david@company.com", "testpassword");
            assertTrue(result.isSuccessfull());
            assertNotNull(result.getData());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    public void testSuccessfulLoginForErica() {
        try {
            IActionResult<String> result = authService.login("erica@company.com", "testpassword");
            assertTrue(result.isSuccessfull());
            assertNotNull(result.getData());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    public void testSuccessfulLoginForFred() {
        try {
            IActionResult<String> result = authService.login("fred@company.com", "testpassword");
            assertTrue(result.isSuccessfull());
            assertNotNull(result.getData());
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
    
    
}
