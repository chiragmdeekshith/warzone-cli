package com.fsociety.warzone.asset.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Menu;

class CommandProcessorTests {

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
    void testProcessCommand_emptyCommand() {
        final String p_command = "";
        CommandProcessor.processCommand(p_command);
        Assertions.assertEquals("Command is empty. It cannot be empty.\r\n", d_outContent.toString());
    }

    @Test
    void testProcessCommand_invalidCommand() {
        final String p_command = "invalid command";
        CommandProcessor.processCommand(p_command);
        Assertions.assertEquals("Unrecognized command.\r\n", d_outContent.toString());
    }

    @Test
    void testProcessCommand_validCommand() {
        final String p_command = "help";
        GameEngine.setPhase(new Menu());
        CommandProcessor.processCommand(p_command);
        final String expected = "Please enter one of the following commands: (Phase - Menu)\r\n" + //
                "\tplaygame \r\n" + //
                "\tmapeditor \r\n" + //
                "\texit \r\n" + //
                "\tback \r\n" + //
                "\ttournament \r\n" + //
                "\r\n";
        // TODO: Add the assertion after comparing in Intellij IDEA
        // Assertions.assertEquals(1, expected.compareTo(d_originalOut.toString()));
    }

    public static void identifyDifference(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                System.out.println("Difference found at index " + i);
                throw new RuntimeException("Difference found at index " + i);
            }
        }

        if (str1.length() != str2.length()) {
            System.out.println("Strings differ in length");
        } else {
            System.out.println("No differences found");
        }
    }

}
