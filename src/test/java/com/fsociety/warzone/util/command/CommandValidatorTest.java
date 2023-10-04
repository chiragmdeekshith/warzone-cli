package com.fsociety.warzone.util.command;

import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.command.constant.Phase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandValidatorTest {

    @BeforeEach
    void setUp() {
        System.out.println("Testing the command validator");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finished testing command validator");
    }

    /**
     * Verifies proper behaviour of Command Validator when given valid commands based on the game phase.
     * Test method for {@link CommandValidator#isValidCommand(String, Phase)}
     */
    @Test
    void isValidCommand_success() {
        assertTrue(CommandValidator.isValidCommand("loadmap euro.map", Phase.START_UP));
        assertTrue(CommandValidator.isValidCommand("assigncountries", Phase.START_UP));
        assertTrue(CommandValidator.isValidCommand("showmap", Phase.START_UP));
        assertTrue(CommandValidator.isValidCommand("showmap", Phase.MAP_EDITOR));
        assertTrue(CommandValidator.isValidCommand("showmap", Phase.GAME_PLAY));
        assertTrue(CommandValidator.isValidCommand("gameplayer -add a -add b -remove a -add a", Phase.START_UP));
        assertTrue(CommandValidator.isValidCommand("editmap euro.map", Phase.MAP_EDITOR));
        assertTrue(CommandValidator.isValidCommand("deploy 2 5", Phase.GAME_PLAY));

    }

    /**
     * Verifies proper behaviour of Command Validator when given invalid commands based on the game phase.
     * Test method for {@link CommandValidator#isValidCommand(String, Phase)}
     */
    @Test
    void isValidCommand_fail() {
        assertFalse(CommandValidator.isValidCommand("", Phase.START_UP));
        assertFalse(CommandValidator.isValidCommand("editcontinent -addd 1 2", Phase.MAP_EDITOR));
        assertFalse(CommandValidator.isValidCommand("editcountry -remove 1 4 5", Phase.MAP_EDITOR));
        assertFalse(CommandValidator.isValidCommand("loadmap euro.map", Phase.GAME_PLAY));
        assertFalse(CommandValidator.isValidCommand("deploy 25", Phase.GAME_PLAY));

    }
}