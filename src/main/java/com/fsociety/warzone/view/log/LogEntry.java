package com.fsociety.warzone.view.log;

/**
 * The LogEntry class represents individual log entries with a message.
 */
public class LogEntry {
    private final String d_message;

    /**
     * Constructs a LogEntry with the specified log message.
     *
     * @param p_message The log message to be stored in this LogEntry.
     */
    public LogEntry(String p_message) {
        this.d_message = p_message;
    }

    /**
     * Gets the log message stored in this LogEntry.
     *
     * @return The log message.
     */
    public String getMessage() {
        return d_message;
    }
}
