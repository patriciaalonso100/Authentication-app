package dtu.group08.core.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 *
 * @author patri
 */
public interface IWorkerManagementService extends Remote {
    
     /**
     * 
     * @param id the username of the worker
     * @param sessiontoken represents the login token
     * @return
     * @throws RemoteException 
     */
     IActionResult<Void> deleteWorker (String id, String sessiontoken) throws RemoteException;
     /**
      * @param name
      * @param username
      * @param password
      * @param role the role name. Should be a valid role
      * @param sessionToken represents the login token
      * @return
      * @throws RemoteException 
      */
     IActionResult<Void> addWorker (String name, String username, String password, String role, String sessionToken) throws RemoteException;
    /**
     * @param sessionToken represents the login token
     * @return returns a list of all usernames
     * @throws java.rmi.RemoteException
     */
     IActionResult<List<String>> getUsers(String sessionToken)throws RemoteException;
     /**
      * @param username
      * @param sessionToken represents the login token
      * @return returns a list of permissions
     * @throws java.rmi.RemoteException
      */
     IActionResult<List<String>> getUserPermissions(String username, String sessionToken) throws RemoteException;
     
     IActionResult<String> getUserRole(String username, String sessionToken) throws RemoteException;
     /**
      * @param username
      * @param role
      * @param sessionToken
      * @return
      * @throws RemoteException 
      */
     IActionResult<String> setUserRole(String username, String role, String sessionToken)  throws RemoteException;
}