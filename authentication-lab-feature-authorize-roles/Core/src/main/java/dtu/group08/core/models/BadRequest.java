package dtu.group08.core.models;

public class BadRequest<T> extends ActionResult<T> {

    public BadRequest(String message) {
        super(StatusCode.BAD_REQUEST, message, null);
    }
}
