package dtu.group08.server.services;

import dtu.group08.core.models.BadRequest;
import dtu.group08.core.models.Ok;
import dtu.group08.core.models.ServerError;
import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.core.tools.PasswordUtils;
import dtu.group08.data.models.*;
import dtu.group08.data.repositories.UserRepository;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import dtu.group08.core.interfaces.IAuthenticationService;
import dtu.group08.data.interfaces.IAccessControlRepository;
import dtu.group08.data.repositories.AccessRepository_RBAC;
import javax.persistence.EntityManager;

public class AuthenticationService extends UnicastRemoteObject implements IAuthenticationService {

    private final UserRepository userRepository;
    private final IAccessControlRepository accessControlRepository;
    
    public AuthenticationService(EntityManager entityManager) throws RemoteException {
        super();
        this.userRepository = new UserRepository(entityManager);
        this.accessControlRepository = new AccessRepository_RBAC(entityManager);
    }
    
    public AuthenticationService(UserRepository userRepository, IAccessControlRepository accessControlRepository) throws RemoteException {
        super();
        this.userRepository = userRepository;
        this.accessControlRepository = accessControlRepository;
    }

    @Override
    public IActionResult<String> login(String username, String password) throws RemoteException {
        try {
            User user = userRepository.findUserByUsername(username);
            if (user != null) {
                String hashedPassword = PasswordUtils.hashPassword(password, user.getSalt());
                if (hashedPassword.equals(user.getPassword())) {
                    SessionToken token = new SessionToken(user.getId());
                    userRepository.addToken(token);
                    return new Ok(token.getToken());
                }
            } else {
                return new BadRequest("Login failed!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ServerError("Server error");
    }

    @Override
    public IActionResult<Boolean> logout(String token) throws RemoteException {
        System.out.println("Deleting user token");
        userRepository.deleteToken(token);
        return new Ok(true);
    }

    @Override
    public IActionResult<Boolean> changePassword(String username, String oldPassword, String newPassword) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IActionResult<Boolean> isAuthenticated(String token) throws RemoteException {
        try {
            SessionToken tokenFromDB = userRepository.findToken(token);

            if (tokenFromDB == null) {
                return new Ok(false);
            } else {
                LocalDateTime now = LocalDateTime.now();
                boolean isValid = tokenFromDB.getValidTo().isAfter(now);
                return new Ok(isValid);
            }
        } catch (Exception e) {
            return new Ok(false);
        }
    }

    @Override
    public IActionResult<Boolean> isAuthorized(String token, String service) throws RemoteException {
        
        if(isAuthenticated(token).getData() == false) {
            return new Ok(false);
        }
        
        String username = userRepository.findUserNameFromToken(token);
        log(username+" requested service "+service);
        return new Ok(accessControlRepository.hasPermission(username, service));
    }

    private void log(String message){
        System.out.println(message);
    }
}
