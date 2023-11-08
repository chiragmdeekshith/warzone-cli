package com.fsociety.warzone.view.log;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fsociety.warzone.view.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogTest {

    String d_file = "src/test/resources/logs/log.txt";

    @Test
    void testEndCurrentTurn() {

    }

    @Test
    void testInitLogEntryBuffer_FileNameNull() {
        assertThrows(RuntimeException.class, () -> {
            Log.initLogEntryBuffer(null);
        });
    }

    @Test
    void testLog_success() {
        Log.initLogEntryBuffer(d_file);
        Log.log("Test log 1");
        Log.log("Test log 2");
        Log.endCurrentTurn();
    }
}
