package dtu.group08.server.services;

import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.core.interfaces.IWorkerManagementService;
import dtu.group08.core.models.Unautorized;
import dtu.group08.data.models.User;
import dtu.group08.data.repositories.UserRepository;
import java.rmi.RemoteException;
import dtu.group08.core.interfaces.IAuthenticationService;
import dtu.group08.core.models.Ok;
import dtu.group08.core.models.ServerError;
import dtu.group08.data.interfaces.IAccessControlRepository;
import dtu.group08.data.repositories.AccessRepository_RBAC;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.EntityManager;


/**
 *
 * @author patri
 */
public class WorkerManagementService extends UnicastRemoteObject implements IWorkerManagementService{
    
    private final UserRepository userRepository;
    private final IAuthenticationService _authService;
    private final IAccessControlRepository accessControlRepository;    
    
    
    public WorkerManagementService(IAuthenticationService authService, UserRepository userRepository, IAccessControlRepository accessControlRepository) throws RemoteException {
        super();
        _authService = authService;
        this.userRepository = userRepository;
        this.accessControlRepository = accessControlRepository;
    }
    
    public WorkerManagementService (EntityManager entityManager) throws RemoteException{
        _authService = new AuthenticationService(entityManager);
        
        this.userRepository = new UserRepository(entityManager);  
        this.accessControlRepository = new AccessRepository_RBAC(entityManager);
        
    }
    
    private boolean isAuthorized(String token, String function) throws RemoteException {
        return _authService.isAuthorized(token, function).getData();
    }
    
    @Override
     public IActionResult<Void> deleteWorker (String username, String token) throws RemoteException {
         
         try {
            if (!isAuthorized(token, "deleteUser")){
                
                return new Unautorized();
            }
            userRepository.deleteUser(username);
            accessControlRepository.deleteUserPermissions(username);
            return new Ok(null);
            }
         catch (RemoteException e) {
            return new ServerError("Server error.");
            
         }
         
     }
     
     
     @Override
     public IActionResult<Void> addWorker (String name, String username, String password, String userRole, String token) throws RemoteException {
         
         try {
            if (!isAuthorized(token, "addUser")){
                log("unauthorized call for addWorker");
                return new Unautorized();
            }
            User user = new User(name, username, password);
            userRepository.addUser(user);
            accessControlRepository.setRole(username ,userRole);
            log("new user added with username "+username);
            return new Ok(null);
         }
         catch (RemoteException e) {
            return new ServerError("Server error.");
         }
     }

    @Override
    public IActionResult<List<String>> getUsers(String sessionToken) throws RemoteException {
        try{
            if(!isAuthorized(sessionToken, "getUsers"))
                return new Unautorized();
            return new Ok(userRepository.getAll());
        }
        catch(RemoteException e){
            return new ServerError("Operation failed");
        }
        catch(Exception e){
            return new ServerError("Operation failed");
        }
    }

    @Override
    public IActionResult<List<String>> getUserPermissions(String username, String sessionToken) throws RemoteException{
        try{
            if(!isAuthorized(sessionToken, "getUserPermissions"))
                return new Unautorized();
            return new Ok(accessControlRepository.getPermissions(username));
        }catch(RemoteException e){
            return new ServerError("Operation failed");
        }
        catch(Exception e){
            log("failed to get permissions for "+username);
            log(e.getMessage());
            return new ServerError("Operation failed");
        }
    }
    
    @Override
    public IActionResult<String> getUserRole(String username, String sessionToken) throws RemoteException {

        log("getting user role for user " + username);
        try{
            if(!isAuthorized(sessionToken, "getUserRole"))
                return new Unautorized();
            return new Ok(accessControlRepository.getRole(username));
        }catch(RemoteException e)
        {
            log("failed to get user role for " +username);
            log(e.getMessage());
            return new ServerError("");
        }
        catch(Exception e){
            log("failed to get user role for " +username);
            log(e.getMessage());
            return new ServerError("Operation failed");
        }
         
    }
    
    void log(String message){
        System.out.println(message);
    }

    @Override
    public IActionResult<String> setUserRole(String username, String role, String sessionToken) throws RemoteException {

        try{
            if(!isAuthorized(sessionToken, "setUserRole"))
                return new Unautorized();
            accessControlRepository.deleteUserPermissions(username.trim());
            accessControlRepository.setRole(username, role.trim());
            return new Ok("Role updated sucessfully!");
        }
        catch(RemoteException e){
            log("failed to get user role for " +username);
            log(e.getMessage());
            return new ServerError("");
        }
        catch(Exception e){
            log("Error changing the user role. "+ e.getMessage());
            return new ServerError("");
        }
    }

}
