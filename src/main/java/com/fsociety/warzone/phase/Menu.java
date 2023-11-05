package com.fsociety.warzone.phase;

public class Menu extends Phase{

    @Override
    public void help() {
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
    public void deploy() {
        printInvalidCommandMessage();
    }

    @Override
    public void advance() {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb() {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade() {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift() {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate() {
        printInvalidCommandMessage();
    }

    @Override
    public void commit() {
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
