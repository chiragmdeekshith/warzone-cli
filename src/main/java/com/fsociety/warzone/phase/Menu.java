package com.fsociety.warzone.phase;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;

public class Menu extends Phase {

    Command[] d_validCommands = {Command.PLAY_GAME, Command.MAP_EDITOR, Command.EXIT, Command.BACK};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: " +
                getValidCommands();
        Console.print(help);
    }
    @Override
    public void playGame() {
    }

    @Override
    public void mapEditor() {
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
        System.out.println("Invalid command in phase "
                + this.getClass().getSimpleName()
                + ". Please use the 'help' command to print a list of valid commands.");
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void gamePlayer() {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    @Override
    public void deploy(Player p_player, int p_countryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    @Override
    public void advance(Player l_issuer, int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb(Player l_issuer, int p_targetCountryId) {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade(Player p_issuer, int p_countryId) {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift(Player p_issuer, int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate(Player p_issuer, int p_targetPlayerId) {
        printInvalidCommandMessage();
    }

    @Override
    public void commit(Player p_issuer) {
        printInvalidCommandMessage();
    }

    @Override
    public void editMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void saveMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent() {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountry() {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbour() {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }
}
