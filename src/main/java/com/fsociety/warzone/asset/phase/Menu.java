package com.fsociety.warzone.asset.phase;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.edit.EditPreLoad;
import com.fsociety.warzone.asset.phase.play.playsetup.PlayPreLoad;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.Tournament;
import com.fsociety.warzone.model.GameSaveData;
import com.fsociety.warzone.util.GameSaveUtil;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This Class implements the commands common across the application.
 */
public class Menu extends Phase {

    /**
     * This method compiles and prints a help message of valid commands for the Menu phase when the 'help' command is
     * entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.PLAY_GAME, Command.LOAD_GAME,Command.TOURNAMENT, Command.MAP_EDITOR, Command.BACK,Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(help);
    }

    /**
     * This method starts a new game when the 'playgame' command is entered.
     */
    @Override
    public void playGame() {
        Console.print("Starting a new game.");
        GameEngine.setPhase(new PlayPreLoad());
    }

    /**
     * This method launches the map editor when the 'mapeditor' command is entered.
     */
    @Override
    public void mapEditor() {
        Console.print("Map Editor selected. Please start by loading a map. Type 'back' to go to the previous menu.");
        GameEngine.setPhase(new EditPreLoad());
    }

    @Override
    public void tournamentMode(int p_numberOfGames, int p_maxNumberOfTurns, ArrayList<String> p_botPlayers, ArrayList<String> p_maps) {
        Console.print("Beginning tournament...");
        new Tournament(p_numberOfGames, p_maxNumberOfTurns, p_botPlayers, p_maps).runTournament();
        Console.print("Tournament concluded. Returning to Main Menu.");
        GameEngine.setPhase(new Menu());
        Console.print("""
                                                          \s
                 _ _ _ _____ _____ _____ _____ _____ _____\s
                | | | |  _  | __  |__   |     |   | |   __|
                | | | |     |    -|   __|  |  | | | |   __|
                |_____|__|__|__|__|_____|_____|_|___|_____|
                                                          \s""");
    }

    /**
     * This method halts execution of the program when the 'exit' command is used.
     */
    @Override
    public void exit() {
        System.out.println("Exiting the game. Thank you for playing Warzone!");
        System.exit(0);
    }

    /**
     * This method halts execution of the program when the 'back' command is used in the main menu.
     */
    @Override
    public void back() {
        exit();
    }

    /**
     * This method prints the invalid command message when an invalid command is entered.
     */
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

    /**
     * This method prints out the invalid command message when the 'showmap' command is used in the main menu.
     */
    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'loadmap' command is used outside of the
     * PlayPreLoad phase.
     */
    @Override
    public void loadMap(String p_fileName) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'gameplayer' command is used outside of the
     * PlayPostLoad phase.
     */
    @Override
    public void gamePlayer(Map<String, String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'assigncountries' command is used outside of the
     * PlayPostLoad phase.
     */
    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'airlift' command is used outside of the
     * Reinforcement phase.
     */
    @Override
    public void deploy(int p_countryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'advance' command is used outside of the Attack
     * phase.
     */
    @Override
    public void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'bomb' command is used outside of the Attack
     * phase.
     */
    @Override
    public void bomb(int p_targetCountryId) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'blockade' command is used outside of the Attack
     * phase.
     */
    @Override
    public void blockade(int p_countryId) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'airlift' command is used outside of the Attack
     * phase.
     */
    @Override
    public void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'negotiate' command is used outside of the Attack
     * phase.
     */
    @Override
    public void negotiate(String p_targetPlayerId) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'commit' command is used outside of the Attack
     * phase.
     */
    @Override
    public void commit() {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'showcards' command is used outside of the MainPlay
     * phase.
     */
    @Override
    public void showCards() { printInvalidCommandMessage(); }

    /**
     * This method prints out the invalid command message when the 'showplayers' command is used outside of the Play
     * phase.
     */
    @Override
    public void showPlayers() { printInvalidCommandMessage(); }

    /**
     * This method prints out the invalid command message when the 'showtroops' command is used outside of the Attack
     * phase.
     */
    @Override
    public void showAvailableArmies() { printInvalidCommandMessage(); }

    /**
     * This method prints out the invalid command message when the 'editmap' command is used outside of the EditPreLoad
     * phase.
     */
    @Override
    public void editMap(String p_fileName) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'savemap' command is used outside of the
     * EditPostLoad phase.
     */
    @Override
    public void saveMap(String[] p_fileSaveData) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'editcontinent' command is used outside of the
     * EditPostLoad phase.
     */
    @Override
    public void editContinent(Map<Integer, Integer> p_continentsToAdd, Set<Integer> p_continentsToRemove) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'editcountry' command is used outside of the
     * EditPostLoad phase.
     */
    @Override
    public void editCountry(Map<Integer, Integer> p_countriesToAdd, Set<Integer> p_countriesToRemove) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'editneighbor' command is used outside of the
     * EditPostLoad phase.
     */
    @Override
    public void editNeighbour(Map<Integer, Integer> p_neighboursToAdd, Map<Integer, Integer> p_neighboursToRemove) {
        printInvalidCommandMessage();
    }

    /**
     * This method prints out the invalid command message when the 'validatemap' command is used outside of the
     * EditPostLoad phase.
     */
    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * This method loads a new game from a save file when the 'loadgame' command is entered.
     */
    @Override
    public void loadGame(String p_fileName) {
        GameSaveData l_gameSavedata = GameSaveUtil.loadGameFromFile(p_fileName);
        if(null == l_gameSavedata) {
            Console.print("Could not load the save file. Game file corrupted or does not exist.");
            return;
        }

        GameplayController.setCurrentTournament(l_gameSavedata.getCurrentTournament());
        GameplayController.setGameWonForLoad(l_gameSavedata.getGameWon());
        GameplayController.setTruces(l_gameSavedata.getTruces());
        GameplayController.setWinner(l_gameSavedata.getWinner());
        GameplayController.setPlayerIdMap(l_gameSavedata.getPlayerIdMap());
        GameplayController.setPlayerNameMap(l_gameSavedata.getPlayerNameMap());
        GameplayController.setPlayers(l_gameSavedata.getPlayers());
        GameplayController.setPlayMap(l_gameSavedata.getPlayMap());
        GameplayController.setTurns(l_gameSavedata.getTurns());
        GameplayController.setNeutralPlayer(l_gameSavedata.getNeutralPlayer());
        GameEngine.setPhase(l_gameSavedata.getCurrentPhase());

        //Resume the game
        GameplayController.gamePlayLoop(false);
    }

    /**
     * This method prints out the invalid command message when the 'savegame' command is used outside of the
     * MainPlay phase.
     */
    @Override
    public void saveGame(String p_fileName) {
        printInvalidCommandMessage();
    }
}
