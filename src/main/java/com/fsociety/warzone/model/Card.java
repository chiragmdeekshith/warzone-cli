package com.fsociety.warzone.model;

import com.fsociety.warzone.constant.Constants;

public enum Card {
    DEPLOY(Constants.DEPLOY_HELP_MESSAGE),
    ADVANCE(Constants.ADVANCE_HELP_MESSAGE),
    BOMB(Constants.BOMB_HELP_MESSAGE),
    BLOCKADE(Constants.BLOCKADE_HELP_MESSAGE),
    AIRLIFT(Constants.AIRLIFT_HELP_MESSAGE),
    NEGOTIATE(Constants.NEGOTIATE_HELP_MESSAGE);

    private final String helpMessage;

    private Card(final String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public String getHelpMessage() {
        return helpMessage;
    }
}
