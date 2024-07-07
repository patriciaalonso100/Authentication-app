package dtu.group08.core.models;

public class ServerError<T> extends ActionResult<T> {

    public ServerError(String message) {
        super(StatusCode.INTERNAL_SERVER_ERROR, message, null);
    }
}
