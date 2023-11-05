package com.fsociety.warzone.phase.end;

import com.fsociety.warzone.phase.Menu;

public class End extends Menu {

    @Override
    public void showMap() {

    }

    @Override
    public void back() {

    }

    @Override
    public void help() {

    }

    // Invalid commands

    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }

}
