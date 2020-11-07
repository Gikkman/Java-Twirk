package com.gikk.twirk;

import java.util.function.Consumer;

class TwirkLogger {

    private final TwirkLogLevel logLevel;
    private final Consumer<String> errorConsumer;
    private final Consumer<String> warnConsumer;
    private final Consumer<String> infoConsumer;
    private final Consumer<String> debugConsumer;

    TwirkLogger(TwirkLogLevel logLevel,
                Consumer<String> errorConsumer,
                Consumer<String> warnConsumer,
                Consumer<String> infoConsumer,
                Consumer<String> debugConsumer) {
        this.logLevel = logLevel;
        this.errorConsumer = errorConsumer;
        this.warnConsumer = warnConsumer;
        this.infoConsumer = infoConsumer;
        this.debugConsumer = debugConsumer;
    }

    TwirkLogLevel getLogLevel() {
        return logLevel;
    }

    void error(String message) {
        if(logLevel.isAtLeast(TwirkLogLevel.ERROR))
            errorConsumer.accept(message);
    }
    void warn(String message) {
        if(logLevel.isAtLeast(TwirkLogLevel.WARN))
            warnConsumer.accept(message);
    }
    void info(String message) {
        if(logLevel.isAtLeast(TwirkLogLevel.INFO))
            infoConsumer.accept(message);
    }
    void debug(String message) {
        if(logLevel.isAtLeast(TwirkLogLevel.DEBUG))
            debugConsumer.accept(message);
    }
}
