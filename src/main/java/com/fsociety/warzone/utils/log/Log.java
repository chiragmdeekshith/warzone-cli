package com.fsociety.warzone.utils.log;

/**
 * Utility class for game logging.
 */
public class Log {

    public static final LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getInstance();

    /**
     * Private constructor to prevent external instantiation.
     * Being a utility,It also throws an exception if this class is instantiated
     * internally.
     */
    private Log() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initializes a log entry buffer and attaches a log observer to it.
     *
     * @param p_filename The name of the log file to write log entries to.
     * @return The initialized LogEntryBuffer.
     */
    public static LogEntryBuffer initLogEntryBuffer(final String p_filename) {
        final LogEntryBuffer l_logEntryBuffer = LogEntryBuffer.getInstance();
        l_logEntryBuffer.addObserver(new LogObserver(p_filename));
        return l_logEntryBuffer;
    }

    /**
     * Adds a log entry to the log entry buffer.
     * 
     * @param p_message The log message to be added
     */
    public static void log(final String p_message) {
        d_logEntryBuffer.addLogEntry(p_message);
    }

    /**
     * Increments the turn number and notifies observers of the change.
     */
    public static void endCurrentTurn() {
        d_logEntryBuffer.endCurrentTurn();
    }
}
