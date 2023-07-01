package org.marco.authdemo.gdpr;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;

public class LogMaskConverter extends CompositeConverter<ILoggingEvent> {

    public String transform(ILoggingEvent event, String in) {

        in = in.replaceAll("(?<=firstName=')[^']+?(?=')|(?<=\"firstName\":\")[^\"]+?(?=\")", "****");
        in = in.replaceAll("(?<=lastName=')[^']+?(?=')|(?<=\"lastName\":\")[^\"]+?(?=\")", "****");
        in = in.replaceAll("(?<=email=)\\d+(?=(,|\\s|}))|(?<=\"email\":)\\d+(?=(,|\\s|}))", "****");
        in = in.replaceAll("(?<=username=)\\d+(?=(,|\\s|}))|(?<=\"username\":)\\d+(?=(,|\\s|}))", "****");
        in = in.replaceAll("(?<=password=)\\d+(?=(,|\\s|}))|(?<=\"password\":)\\d+(?=(,|\\s|}))", "****");
        in = in.replaceAll("(?<=fiscalCode=)\\d+(?=(,|\\s|}))|(?<=\"fiscalCode\":)\\d+(?=(,|\\s|}))", "****");

        return in;
    }
}
