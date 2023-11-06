package com.fsociety.warzone.phase;

import com.fsociety.warzone.command.Command;

public abstract class Phase {

    protected Command[] d_validCommands;

    // Main Menu
    public abstract void playGame();
    public abstract void mapEditor();

    // General behaviour
    public abstract void showMap();
    public abstract void back();
    public abstract void help();

    // Startup commands
    public abstract void loadMap();
    public abstract void gamePlayer();
    public abstract void assignCountries();

    // Gameplay commands
    public abstract void deploy();
    public abstract void advance();
    public abstract void bomb();
    public abstract void blockade();
    public abstract void airlift();
    public abstract void negotiate();
    public abstract void commit();

    // Map Editor commands
    public abstract void editMap();
    public abstract void saveMap();
    public abstract void editContinent();
    public abstract void editCountry();
    public abstract void editNeighbour();
    public abstract void validateMap();

    // Common commands
    public abstract void exit();
    public abstract void printInvalidCommandMessage();

    /**
     *  This method formats the valid commands for a given phase in order to be printed as part of the help message.
     */
    protected String getValidCommands() {
        String l_validCommands = "";
        for (Command l_command : d_validCommands) {
            l_validCommands += "\t" + l_command.getCommand() + " " + l_command.getHelpMessage() + "\n";
        }
        return l_validCommands;
    }

}
