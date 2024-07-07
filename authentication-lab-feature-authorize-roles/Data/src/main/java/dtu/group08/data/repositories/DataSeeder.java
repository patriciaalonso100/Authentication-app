package dtu.group08.data.repositories;

import dtu.group08.data.models.Role;
import dtu.group08.data.models.User;
import javax.persistence.EntityManager;

public class DataSeeder {
    
    public static void SeedContext(EntityManager context){
        SeedUsers(context);
        SeedRoles(context);
        SeedAclTable(context);
        SeedRbacTable(context);
    }
    
    public static void SeedUsers(EntityManager context){
        UserRepository userRepo = new UserRepository(context);
        userRepo.addUser(new User("Alice", "alice@company.com", "testpassword"));
        userRepo.addUser(new User("Bob", "bob@company.com", "testpassword"));
        userRepo.addUser(new User("Cecilia", "cecilia@company.com", "testpassword"));
        userRepo.addUser(new User("David", "david@company.com", "testpassword"));
        userRepo.addUser(new User("Erica", "erica@company.com", "testpassword"));
        userRepo.addUser(new User("Fred", "fred@company.com", "testpassword"));
        userRepo.addUser(new User("George", "george@company.com", "testpassword"));

    }
    
    public static void SeedRoles(EntityManager context)
    {
        RoleRepository roleRepo = new RoleRepository(context);
        roleRepo.addRole(new Role("ordinary", new String[]{}, new String[]{"print", "showQueue"}));
        roleRepo.addRole(new Role("power_user", new String[]{"ordinary"}, new String[]{"topQueue", "restart"}));
        roleRepo.addRole(new Role("service_technician", new String[]{}, new String[]{"start", "stop", "restart", "status", "read_config", "set_config"}));
        roleRepo.addRole(new Role("userAdmin", new String[]{}, new String[]{"deleteUser", "addUser", "getUsers", "getUserPermissions", "getUserRole", "setUserRole"}));
        roleRepo.addRole(new Role("manager", new String[]{"ordinary", "power_user", "service_technician", "userAdmin"}, new String[]{}));
    }
    
    public static void SeedAclTable(EntityManager context)
    {
        AccessRepository_ACL repo = new  AccessRepository_ACL(context);
        repo.addPermissions("alice@company.com", new String[]{"print", "showQueue", "topQueue", "start", "stop", "restart", "status", "read_config", "set_config", "addUser", "deleteUser", "deleteUser", "addUser", "showUsers", "showUserPermissions"});
        repo.addPermissions("bob@company.com", new String[]{"start", "stop", "restart", "status", "read_config", "set_config"});
        repo.addPermissions("cecilia@company.com", new String[]{"topQueue", "restart"});
        repo.addPermissions("david@company.com", new String[]{"print", "showQueue"});
        repo.addPermissions("erica@company.com", new String[]{"print", "showQueue"});
        repo.addPermissions("fred@company.com", new String[]{"print", "showQueue"});
        repo.addPermissions("george@company.com", new String[]{"print", "showQueue"});
    }
    
    public static void SeedRbacTable(EntityManager context)
    {
        AccessRepository_RBAC repo = new AccessRepository_RBAC(context);
        repo.setRole("alice@company.com", "manager");
        repo.setRole("bob@company.com", "service_technician");
        repo.setRole("cecilia@company.com", "power_user");
        repo.setRole("david@company.com", "ordinary");
        repo.setRole("erica@company.com","ordinary");
        repo.setRole("fred@company.com", "ordinary");
        repo.setRole("george@company.com", "ordinary");
    }
    
}
