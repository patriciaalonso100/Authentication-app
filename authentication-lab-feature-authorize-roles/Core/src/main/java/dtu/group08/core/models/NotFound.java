package dtu.group08.core.models;

public class NotFound<T> extends ActionResult<T> {

    public NotFound() {
        super(StatusCode.BAD_REQUEST, StatusCode.NOT_FOUND.getMessage(), null);
    }
}
