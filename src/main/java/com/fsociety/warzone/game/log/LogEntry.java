package com.fsociety.warzone.game.log;

/**
 * The LogEntry class represents individual log entries with a message.
 */
public class LogEntry {
    private String message;

    /**
     * Constructs a LogEntry with the specified log message.
     *
     * @param message The log message to be stored in this LogEntry.
     */
    public LogEntry(String message) {
        this.message = message;
    }

    /**
     * Gets the log message stored in this LogEntry.
     *
     * @return The log message.
     */
    public String getMessage() {
        return message;
    }
}
