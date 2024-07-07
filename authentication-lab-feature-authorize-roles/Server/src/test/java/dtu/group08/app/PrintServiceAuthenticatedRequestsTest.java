package dtu.group08.app;

import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.core.interfaces.IAuthenticationService;
import dtu.group08.core.interfaces.IPrintService;
import dtu.group08.core.models.Ok;
import dtu.group08.server.services.PrintService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class PrintServiceAuthenticatedRequestsTest {

    /**
     * System under test
     */
    private IPrintService _sut;
    private final String token = "testtoken";

    @BeforeEach
    public void setUp() throws Exception {
        IAuthenticationService authService = mock(IAuthenticationService.class);
        when(authService.isAuthorized(anyString(),anyString())).thenReturn(new Ok(true));
        _sut = new PrintService(authService);
    }

    @AfterEach
    public void tearDown() throws Exception {
        _sut = null;
    }

    @Test
    public void testPrint() throws Exception {
        System.out.println("Test Print");
        String filename = "File1";
        String printer = "Printer1";
        int expResult = 1;
        int result = _sut.print(filename, printer, token).getData();
        assertEquals(expResult, result);
    }

    @Test
    public void testQueue() throws Exception {
        System.out.println("Test Queue");
        String printer = "printer1";
        assertEquals(0, _sut.queue(printer, token).getData().length);

        _sut.print("TestFile", printer, token);
        assertEquals(1, _sut.queue(printer, token).getData().length);
    }

    @Test
    public void testTopQueue() throws Exception {
        System.out.println("Test TopQueue");
        String printer = "printer2";
        int job = 4;
        String fileName = "TestFile";
        _sut.print(fileName, printer, token);
        _sut.print(fileName, printer, token);
        _sut.print(fileName, printer, token);
        _sut.print(fileName, printer, token);
        _sut.print(fileName, printer, token);
        _sut.print(fileName, printer, token);
        String[] jobs = _sut.queue(printer, token).getData();
        assertEquals("1 " + fileName, jobs[0]);

        assertTrue(_sut.topQueue(printer, job, token).getData());
        jobs = _sut.queue(printer, token).getData();
        assertEquals("4 " + fileName, jobs[0]);
    }

    @Test
    public void testStart() throws Exception {
        System.out.println("Test Start");
        IActionResult res = _sut.start(token);
        assertTrue(res.isSuccessfull());
    }

    @Test
    public void testStop() throws Exception {
        System.out.println("Test Stop");
        assertTrue(_sut.stop(token).isSuccessfull());
    }

    @Test
    public void testRestart() throws Exception {
        System.out.println("Test Restart");
        assertTrue(_sut.restart(token).isSuccessfull());
    }

    @Test
    public void testStatus() throws Exception {
        System.out.println("Test Status");
        String printer = "PRINT1";
        assertEquals(printer + ": ON", _sut.status(printer, token).getData());

        _sut.stop(token);
        assertEquals(printer + ": OFF", _sut.status(printer, token).getData());

        _sut.restart(token);
        assertEquals(printer + ": ON", _sut.status(printer, token).getData());
    }

    @Test
    public void testReadConfig() throws Exception {
        System.out.println("Test ReadConfig");
        String parameter = "PaperSize";
        String expResult = "A4";
        String result = _sut.readConfig(parameter, token).getData();
        assertEquals(expResult, result);

        String newValue = "A3";
        _sut.setConfig(parameter, newValue, token);
        assertEquals(newValue, _sut.readConfig(parameter, token).getData());
    }

    @Test
    public void testSetConfig() throws Exception {
        System.out.println("Test SetConfig");
        String parameter = "PaperSize";
        String value = "A4";
        boolean expResult = true;
        boolean result = _sut.setConfig(parameter, value, token).getData();
        assertEquals(expResult, result);
        String actualValue = _sut.readConfig(parameter, token).getData();
        assertEquals(value, actualValue);
        IActionResult<Boolean> response = _sut.setConfig("randomconfig", "randomvalue", token);
        assertEquals(400, response.getStatusCode());
        assertNull(response.getData());
    }
}
