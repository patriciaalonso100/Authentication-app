package dtu.group08.server.services;

import dtu.group08.core.models.Unautorized;
import dtu.group08.core.models.Ok;
import dtu.group08.core.models.ServerError;
import dtu.group08.core.models.ActionResult;
import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.core.interfaces.IAuthenticationService;
import dtu.group08.core.interfaces.IPrintService;
import dtu.group08.core.models.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class PrintService extends UnicastRemoteObject implements IPrintService {

    private final HashMap<String, String> configs = new HashMap<>();
    HashMap<String, Deque<PrintJob>> printers = new HashMap<>();
    private String Status = "ON";
    private final IAuthenticationService _authService;

    public PrintService(IAuthenticationService authService) throws RemoteException {
        super();
        _authService = authService;
        configs.put("Orientation", "Portrait");
        configs.put("PaperSize", "A4");
        configs.put("DoubleSided", "true");
    }

    private boolean isAuthorized(String token, String function) throws RemoteException {
        return _authService.isAuthorized(token, function).getData();
    }

    @Override
    public IActionResult<Integer> print(String filename, String printer, String token) throws RemoteException {
        try {
            if (!isAuthorized(token,"print")) {
                throw new UnauthorizedException();
            }
            Deque<PrintJob> printerQueue = getPrinterQueue(printer);
            int maxId = printerQueue.stream()
                    .mapToInt(PrintJob::getId)
                    .max()
                    .orElse(0);

            PrintJob job = new PrintJob();
            job.FileName = filename;
            job.Id = maxId + 1;
            printerQueue.add(job);
            return new Ok(job.Id);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ActionResult(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    private Deque<PrintJob> getPrinterQueue(String printer) {
        if (!printers.containsKey(printer)) {
            printers.put(printer, new LinkedList<>());
        }
        return printers.get(printer);
    }

    @Override
    public IActionResult<String[]> queue(String printer, String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "showQueue")) {
                throw new UnauthorizedException();
            }
            Deque<PrintJob> queue = getPrinterQueue(printer);
            String[] result = queue.stream()
                    .map(job -> job.Id + " " + job.FileName)
                    .toArray(String[]::new);
            return new Ok(result);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<Boolean> topQueue(String printer, int jobId, String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "topQueue")) {
                throw new UnauthorizedException();
            }

            Deque<PrintJob> queue = getPrinterQueue(printer);
            PrintJob match = null;
            for (PrintJob job : queue) {
                if (job.Id == jobId) {
                    match = job;
                    break;
                }
            }
            if (match == null) {
                return new Ok(false);
            }

            queue.remove(match);
            queue.offerFirst(match);
            return new Ok(true);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<Void> start(String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "start")) {
                throw new UnauthorizedException();
            }

            System.out.println("Starting the service");
            this.Status = "ON";
            return new Ok(null);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<Void> stop(String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "stop")) {
                throw new UnauthorizedException();
            }
            System.out.println("Stopping the service!");
            this.Status = "OFF";
            return new Ok(null);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<Void> restart(String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "restart")) {
                throw new UnauthorizedException();
            }
            stop(token);
            start(token);
            this.printers = new HashMap<>();

            return new Ok(null);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<String> status(String printer, String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "status")) {
                throw new UnauthorizedException();
            }
            return new Ok(printer + ": " + this.Status);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<String> readConfig(String parameter, String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "read_config")) {
                throw new UnauthorizedException();
            }
            String result = configs.containsKey(parameter)
                    ? configs.get(parameter)
                    : "Unknown config name";
            return new Ok(result);

        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }

    @Override
    public IActionResult<Boolean> setConfig(String parameter, String value, String token) throws RemoteException {
        try {
            if (!isAuthorized(token, "set_config")) {
                throw new UnauthorizedException();
            }
            if (!configs.containsKey(parameter)) {
                return new BadRequest("Parameter not found!");
            }
            configs.put(parameter, value);
            return new Ok(true);
        } catch (UnauthorizedException e) {
            return new Unautorized();
        } catch (RemoteException e) {
            return new ServerError(e.getMessage());
        } catch (Exception e) {
            return new ServerError(e.getMessage());
        }
    }
}
