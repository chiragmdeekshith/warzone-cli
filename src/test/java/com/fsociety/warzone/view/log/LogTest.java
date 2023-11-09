package com.fsociety.warzone.view.log;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class performs unit tests for the Logging module
 */
class LogTest {

    /**
     * Common file name for reading and writing for tests
     */
    String d_file = "src/test/resources/logs/test.log";

    /**
     * Ensure that an exception is thrown if the file name is null
     */
    @Test
    void testInitLogEntryBuffer_FileNameNull() {
        assertThrows(RuntimeException.class, () -> Log.initLogEntryBuffer(null));
    }

    /**
     * Ensure that the log file contains the log data.
     */
    @Test
    void testLog_successfulFileWrite() {
        LogEntryBuffer.clearBuffer();

        String l_log1 = "Test log 1";
        String l_log2 = "Test log 2";

        Log.initLogEntryBuffer(d_file);

        Log.log(l_log1);
        Log.log(l_log2);
        Log.flushToFile();

        FileReader l_fileReader ;
        try {
            l_fileReader = new FileReader(d_file);
        } catch (FileNotFoundException e) {
            fail();
            return;
        }
        BufferedReader l_bufferedReader = new BufferedReader(l_fileReader);
        try {
            assertEquals(l_log1, l_bufferedReader.readLine());
            assertEquals(l_log2, l_bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }

    }
}
