package dtu.group08.core.models;

public class Ok<T> extends ActionResult<T> {

    public Ok(T data) {
        super(StatusCode.OK, StatusCode.OK.getMessage(), data);
    }
}
