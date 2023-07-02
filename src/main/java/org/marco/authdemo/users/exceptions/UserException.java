package org.marco.authdemo.users.exceptions;

public class UserException extends Throwable {
    public UserException() {
    }

    public UserException(String detailMessage) {
        super(detailMessage);
    }

    public UserException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UserException(Throwable throwable) {
        super(throwable);
    }

    public UserException(String detailMessage, Throwable throwable, boolean enableSuppression, boolean enableWritableStackTrace) {
        super(detailMessage, throwable, enableSuppression, enableWritableStackTrace);
    }
}
