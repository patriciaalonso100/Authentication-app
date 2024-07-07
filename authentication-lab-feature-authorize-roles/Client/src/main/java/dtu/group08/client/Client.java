package dtu.group08.client;

import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.core.interfaces.IPrintService;
import dtu.group08.core.interfaces.IWorkerManagementService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import dtu.group08.core.interfaces.IAuthenticationService;
import java.util.List;

public class Client {

    private static String userToken;
    private static IPrintService printService;
    private static IAuthenticationService authService;
    private static IWorkerManagementService workerManagementService;

    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        String baseURI = "rmi://localhost:5099/";
        printService = (IPrintService) Naming.lookup(baseURI + "PrintService");
        authService = (IAuthenticationService) Naming.lookup(baseURI + "AuthenticationService");
        workerManagementService = (IWorkerManagementService) Naming.lookup(baseURI + "WorkerManagementService");

        runInteractiveMode();
    }

    private static void runInteractiveMode() {
        userToken = handleLogin();
        while (userToken != null) {
            handleSelection(getAction());
            sleep(3);
        }
    }

    private static String handleLogin() {
        String result = null;
        while (result == null) {
            try {
                Scanner scanner = new Scanner(System.in);
                println("Enter username:");
                String username = scanner.nextLine();
                println("Enter password:");
                String password = scanner.nextLine();
                IActionResult<String> authResult = authService.login(username, password);
                if (authResult.isSuccessfull()) {
                    result = authResult.getData();
                    println("Login successfull!");
                } else {
                    System.err.println("Login failed! Try again.");
                }
            } catch (RemoteException e) {
                println("AuthenticationService not available. Start the server and try again!");
            }
        }
        return result;
    }

    private static Action getAction() {
        while (true) {
            printEmptyLines(2);
            Scanner scanner = new Scanner(System.in);
            // Print all options
            for (Action option : Action.values()) {
                println(option.getValue() + ": " + option.getDescription());
            }

            println("Enter an option number:");

            try {
                int input = scanner.nextInt();
                Action selectedOption = Action.fromValue(input);
                return selectedOption;
            } catch (Exception e) {
                printEmptyLines(5);
                println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleSelection(Action selectedAction) {
        // Use a switch statement to perform actions based on the user's input
        try {
            switch (selectedAction) {
                case PRINT:
                    handlePrint();
                    break;
                case QUEUE:
                    handleGetQueue();
                    break;
                case TOP_QUEUE:
                    handleTopQueue();
                    break;
                case START:
                    handleStart();
                    break;
                case STOP:
                    handleStop();
                    break;
                case RESTART:
                    handleRestart();
                    break;
                case STATUS:
                    handlePrintStatus();
                    break;
                case READ_CONFIGURATION:
                    handleReadConfig();
                    break;
                case SET_CONFIGURATION:
                    handleSetConfig();
                    break;
                case DELETE_WORKER:
                    handleDeleteWorker();
                    break;
                case ADD_WORKER:
                    handleAddWorker();
                    break;
                case SET_USER_ROLE: 
                    handleSetUserRole();
                    break;
                case SHOW_USERS:
                    handleShowUsers();
                    break;
                case SHOW_USER_PERMISSIONS:
                    handleShowPermissions();
                    break;
                case LOGOUT:
                    handleLogout();
                    break;
                default:
                    println("Invalid command! Please select again.");
                    break;
            }
        } catch (RemoteException e) {
            System.err.println("Remote service not available!");
        }
    }

    private static void printEmptyLines(int num) {
        // set num to 5 in case of insane value
        num = (num < 1 || num > 10) ? 5 : num;
        for (int i = 0; i < num; i++) {
            println("");
        }
    }

    private static void println(String text) {
        System.out.println(text);
    }

    private static void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            println("Failed to sleep");
        }
    }
    
    private static void handleLogout() {
        try {
            printEmptyLines(5);
            println("Logging out...");
            authService.logout(userToken);
        } catch (RemoteException e) {
        }
        sleep(3);
        userToken = null;
    }

    private static void handleSetConfig() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        println("Config name:");
        String parameter = scanner.nextLine();
        println("Config value:");
        String value = scanner.nextLine();
        IActionResult<Boolean> response = (IActionResult<Boolean>) printService.setConfig(parameter, value, userToken);
        if (response.isSuccessfull() && response.getData()) {
            println("Config value changed to " + value);
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handleReadConfig() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter parameter:");
        String parameter = scanner.nextLine();
        IActionResult<String> response = (IActionResult<String>) printService.readConfig(parameter, userToken);
        if (response.isSuccessfull()) {
            println(response.getData());
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handlePrint() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filename:");
        String filename = scanner.nextLine();
        System.out.println("Enter printer:");
        String printer = scanner.nextLine();
        IActionResult<Integer> response = (IActionResult<Integer>) printService.print(filename, printer, userToken);
        if (response.isSuccessfull()) {
            println("Print job sat to queue. Job Id: " + response.getData());
        } else {
            handleUnsuccessfullReponse(response);
        }
    }
    
    private static void handleDeleteWorker() throws RemoteException {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter worker's id:");
        String id = scanner.nextLine();
        IActionResult<Void> response = (IActionResult<Void>) workerManagementService.deleteWorker(id, userToken);
        if (response.isSuccessfull()) {
            println("Worker " + id + " deleted.");
        } else {
            handleUnsuccessfullReponse(response);
        }
    }
    
    private static void handleAddWorker() throws RemoteException {
        
        Scanner scanner = new Scanner(System.in);
        println("What's the new employees name?");
        String name = scanner.nextLine();
        println("Username:");
        String username = scanner.nextLine();
        println("Enter a temporary password");
        String password = scanner.nextLine();
        println("What role should he/she have?");
        String userRole = scanner.nextLine();        
        IActionResult<Void> response = (IActionResult<Void>) workerManagementService.addWorker(name, username, password, userRole, userToken);
        if (response.isSuccessfull()) {
            println("New user is with username " + username + " added.");
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handleGetQueue() throws RemoteException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter printer:");
        String printer = scanner.nextLine();
        IActionResult<String[]> response = (IActionResult<String[]>) printService.queue(printer, userToken);
        if (response.isSuccessfull() && response.getData() != null) {
            println("Job list for printer: " + printer);
            for (String job : response.getData()) {
                println(job);
            }
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handleTopQueue() throws RemoteException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter printer:");
        String printer = scanner.nextLine();
        System.out.println("Enter jobId:");
        int jobId = scanner.nextInt();
        IActionResult<Boolean> response = (IActionResult<Boolean>) printService.topQueue(printer, jobId, userToken);
        if (response.isSuccessfull() && response.getData()) {
            println("Job sat on top of queue for printer " + printer);
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handlePrintStatus() throws RemoteException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter printer:");
        String printer = scanner.nextLine();
        IActionResult<String> response = (IActionResult<String>) printService.status(printer, userToken);
        if (response.isSuccessfull()) {
            println("Status: " + response.getData());
        } else {
            handleUnsuccessfullReponse(response);
        }
    }
    
    private static void handleSetUserRole() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter new Role name:");
        String role = scanner.nextLine();
        IActionResult<String> response = workerManagementService.setUserRole(username, role, userToken);
        if (response.isSuccessfull()) {
            println("  - Role updated!");
            println("");
            println("Showing updated user list...");
            handleShowUsers();
        } else {
            handleUnsuccessfullReponse(response);
        }
    }
    
    private static void handleShowUsers() throws RemoteException {
        println("  Fetching users...");
        IActionResult<List<String>> response = workerManagementService.getUsers(userToken);
        if (response.isSuccessfull()) {
            for(String item : response.getData())
            {
                IActionResult<String> userRoleResponse = workerManagementService.getUserRole(item, userToken);
                println( "  - "+item+" : "+userRoleResponse.getData());
            }
        } else {
            handleUnsuccessfullReponse(response);
        }
    }
    
    public static void handleShowPermissions()  throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        IActionResult<List<String>> response = workerManagementService.getUserPermissions(username, userToken);
        if (response.isSuccessfull()) {
            for(String item : response.getData())
                println("  - "+ item);
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handleUnsuccessfullReponse(IActionResult response) {
        switch (response.getStatusCode()) {
            case 401 : {
                println("insufficient permission for this request!");
                sleep(2);
            }
            default:
                println("Request failed with message: " + response.getMessage());
        }
    }

    private static void handleRestart() throws RemoteException {
        IActionResult<Void> response = (IActionResult<Void>) printService.restart(userToken);
        if (response.isSuccessfull()) {
            println("Service restarting.");
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handleStop() throws RemoteException {
        IActionResult<Void> response = (IActionResult<Void>) printService.stop(userToken);
        if (response.isSuccessfull()) {
            println("Successfully stopped service");
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

    private static void handleStart() throws RemoteException {

        IActionResult<Void> response = (IActionResult<Void>) printService.start(userToken);
        if (response.isSuccessfull()) {
            println("Successfully started service");
        } else {
            handleUnsuccessfullReponse(response);
        }
    }

}
