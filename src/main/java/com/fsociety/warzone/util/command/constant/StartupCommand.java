package com.fsociety.warzone.util.command.constant;

public enum StartupCommand {
    BACK("back"),
    GAME_PLAYER("gameplayer"),
    ASSIGN_COUNTRIES("assigncountries"),
    SHOW_MAP("showmap"),
    LOAD_MAP("loadmap");

    // Arguments for the command
    // Arguments for commands
    public static final String ADD = "-add";
    public static final String REMOVE = "-remove";

    private final String command;

    StartupCommand(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
