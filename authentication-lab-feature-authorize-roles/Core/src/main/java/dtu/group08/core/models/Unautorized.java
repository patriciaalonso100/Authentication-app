package dtu.group08.core.models;

public class Unautorized<T> extends ActionResult<T> {

    public Unautorized() {
        super(StatusCode.UNAUTHORIZED, null, null);
    }
}
