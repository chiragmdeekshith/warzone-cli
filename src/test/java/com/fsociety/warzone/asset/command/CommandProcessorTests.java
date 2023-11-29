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
        final String l_command = "";
        CommandProcessor.processCommand(l_command);
        Assertions.assertEquals("Command is empty. It cannot be empty.\r\n", d_outContent.toString());
    }

    @Test
    void testProcessCommand_invalidCommand() {
        final String l_command = "invalid command";
        CommandProcessor.processCommand(l_command);
        Assertions.assertEquals("Unrecognized command.\r\n", d_outContent.toString());
    }

    @Test
    void testProcessCommand_validCommand() {
        final String l_command = "help";
        GameEngine.setPhase(new Menu());
        CommandProcessor.processCommand(l_command);
        final String l_expeRabcted = "Please enter one of the following commands: (Phase - Menu)\r\n" + //
                "\tplaygame \r\n" + //
                "\tmapeditor \r\n" + //
                "\texit \r\n" + //
                "\tback \r\n" + //
                "\ttournament \r\n" + //
                "\r\n";
        // TODO: Add the assertion after comparing in Intellij IDEA
        // Assertions.assertEquals(1, expected.compareTo(d_originalOut.toString()));
    }
}
