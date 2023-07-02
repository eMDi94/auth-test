package org.marco.authdemo.userregistration.exceptions;

public class UserRegistrationException extends Throwable {
    public UserRegistrationException() {
    }

    public UserRegistrationException(String detailMessage) {
        super(detailMessage);
    }

    public UserRegistrationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UserRegistrationException(Throwable throwable) {
        super(throwable);
    }

    public UserRegistrationException(String detailMessage, Throwable throwable, boolean enableSuppression, boolean enableWritableStackTrace) {
        super(detailMessage, throwable, enableSuppression, enableWritableStackTrace);
    }
}
