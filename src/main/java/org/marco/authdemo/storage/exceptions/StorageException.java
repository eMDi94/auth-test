package org.marco.authdemo.storage.exceptions;

public class StorageException extends Throwable {
    public StorageException() {
    }

    public StorageException(String detailMessage) {
        super(detailMessage);
    }

    public StorageException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public StorageException(Throwable throwable) {
        super(throwable);
    }

    public StorageException(String detailMessage, Throwable throwable, boolean enableSuppression, boolean enableWritableStackTrace) {
        super(detailMessage, throwable, enableSuppression, enableWritableStackTrace);
    }
}
