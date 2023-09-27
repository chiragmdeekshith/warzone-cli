package com.fsociety.warzone.util.command.constant;

public enum StartupCommand {
    BACK("back"),
    GAME_PLAYER("gameplayer"),
    ASSIGN_COUNTRIES("assigncountries"),
    SHOW_MAP("showmap"),
    LOAD_MAP("loadmap");

    // Arguments for commands
    public static final String ADD = "-add";
    public static final String REMOVE = "-remove";

    private final String d_command;

    StartupCommand(String p_command){
        this.d_command = p_command;
    }

    public String getCommand() {
        return d_command;
    }
}
