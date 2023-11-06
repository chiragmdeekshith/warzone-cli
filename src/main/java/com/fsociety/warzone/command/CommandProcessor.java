package com.fsociety.warzone.command;

import com.fsociety.warzone.phase.Phase;
import com.fsociety.warzone.util.Console;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {

    private static final Map<String, Command> d_commands;

    static {
        d_commands = new HashMap<>();
        for (Command l_command : Command.values()) {
            d_commands.put(l_command.getCommand(), l_command);
        }
    }

    public static void processCommand(String p_rawCommand, Phase p_phase) {
        if(p_rawCommand.isEmpty()) {
            Console.print("Command is empty. It cannot be empty.");
            return;
        }
        String[] l_parsedCommand = parseCommand(p_rawCommand);
    }

    private static String[] parseCommand(String p_rawCommand) {
        return p_rawCommand.split(" ");
    }

    private static boolean validateCommand(String[] p_parsedCommand) {
        String l_commandName = p_parsedCommand[0];

        // If the command is not present in the list of valid commands, the command is not valid
        if(!d_commands.containsKey(l_commandName)) {
            return false;
        }


        switch (d_commands.get(l_commandName)) {
            case BACK -> {

            }
        }
        return true;
    }

    private static boolean executeCommand() {
        return false;
    }
}
