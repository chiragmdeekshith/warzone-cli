package com.fsociety.warzone.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ConsoleTests {

    private final ByteArrayOutputStream d_outContent = new ByteArrayOutputStream();
    private final PrintStream d_originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(d_outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(d_originalOut);
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
}
