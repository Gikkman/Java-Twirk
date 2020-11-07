package com.gikk.twirk;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

class Util {
    static String stacktraceToString(Exception e) {
        AtomicBoolean firstLine = new AtomicBoolean(true);
        return Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .map(s -> firstLine.getAndSet(false) ? "\t" + s : s)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
