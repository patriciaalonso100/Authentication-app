package dtu.group08.core.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthenticationService extends Remote {

    IActionResult<String> login(String username, String password) throws RemoteException;

    IActionResult<Boolean> logout(String token) throws RemoteException;

    IActionResult<Boolean> changePassword(String username, String oldPassword, String newPassword) throws RemoteException;

    IActionResult<Boolean> isAuthenticated(String token) throws RemoteException;

    IActionResult<Boolean> isAuthorized(String token, String service) throws RemoteException;
}
