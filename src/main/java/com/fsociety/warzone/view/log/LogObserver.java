package com.fsociety.warzone.view.log;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The LogObserver class writes log entries to a file.
 */
public class LogObserver implements Observer {
    private final FileWriter d_fileWriter;

    /**
     * Constructs a LogObserver instance that writes log entries to the specified
     * file.
     *
     * @param p_filename The name of the log file to write to.
     */
    public LogObserver(final String p_filename) {
        try {
            this.d_fileWriter = new FileWriter(p_filename);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Exception while initializing file to write logs : \n" + e.getMessage());
        }
    }

    /**
     * Updates the observer with a new log entry and writes it to the log file.
     *
     * @param p_o   The Observable object (not used in this implementation).
     * @param p_arg The log entry to be written to the log file.
     */
    @Override
    public void update(final Observable p_o, final Object p_arg) {
        final LogEntry l_logEntry = (LogEntry) p_arg;
        try {
            d_fileWriter.write(l_logEntry.getMessage() + "\n");
            d_fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Exception while writing logs: " + e.getMessage());
        }
    }
}
