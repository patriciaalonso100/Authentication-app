package dtu.group08.data;

import dtu.group08.core.tools.PasswordUtils;
import dtu.group08.data.models.User;
import dtu.group08.data.repositories.UserRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

public class UserRepositoryTests {

    private UserRepository userRepo;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager context;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("on-memory");
        context = entityManagerFactory.createEntityManager();
        userRepo = new UserRepository(context);
    }

    @AfterEach
    public void tearDown() {
        context.clear();
        entityManagerFactory.close();
    }

    @Test
    public void UserPasswordShouldBeHashed() {
        String password = "test_password";
        User user = new User("Test","test@dtu.dk", password);
        assertNotEquals(password, user.getPassword());
        assertEquals(PasswordUtils.hashPassword(password, user.getSalt()), user.getPassword());
    }
    
     @Test
     public void shouldAddUser()
     { 
         System.out.println("test addUser");
        String username = "test@dtu.dk";
        assertNull(userRepo.findUserByUsername(username));
        User user = new User("test", username, "test_password");
        userRepo.addUser(user);
        assertNotNull(userRepo.findUserByUsername(username));
     }
}
