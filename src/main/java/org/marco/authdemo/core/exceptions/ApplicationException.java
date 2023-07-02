package org.marco.authdemo.core.exceptions;

public class ApplicationException extends Throwable {
    public ApplicationException() {
    }

    public ApplicationException(String detailMessage) {
        super(detailMessage);
    }

    public ApplicationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }

    public ApplicationException(String detailMessage, Throwable throwable, boolean enableSuppression, boolean enableWritableStackTrace) {
        super(detailMessage, throwable, enableSuppression, enableWritableStackTrace);
    }
}
