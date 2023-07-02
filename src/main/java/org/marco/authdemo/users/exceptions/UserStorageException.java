package org.marco.authdemo.users.exceptions;

public class UserStorageException extends Throwable {
    public UserStorageException() {
    }

    public UserStorageException(String detailMessage) {
        super(detailMessage);
    }

    public UserStorageException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UserStorageException(Throwable throwable) {
        super(throwable);
    }

    public UserStorageException(String detailMessage, Throwable throwable, boolean enableSuppression, boolean enableWritableStackTrace) {
        super(detailMessage, throwable, enableSuppression, enableWritableStackTrace);
    }
}
