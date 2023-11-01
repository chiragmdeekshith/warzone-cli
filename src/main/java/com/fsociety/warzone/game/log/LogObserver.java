package com.fsociety.warzone.game.log;

import java.util.Observable;
import java.util.Observer;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The LogObserver class writes log entries to a file.
 */
public class LogObserver implements Observer {
    private FileWriter fileWriter;

    /**
     * Constructs a LogObserver instance that writes log entries to the specified
     * file.
     *
     * @param filename The name of the log file to write to.
     * @throws IOException If an I/O error occurs while opening the log file.
     */
    public LogObserver(final String filename) throws IOException {
        this.fileWriter = new FileWriter(filename);
    }

    /**
     * Updates the observer with a new log entry and writes it to the log file.
     *
     * @param o   The Observable object (not used in this implementation).
     * @param arg The log entry to be written to the log file.
     */
    @Override
    public void update(final Observable o, final Object arg) {
        if (arg instanceof LogEntry) {
            LogEntry logEntry = (LogEntry) arg;
            try {
                fileWriter.write(logEntry.getMessage() + "\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the log file writer, releasing associated resources.
     *
     * @throws IOException If an I/O error occurs while closing the log file.
     */
    public void close() throws IOException {
        fileWriter.close();
    }
}
