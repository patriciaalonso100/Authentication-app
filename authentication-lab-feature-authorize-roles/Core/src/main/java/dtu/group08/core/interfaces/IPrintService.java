package dtu.group08.core.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrintService extends Remote {

    IActionResult<Integer> print(String filename, String printer, String token) throws RemoteException;   // prints file filename on the specified printer

    IActionResult<String[]> queue(String printer, String token) throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>

    IActionResult<Boolean> topQueue(String printer, int jobId, String token) throws RemoteException;   // moves job to the top of the queue

    IActionResult<Void> start(String token) throws RemoteException;   // starts the print server

    IActionResult<Void> stop(String token) throws RemoteException;   // stops the print server

    IActionResult<Void> restart(String token) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again

    IActionResult<String> status(String printer, String token) throws RemoteException;  // prints status of printer on the user's display

    IActionResult<String> readConfig(String parameter, String token) throws RemoteException;   // prints the value of the parameter on the print server to the user's display

    IActionResult<Boolean> setConfig(String parameter, String value, String token) throws RemoteException;   // sets the parameter on the print server to value
}
