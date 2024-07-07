package dtu.group08.server;

import dtu.group08.core.interfaces.*;
import dtu.group08.data.repositories.DataSeeder;
import dtu.group08.server.services.AuthenticationService;
import dtu.group08.server.services.PrintService;
import dtu.group08.server.services.WorkerManagementService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Server {

    public static void main(String[] args) throws RemoteException, IOException {
        System.out.println("--- Starting PrintService on localhost:5099");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("group08");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        DataSeeder.SeedContext(entityManager);
        IAuthenticationService authService = new AuthenticationService(entityManager);
        IPrintService printService = new PrintService(authService);
        IWorkerManagementService workerManagementService = new WorkerManagementService(entityManager);
        

        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("PrintService", printService);
        registry.rebind("AuthenticationService", authService);
        registry.rebind("WorkerManagementService", workerManagementService);

        // Wait for user input to shut down the server
        System.out.println("Server is running. Press any key to shut down the server...");
        System.in.read();

        // Unexport the remote object
        UnicastRemoteObject.unexportObject(printService, true);
        UnicastRemoteObject.unexportObject(authService, true);
        UnicastRemoteObject.unexportObject(workerManagementService, true);

        // Unexport the registry
        UnicastRemoteObject.unexportObject(registry, true);

        System.out.println("Server shut down");
    }
}
