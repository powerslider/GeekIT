package com.ceco.geekit.app.exception;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 17 Oct 2015
 */
public class GeekItException extends RuntimeException {

    public GeekItException(String detailMessage) {
        super(detailMessage);
    }

    public GeekItException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    @Override
    public String getMessage() {
        return ">>> GeekIT Error <<<: " + super.getMessage();
    }
}
