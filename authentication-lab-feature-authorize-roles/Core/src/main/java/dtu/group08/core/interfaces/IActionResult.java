package dtu.group08.core.interfaces;

public interface IActionResult<T> {

    int getStatusCode();

    String getMessage();

    boolean isSuccessfull();

    T getData();
}
