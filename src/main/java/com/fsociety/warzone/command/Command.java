package com.fsociety.warzone.command;

public enum Command {

    BACK("back", "s;rgnsrjlef"),
    DEPLOY("deploy", "s;ejfnsnedf"),
    SHOW_MAP("showmap", "soeifjesjf"),
    EDIT_MAP("editmap", "soeifjesjf"),
    EDIT_CONTINENT("editcontinent", "soeifjesjf"),
    EDIT_COUNTRY("editcountry", "soeifjesjf"),
    EDIT_NEIGHBOUR("editneighbor", "soeifjesjf"),
    VALIDATE_MAP("validatemap", "soeifjesjf"),
    SAVE_MAP("savemap", "soeifjesjf"),
    GAME_PLAYER("gameplayer", "soeifjesjf"),
    ASSIGN_COUNTRIES("assigncountries", "soeifjesjf"),
    LOAD_MAP("loadmap", "soeifjesjf"),
    PLAY_GAME("playgame", "soeifjesjf"),
    MAP_EDITOR("mapeditor", "soeifjesjf"),
    ADVANCE("advance", "soeifjesjf"),
    COMMIT("commit", "soeifjesjf"),
    BOMB("bomb", "soeifjesjf"),
    AIRLIFT("airlift", "soeifjesjf"),
    NEGOTIATE("negotiate", "soeifjesjf"),
    BLOCKADE("blockade", "soeifjesjf");


    // Arguments for commands
    public static final String ADD = "-add";
    public static final String REMOVE = "-remove";


    private final String d_command;
    private final String d_helpMessage;

    /**
     * Constructor to assign the string
     * @param p_command - the command string
     * @param p_helpMessage - the help message
     */
    Command(String p_command, String p_helpMessage){
        this.d_command = p_command;
        this.d_helpMessage = p_helpMessage;
    }

    /**
     * Get the command string
     * @return command string
     */
    public String getCommand() {
        return d_command;
    }

    /**
     * Get the help message string
     * @return help message string
     */
    public String getHelpMessage() {
        return d_helpMessage;
    }

}
