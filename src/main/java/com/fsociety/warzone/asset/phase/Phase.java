package com.fsociety.warzone.asset.phase;

import com.fsociety.warzone.asset.command.Command;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This abstract Class contains the list of possible methods called on the Phase objects.
 */
public abstract class Phase {

    // Main Menu
    /**
     * This method launches a new game.
     */
    public abstract void playGame();

    /**
     * This method launches the map editor.
     */
    public abstract void mapEditor();

    /**
     * This method launches a tournament.
     * @param p_numberOfGames the number of games
     * @param p_maxNumberOfTurns the max number of turns
     * @param p_botPlayers the list of player strategy types
     * @param p_maps the list of map file names
     */
    public abstract void tournamentMode(int p_numberOfGames, int p_maxNumberOfTurns, ArrayList<String> p_botPlayers, ArrayList<String> p_maps);


    // General behaviour
    /**
     * This method prints the map to the console.
     */
    public abstract void showMap();

    /**
     * This method returns to the main menu at any point during execution (or terminates the program if already in the
     * main menu).
     */
    public abstract void back();

    /**
     * This method compiles and prints a help message of valid commands for the given phase.
     */
    public abstract void help();



    // Startup commands

    /**
     * This method loads a file's contents into an PlayMap object.
     * @param p_fileName the name of the file to be loaded
     */
    public abstract void loadMap(String p_fileName);

    /**
     * This method adds and removes players from the list of players.
     * @param p_gamePlayersToAdd the players to be added
     * @param p_gamePlayersToRemove the players to be removed
     */
    public abstract void gamePlayer(Map<String, String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove);

    /**
     * This method assigns countries to players and starts a new game.
     */
    public abstract void assignCountries();


    // Gameplay commands

    /**
     * This method creates a Deploy order.
     * @param p_countryId the country to be deployed to
     * @param p_troopsCount the troops to be deployed
     */
    public abstract void deploy(int p_countryId, int p_troopsCount);

    /**
     * This method creates an Advance order.
     * @param p_sourceCountryId the source country
     * @param p_targetCountryId the target country
     * @param p_troopsCount the number of troops to be moved
     */
    public abstract void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount);

    /**
     * This method creates a Bomb order.
     * @param p_targetCountryId the country to be bombed
     */
    public abstract void bomb(int p_targetCountryId);

    /**
     * This method creates a Blockade order.
     * @param p_countryId the country to be blockaded
     */
    public abstract void blockade(int p_countryId);

    /**
     * This method creates an Airlift order.
     * @param p_sourceCountryId the source country
     * @param p_targetCountryId the target country
     * @param p_troopsCount the number of troops to be moved
     */
    public abstract void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount);

    /**
     * This method creates a Diplomacy order.
     * @param p_targetPlayer the name of the player to be negotiated with
     */
    public abstract void negotiate(String p_targetPlayer);

    /**
     * This method allows a player to commit their orders.
     */
    public abstract void commit();

    /**
     * This method prints a player's cards to the console.
     */
    public abstract void showCards();

    /**
     * This method prints a player's available armies to the console.
     */
    public abstract void showAvailableArmies();

    /**
     * This method prints the list of players to the console.
     */
    public abstract void showPlayers();


    // Map Editor commands

    /**
     * This method loads a file's contents into an EditMap object.
     * @param p_fileName the name of the file to be loaded
     */
    public abstract void editMap(String p_fileName);

    /**
     * This method saves an EditMap's contents to a file.
     * @param p_fileName the name of the file to be saved to
     */
    public abstract void saveMap(String p_fileName);

    /**
     * This method saves a game's contents to a file.
     * @param p_fileName the name of the file to be saved to
     */
    public abstract void saveGame(String p_fileName);

    /**
     * This method loads a file's contents into an game object.
     * @param p_fileName the name of the file to be loaded
     */
    public abstract void loadGame(String p_fileName);

    /**
     * This method adds and removes continents from the map.
     * @param p_continentsToAdd the continents to add
     * @param p_continentsToRemove the continents to remove
     */
    public abstract void editContinent(Map<Integer, Integer> p_continentsToAdd, Set<Integer> p_continentsToRemove);

    /**
     * This method adds and removes countries from the map.
     * @param p_countriesToAdd the countries to add
     * @param p_countriesToRemove the countries to remove
     */
    public abstract void editCountry(Map<Integer, Integer> p_countriesToAdd, Set<Integer> p_countriesToRemove);

    /**
     * This method adds and removes neighbours from a country.
     * @param p_neighboursToAdd neighbours to be added
     * @param p_neighboursToRemove neighbours to be removed
     */
    public abstract void editNeighbour(Map<Integer, Integer> p_neighboursToAdd, Map<Integer, Integer> p_neighboursToRemove);

    /**
     * This method validates the map.
     */
    public abstract void validateMap();


    // Common commands

    /**
     * This method terminates execution of the program.
     */
    public abstract void exit();

    /**
     * This method prints the invalid command message when an invalid command is entered.
     */
    public abstract void printInvalidCommandMessage();

    /**
     * This method formats the valid commands for a phase as a string.
     * @param p_commands the valid commands for a phase
     * @return the String representation of the list of commands
     */
    protected abstract String getValidCommands(Command[] p_commands);

}
