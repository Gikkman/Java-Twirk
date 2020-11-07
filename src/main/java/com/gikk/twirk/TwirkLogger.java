package com.gikk.twirk;

import java.util.function.Consumer;

class TwirkLogger {

    private final Consumer<String> errorConsumer;
    private final Consumer<String> warnConsumer;
    private final Consumer<String> infoConsumer;
    private final Consumer<String> debugConsumer;

    TwirkLogger(Consumer<String> errorConsumer,
                Consumer<String> warnConsumer,
                Consumer<String> infoConsumer,
                Consumer<String> debugConsumer) {
        this.errorConsumer = errorConsumer == null ? this::noop : errorConsumer;
        this.warnConsumer = warnConsumer == null ? this::noop : warnConsumer;
        this.infoConsumer = infoConsumer == null ? this::noop : infoConsumer;
        this.debugConsumer = debugConsumer == null ? this::noop : debugConsumer;
    }

    void error(String message) {
        errorConsumer.accept(message);
    }
    void warn(String message) {
        warnConsumer.accept(message);
    }
    void info(String message) {
        infoConsumer.accept(message);
    }
    void debug(String message) {
        debugConsumer.accept(message);
    }

    private void noop(String s) {}
}
