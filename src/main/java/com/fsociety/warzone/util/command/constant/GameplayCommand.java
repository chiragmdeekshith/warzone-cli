package com.fsociety.warzone.util.command.constant;

public enum GameplayCommand {

    BACK("back"),
    DEPLOY("deploy"),
    SHOW_MAP("showmap");


    private final String d_command;

    GameplayCommand(String p_command){
        this.d_command = p_command;
    }

    public String getCommand() {
        return d_command;
    }

}
