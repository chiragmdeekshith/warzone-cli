package com.fsociety.warzone.game.log;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fsociety.warzone.view.log.Log;
import org.junit.jupiter.api.Test;

class LogTest {

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
        Log.initLogEntryBuffer("log.txt");
        Log.log("Test log");
        Log.endCurrentTurn();
    }
}
