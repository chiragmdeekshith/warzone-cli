package com.fsociety.warzone.util.command.constant;

import java.util.*;

public enum GameplayCommand {

    BACK("back"),
    DEPLOY("deploy"),
    SHOW_MAP("showmap");


    private final String command;

    GameplayCommand(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
