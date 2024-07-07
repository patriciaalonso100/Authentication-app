package dtu.group08.app;

import dtu.group08.core.interfaces.IActionResult;
import dtu.group08.core.interfaces.IAuthenticationService;
import dtu.group08.core.interfaces.IPrintService;
import dtu.group08.server.services.PrintService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import dtu.group08.core.models.Ok;

public class PrintServiceUnauthenticatedRequestsTest {

    /**
     * System under test
     */
    private IPrintService _sut;
    private final String token = "testtoken";

    @BeforeEach
    public void setUp() throws Exception {
        IAuthenticationService authService = mock(IAuthenticationService.class);
        //when(authService.isAuthenticated(token)).thenReturn(new Ok(false));
        when(authService.isAuthorized(anyString(),anyString())).thenReturn(new Ok(false));
        _sut = new PrintService(authService);
    }

    @AfterEach
    public void tearDown() throws Exception {
        _sut = null;
    }

    @Test
    public void testPrint() throws Exception {
        System.out.println("Test print");

        IActionResult result = _sut.print("file1", "printer1", token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testQueue() throws Exception {
        System.out.println("Test queue");

        IActionResult result = _sut.queue("printer1", token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testTopQueue() throws Exception {
        System.out.println("Test topQueue");

        IActionResult result = _sut.topQueue("printer1", 100, token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testStart() throws Exception {
        System.out.println("Test Start");
        IActionResult result = _sut.start(token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testStop() throws Exception {
        System.out.println("Test Stop");
        IActionResult result = _sut.stop(token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testRestart() throws Exception {
        System.out.println("Test Restart");
        IActionResult result = _sut.restart(token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testStatus() throws Exception {
        System.out.println("Test Status");
        IActionResult result = _sut.status("printer1", token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testReadConfig() throws Exception {
        System.out.println("Test ReadConfig");
        IActionResult result = _sut.readConfig("printer1", token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

    @Test
    public void testSetConfig() throws Exception {
        System.out.println("Test SetConfig");
        String parameter = "PaperSize";
        String value = "A4";
        IActionResult result = _sut.setConfig(parameter, value, token);
        assertFalse(result.isSuccessfull());
        assertEquals(401, result.getStatusCode());
        assertEquals(null, result.getData());
    }

}
