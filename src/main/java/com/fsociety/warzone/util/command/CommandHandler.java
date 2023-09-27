package com.fsociety.warzone.util.command;

import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.validator.CommandValidator;


import java.util.*;

/**
 * This class handles the validation and processing of all input commands.
 */
public class CommandHandler {

    public static boolean isValidCommand(String p_rawCommand, Phase p_phase) {
        return CommandValidator.isValidCommand(p_rawCommand, p_phase);
    }

    // TODO: Command processor / parser to get objects

}
