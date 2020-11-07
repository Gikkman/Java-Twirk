package com.gikk.twirk;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

class Util {
    static void printError(TwirkLogger logger, Exception e) {
        logger.error(e.getMessage());
        AtomicBoolean firstLine = new AtomicBoolean(true);
        Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .map(s -> firstLine.getAndSet(false) ? s : "\t" + s)
                .forEach(logger::error);
    }
}
