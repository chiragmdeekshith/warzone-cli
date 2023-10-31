package com.fsociety.warzone.game.log;

import java.util.Observable;

/**
 * A singleton class to buffer and manage log entries for game logging.
 */
public class LogEntryBuffer extends Observable {

    private static LogEntryBuffer d_instance;

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
    public void addLogEntry(String p_message) {
        LogEntry logEntry = new LogEntry(p_message);
        setChanged();
        notifyObservers(logEntry);
    }
}
