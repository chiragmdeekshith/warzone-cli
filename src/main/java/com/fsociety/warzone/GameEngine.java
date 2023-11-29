package com.fsociety.warzone;

import com.fsociety.warzone.asset.command.CommandProcessor;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.view.log.Log;

/**
 * This Class implements the Game Engine that takes in user commands and reacts accordingly.
 */
public class GameEngine {

    /**
     * The phase of the application
     */
    private static Phase d_phase;

    /**
     * This function starts the main menu for the game where most commands are gathered and sent to be processed.
     */
    public static void mainMenu() {
        Log.initLogEntryBuffer("src/main/resources/logs/" + System.currentTimeMillis() + ".log");
        GameEngine.setPhase(new Menu());
        String l_command;
        while (true) {
            l_command = Console.commandPrompt();
            CommandProcessor.processCommand(l_command);
        }
    }

    /**
     * The very cool Warzone logo
     */
    private static final String d_logo = """
                                                          \s
                 _ _ _ _____ _____ _____ _____ _____ _____\s
                | | | |  _  | __  |__   |     |   | |   __|
                | | | |     |    -|   __|  |  | | | |   __|
                |_____|__|__|__|__|_____|_____|_|___|_____|
                                                          \s""";

    // Getters and setters

    /**
     * Get the current phase.
     * @return the current phase
     */
    public static Phase getPhase() {
        return d_phase;
    }

    /**
     * Set the current phase.
     * @param p_phase the new phase
     */
    public static void setPhase(Phase p_phase) {
        d_phase = p_phase;
    }

    /**
     * This method prints the Warzone logo when returning to the main menu.
     */
    public static void printLogo() {
        Console.print(d_logo);
    }
}
