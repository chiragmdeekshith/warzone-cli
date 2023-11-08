package com.fsociety.warzone.view.log;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    String d_file = "src/test/resources/logs/log.txt";

    @Test
    void testInitLogEntryBuffer_FileNameNull() {
        assertThrows(RuntimeException.class, () -> Log.initLogEntryBuffer(null));
    }

    @Test
    void testLog_successfulFileWrite() {
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
