package com.fsociety.warzone.asset.phase;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.edit.EditPreLoad;
import com.fsociety.warzone.asset.phase.play.playsetup.PlayPreLoad;
import com.fsociety.warzone.view.Console;

import java.util.Map;
import java.util.Set;

public class Menu extends Phase {

    @Override
    public void help() {
        Command[] l_validCommands = {Command.PLAY_GAME, Command.MAP_EDITOR, Command.EXIT, Command.BACK};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(help);
    }
    @Override
    public void playGame() {
        Console.print("Starting a new game.");
        GameEngine.setPhase(new PlayPreLoad());
    }

    @Override
    public void mapEditor() {
        Console.print("Map Editor selected. Please start by loading a map. Type 'back' to go to the previous menu.");
        GameEngine.setPhase(new EditPreLoad());
    }

    @Override
    public void exit() {
        System.out.println("Exiting the game. Thank you for playing Warzone!");
        System.exit(0);
    }

    @Override
    public void back() {
        exit();
    }

    @Override
    public void printInvalidCommandMessage() {
        Console.print("Invalid command in the "
                + this.getClass().getSimpleName()
                + " phase. Please use the 'help' command to print a list of valid commands.");
    }

    /**
     *  This method formats the valid commands for a given phase in order to be printed as part of the help message.
     */
    protected String getValidCommands(Command[] p_commands) {
        StringBuilder l_validCommands = new StringBuilder();
        l_validCommands.append("(Phase - ").append(this.getClass().getSimpleName()).append(")\n");
        for (Command l_command : p_commands) {
            l_validCommands.append("\t").append(l_command.getCommand()).append(" ").append(l_command.getHelpMessage()).append("\n");
        }
        return l_validCommands.toString();
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void loadMap(String p_fileName) {
        printInvalidCommandMessage();
    }

    @Override
    public void gamePlayer(Set<String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove) {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    @Override
    public void deploy(int p_countryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    @Override
    public void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb(int p_targetCountryId) {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade(int p_countryId) {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate(String p_targetPlayerId) {
        printInvalidCommandMessage();
    }

    @Override
    public void commit() {
        printInvalidCommandMessage();
    }

    @Override
    public void showCards() { printInvalidCommandMessage(); }

    @Override
    public void showPlayers() { printInvalidCommandMessage(); }

    @Override
    public void showAvailableArmies() { printInvalidCommandMessage(); }

    @Override
    public void editMap(String p_fileName) {
        printInvalidCommandMessage();
    }

    @Override
    public void saveMap(String p_fileName) {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent(Map<Integer, Integer> p_continentsToAdd, Set<Integer> p_continentsToRemove) {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountry(Map<Integer, Integer> p_countriesToAdd, Set<Integer> p_countriesToRemove) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbour(Map<Integer, Integer> p_neighboursToAdd, Map<Integer, Integer> p_neighboursToRemove) {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }
}