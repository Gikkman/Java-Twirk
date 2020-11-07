package com.gikk.twirk;

/**
 * Enum for representing different log verbosity levels withing Twirk.
 */
public enum TwirkLogLevel {
    /**
     * The most narrow of log levels. Only errors/exceptions will be logged.
     */
    ERROR(40),
    /**
     * A more narrow log level. Errors, exceptions and unexpected behaviours will be logged.
     */
    WARN(30),
    /**
     * The default of log level. Messages sent and received won't be logged, but connection open/close,
     * info from Twitch and such will be logged.
     */
    INFO(20),
    /**
     * The highest of log level. Everything sent and received in Twirk will be logged. This is
     * equivalent to the old "verbose" mode.
     */
    DEBUG(10);

    private final int level;
    TwirkLogLevel(int level) {
        this.level = level;
    }

    boolean isAtLeast(TwirkLogLevel logLevel) {
        return this.level <= logLevel.level;
    }
}
