package dtu.group08.core.models;

import dtu.group08.core.models.StatusCode;

public class Forbidden<T> extends ActionResult<T> {

    public Forbidden() {
        super(StatusCode.FORBIDDEN, StatusCode.FORBIDDEN.getMessage(), null);
    }
}
