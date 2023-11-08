package com.fsociety.warzone.command;

public enum Command {

    HELP("help", ""),
    PLAY_GAME("playgame", ""),
    MAP_EDITOR("mapeditor", ""),
    BACK("back", ""),
    EXIT("exit", ""),
    SHOW_MAP("showmap", ""),
    EDIT_CONTINENT("editcontinent", "-add [continentID bonusValue] / -remove [continentID]"),
    EDIT_COUNTRY("editcountry", "-add [countryID continentID] / -remove [countryID]"),
    EDIT_NEIGHBOUR("editneighbor", "-add [countryID neighborCountryID] / -remove [countryID neighborCountryID]"),
    VALIDATE_MAP("validatemap", ""),
    EDIT_MAP("editmap", "[filename]"),
    SAVE_MAP("savemap", "[filename]"),
    LOAD_MAP("loadmap", "[filename]"),
    GAME_PLAYER("gameplayer", "-add [name] / -remove [name]"),
    ASSIGN_COUNTRIES("assigncountries", ""),
    DEPLOY("deploy", "[countryID numArmies]"),
    ADVANCE("advance", "[sourceCountryID targetCountryID numArmies]"),
    BOMB("bomb", "[targetCountryID]"),
    AIRLIFT("airlift", "[sourceCountryID targetCountryID numArmies]"),
    NEGOTIATE("negotiate", "[playerID]"),
    BLOCKADE("blockade", "[countryID]"),
    COMMIT("commit", ""),
    SHOW_CARDS("showcards", ""),
    SHOW_AVAILABLE_ARMIES("showtroops", ""),
    SHOW_PLAYERS("showplayers", "");


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
