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
        String input = "This is my input";
        d_inStream = new ByteArrayInputStream(input.getBytes());
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
        final String p_playerName = "player1";
        final String p_prompt = "Please enter a command.\n" + p_playerName + "> ";
        Console.commandPromptPlayer(p_playerName);
        Assertions.assertEquals(p_prompt, d_outContent.toString());
    }

    @Test
    void testPrint() {
        final String p_prompt = "Please print this message.";
        Console.print(p_prompt);
        Assertions.assertEquals(p_prompt + "\r\n", d_outContent.toString());
    }

    @Test
    void testPrint2_no_log() {
        final String p_prompt = "Please print this message.";
        Console.print(p_prompt, false);
        Assertions.assertEquals(p_prompt + "\r\n", d_outContent.toString());
    }

    @Test
    void testPrint2_log() {
        final String p_prompt = "Please print this message.";
        Log.initLogEntryBuffer(d_file);
        Console.print(p_prompt, true);
        Log.flushToFile();
        Assertions.assertEquals(p_prompt + "\r\n", d_outContent.toString());

        FileReader l_fileReader ;
        try {
            l_fileReader = new FileReader(d_file);
        } catch (FileNotFoundException e) {
            fail();
            return;
        }
        BufferedReader l_bufferedReader = new BufferedReader(l_fileReader);
        try {
            assertEquals(p_prompt, l_bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }
    }  
}
