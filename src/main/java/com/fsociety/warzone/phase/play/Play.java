package com.fsociety.warzone.phase.play;

import com.fsociety.warzone.phase.Menu;

public abstract class Play extends Menu {


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
