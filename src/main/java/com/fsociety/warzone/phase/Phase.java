package com.fsociety.warzone.phase;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.model.Player;

import java.util.Map;
import java.util.Set;

public abstract class Phase {

    // Main Menu
    public abstract void playGame();
    public abstract void mapEditor();

    // General behaviour
    public abstract void showMap();
    public abstract void back();
    public abstract void help();

    // Startup commands
    public abstract void loadMap(String p_fileName);
    public abstract void gamePlayer();
    public abstract void assignCountries();

    // Gameplay commands
    public abstract void deploy(int p_countryId, int p_troopsCount);
    public abstract void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount);
    public abstract void bomb(int p_targetCountryId);
    public abstract void blockade(int p_countryId);
    public abstract void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount);
    public abstract void negotiate(int p_targetPlayerId);
    public abstract void commit();

    // Map Editor commands
    public abstract void editMap(String p_fileName);
    public abstract void saveMap(String p_fileName);
    public abstract void editContinent(Map<Integer, Integer> p_continentsToAdd, Set<Integer> p_continentsToRemove);
    public abstract void editCountry(Map<Integer, Integer> p_countriesToAdd, Set<Integer> p_countriesToRemove);
    public abstract void editNeighbour(Map<Integer, Integer> p_neighboursToAdd, Map<Integer, Integer> p_neighboursToRemove);
    public abstract void validateMap();

    // Common commands
    public abstract void exit();
    public abstract void printInvalidCommandMessage();
    protected abstract String getValidCommands(Command[] p_commands);

}
