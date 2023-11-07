package com.fsociety.warzone.utils.command.constant;

/**
 * This enum is to store all the valid commands in the Start-Up phase
 */
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

    /**
     * Constructor to assign the string
     * @param p_command - the command string
     */
    StartupCommand(String p_command){
        this.d_command = p_command;
    }

    /**
     * Get the command string
     * @return command string
     */
    public String getCommand() {
        return d_command;
    }
}
