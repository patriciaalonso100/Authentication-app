package dtu.group08.core.models;

import dtu.group08.core.interfaces.IActionResult;
import java.io.Serializable;

public class ActionResult<T> implements IActionResult<T>, Serializable {

    private final StatusCode statusCode;
    private final String message;
    private final T data;

    public ActionResult(StatusCode statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    @Override
    public int getStatusCode() {
        return statusCode.getCode();
    }

    @Override
    public String getMessage() {
        return message == null || message.isBlank()
                ? statusCode.getMessage()
                : message;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccessfull() {
        return statusCode.getCode() < 400;
    }
}
