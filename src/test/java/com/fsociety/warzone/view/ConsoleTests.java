package com.fsociety.warzone.view;

import org.junit.jupiter.api.*;

import com.fsociety.warzone.view.log.Log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;

class ConsoleTests {

    private final ByteArrayOutputStream d_outContent = new ByteArrayOutputStream();
    private final PrintStream d_originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayInputStream d_inStream;
    private final String d_file = "src/test/resources/logs/console_test.log";

    @BeforeEach
    public void setUpStreams() {
        String l_input = "This is my input";
        d_inStream = new ByteArrayInputStream(l_input.getBytes());
        System.setIn(d_inStream);
        System.setOut(new PrintStream(d_outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(d_originalOut);
    }

    @Test
    void testCommandPromptPlayer() {
        final String l_playerName = "player1";
        final String l_prompt = "Please enter a command.\n" + l_playerName + "> ";
        Console.commandPromptPlayer(l_playerName);
        Assertions.assertEquals(l_prompt, d_outContent.toString());
    }

    @Test
    void testPrint() {
        final String l_prompt = "Please print this message.";
        Console.print(l_prompt);
        Assertions.assertEquals(l_prompt + "\r\n", d_outContent.toString());
    }

    @Test
    void testPrint2_no_log() {
        final String l_prompt = "Please print this message.";
        Console.print(l_prompt, false);
        Assertions.assertEquals(l_prompt + "\r\n", d_outContent.toString());
    }

    @Test
    void testPrint2_log() {
        final String l_prompt = "Please print this message.";
        Log.initLogEntryBuffer(d_file);
        Console.print(l_prompt, true);
        Log.flushToFile();
        Assertions.assertEquals(l_prompt + "\r\n", d_outContent.toString());

        FileReader l_fileReader ;
        try {
            l_fileReader = new FileReader(d_file);
        } catch (FileNotFoundException e) {
            fail();
            return;
        }
        BufferedReader l_bufferedReader = new BufferedReader(l_fileReader);
        try {
            assertEquals(l_prompt, l_bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }
    }  
}
