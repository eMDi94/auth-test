package org.marco.authdemo.system.gdpr;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

public class LogMaskConverter extends CompositeConverter<ILoggingEvent> {

    public String transform(ILoggingEvent event, String in) {
        in = in.replaceAll("(?i)(?<=('|\"?)firstName('|\"?)=)('|\"?)\\w+('|\"?)", "[PROTECTED]");
        in = in.replaceAll("(?i)(?<=('|\"?)lastName('|\"?)=)('|\"?)\\w+('|\"?)", "[PROTECTED]");
        in = in.replaceAll("(?i)(?<=('|\"?)email('|\"?)=)('|\"?)([a-z0-9_.-]+)@([\\da-z.-]+)('|\"?)", "[PROTECTED]");
        in = in.replaceAll("(?i)(?<=('|\"?)fiscalCode('|\"?)=)('|\"?)\\w+('|\"?)", "[PROTECTED]");
        in = in.replaceAll("(?i)(?<=('|\"?)username('|\"?)=)('|\"?)\\w+('|\"?)", "[PROTECTED]");
        in = in.replaceAll("(?i)(?<=('|\"?)passwordHash('|\"?)=)('|\"?)([a-zA-z0-9_.$/]+)('|\"?)", "[PROTECTED]");
        return in;
    }
}
