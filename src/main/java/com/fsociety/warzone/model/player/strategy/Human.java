package com.fsociety.warzone.model.player.strategy;

import com.fsociety.warzone.asset.command.CommandProcessor;
import com.fsociety.warzone.view.Console;

import java.io.Serializable;

/**
 * The Human Strategy is used for prompting the player for user input for all gameplay commands and decisions.
 */
public class Human implements Strategy, Serializable {

    /**
     * This function prompts the user for input and then creates an order, and then adds the order to its execution list.
     * @param p_playerName The name of the player that is to be prompted for an input. (Applicable only for Human players)
     */
    @Override
    public void issueOrder(String p_playerName) {
        String l_command = Console.commandPromptPlayer(p_playerName);
        CommandProcessor.processCommand(l_command);
    }

    /**
     * Returns the type of the player strategy as a string.
     * @return the player strategy as a string
     */
    public String toString() {
        return "Human";
    }
}
