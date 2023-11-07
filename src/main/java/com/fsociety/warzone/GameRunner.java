package com.fsociety.warzone;

import com.fsociety.warzone.command.CommandProcessor;
import com.fsociety.warzone.phase.Menu;
import com.fsociety.warzone.phase.Phase;
import com.fsociety.warzone.util.Console;

public class GameRunner {

    private static Phase d_phase;

    /**
     * This function starts the main menu for the game.
     */
    public static void mainMenu() {
        GameRunner.setPhase(new Menu());
        String l_command;
        while(true) {
            l_command = Console.commandPrompt();
            CommandProcessor.processCommand(l_command);
        }
    }

    public static Phase getPhase() {
        return d_phase;
    }

    public static void setPhase(Phase p_phase) {
        d_phase = p_phase;
    }
}
