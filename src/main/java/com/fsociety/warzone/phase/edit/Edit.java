package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.phase.Menu;

public abstract class Edit extends Menu {

    @Override
    public void back() {

    }

    @Override
    public void help() {

    }

    // Invalidate commands

    @Override
    public void playGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void mapEditor() {
        printInvalidCommandMessage();
    }
}
