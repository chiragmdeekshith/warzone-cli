package com.fsociety.warzone.asset.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test suite for all tests in the CommandValidator class
 */
class CommandValidatorTest {

    /**
     * Ensures that unrecognized commands are not valid
     */
    @Test
    public void testValidateCommand_UnrecognizedCommand() {
        assertFalse(CommandValidator.validateCommand(new String[]{"random"}));
    }

    /**
     * Ensures that commands with too few arguments are not valid
     */
    @Test
    public void testValidateCommand_TooFewArguments() {
        assertFalse(CommandValidator.validateCommand(new String[]{"editmap"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"editcountry"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"editneighbor"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"gameplayer"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"deploy"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"advance"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"bomb"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"negotiate"}));
    }

    /**
     * Ensures that commands with invalid arguments types are not valid
     */
    @Test
    public void testValidateCommand_InvalidArgumentType() {
        assertFalse(CommandValidator.validateCommand(new String[]{"editcountry", "-add", "blah", "blah"}));
        assertFalse(CommandValidator.validateCommand(new String[]{"editneighbor", "-remove", "blah", "blah"}));
    }

    /**
     * Unit test for the common single argument check function
     */
    @Test
    public void testValidateNoArgsCommand_TooManyArguments() {
        assertFalse(CommandValidator.validateNoArgsCommand(new String[]{"showmap", "extra_argument"}));
        assertFalse(CommandValidator.validateNoArgsCommand(new String[]{"commit", "extra_argument"}));
    }

    /**
     * Unit test for the common filename check function
     */
    @Test
    public void testValidateFilenameCommand_InvalidArguments() {
        assertFalse(CommandValidator.validateFilenameCommand(new String[]{"savemap"}));
        assertFalse(CommandValidator.validateFilenameCommand(new String[]{"loadmap", "filename.map", "blah"}));
    }

}