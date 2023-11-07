package com.fsociety.warzone.utils.command.constant;

/**
 * This enum is to store all the valid commands in the Gameplay phase
 */
public enum GameplayCommand {

    BACK("back"),
    DEPLOY("deploy"),
    SHOW_MAP("showmap");


    private final String d_command;

    /**
     * Constructor to assign the string
     * @param p_command - the command string
     */
    GameplayCommand(String p_command){
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
