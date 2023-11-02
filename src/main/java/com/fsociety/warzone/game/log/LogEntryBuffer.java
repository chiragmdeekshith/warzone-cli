package com.fsociety.warzone.game.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * A singleton class to buffer and manage log entries for game logging.
 */
public class LogEntryBuffer extends Observable {

    /*
     * The singleton instance of the LogEntryBuffer.
     */
    private static LogEntryBuffer d_instance;

    /*
     * State observed by the observers before writing logs
     * so that we can notify them when the turn is over
     */
    private static Integer d_turnNumber = 0;

    /*
     * The list of log entries to be written to the log file.
     */
    private static List<LogEntry> d_logEntries = new ArrayList<>();

    /**
     * Private constructor to prevent external instantiation.
     */
    private LogEntryBuffer() {
    }

    /**
     * Retrieves the singleton instance of the LogEntryBuffer. If the instance does
     * not exist, it will be created.
     *
     * @return The LogEntryBuffer singleton instance.
     */
    public static LogEntryBuffer getInstance() {
        if (d_instance == null) {
            d_instance = new LogEntryBuffer();
        }
        return d_instance;
    }

    /**
     * Adds a log entry to the buffer and notifies observers of the change.
     *
     * @param p_message The log message to be added.
     */
    public void addLogEntry(final String p_message) {
        final LogEntry logEntry = new LogEntry(p_message);
        d_logEntries.add(logEntry);
    }

    /**
     * Increments the turn number and notifies observers of the change.
     */
    public void endCurrentTurn() {
        d_turnNumber++;

        // Notify observers of the change and flush the buffer
        d_logEntries.stream().forEach(logEntry -> {
            setChanged();
            notifyObservers(logEntry);
        });
        d_logEntries.clear();
    }
}
