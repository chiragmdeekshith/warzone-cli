package com.fsociety.warzone.asset.command;

/**
 * This enum holds all possible commands that can be used in the application.
 */
public enum Command {

    /**
     * The help command which gives the user a way to play the game
     */
    HELP("help", ""),
    /**
     * The command used to start the game
     */
    PLAY_GAME("playgame", ""),
    /**
     * The command used to use the map editor
     */
    MAP_EDITOR("mapeditor", ""),
    /**
     * The command used to begin a tournament
     */
    TOURNAMENT("tournament", ""),
    /**
     * Go to the previous menu
     */
    BACK("back", ""),
    /**
     * Stop the program
     */
    EXIT("exit", ""),
    /**
     * Show the current map
     */
    SHOW_MAP("showmap", ""),
    /**
     * Add or Remove continents
     */
    EDIT_CONTINENT("editcontinent", "-add [continentID bonusValue] / -remove [continentID]"),
    /**
     * Add or Remove countries
     */
    EDIT_COUNTRY("editcountry", "-add [countryID continentID] / -remove [countryID]"),
    /**
     * Add or Remove neighbours
     */
    EDIT_NEIGHBOUR("editneighbor", "-add [countryID neighborCountryID] / -remove [countryID neighborCountryID]"),
    /**
     * Validate the current map
     */
    VALIDATE_MAP("validatemap", ""),
    /**
     * Load or create a map file for editing
     */
    EDIT_MAP("editmap", "[filename]"),
    /**
     * Save a map file from current map
     */
    SAVE_MAP("savemap", "[filename]"),
    /**
     * Load a map file for playing
     */
    LOAD_MAP("loadmap", "[filename]"),
    /**
     * Save a map file from current map
     */
    SAVE_GAME("savegame", "[filename]"),
    /**
     * Load a map file for playing
     */
    LOAD_GAME("loadgame", "[filename]"),
    /**
     * Add or Remove game players
     */
    GAME_PLAYER("gameplayer", "-aggressive / -benevolent / -cheater / -random / <choose no argument for a Human player> \n -add [name] / -remove [name]"),
    /**
     * Assign countries to Players
     */
    ASSIGN_COUNTRIES("assigncountries", ""),
    /**
     * Deploy troops as reinforcements
     */
    DEPLOY("deploy", "[countryID numArmies]"),
    /**
     * Move troops
     */
    ADVANCE("advance", "[sourceCountryID targetCountryID numArmies]"),
    /**
     * Bomb enemies
     */
    BOMB("bomb", "[targetCountryID]"),
    /**
     * Move troops of any other country
     */
    AIRLIFT("airlift", "[sourceCountryID targetCountryID numArmies]"),
    /**
     * Call a truce / diplomacy with an enemy for a round
     */
    NEGOTIATE("negotiate", "[playerID]"),
    /**
     * Triple troops and become a neutral country
     */
    BLOCKADE("blockade", "[countryID]"),
    /**
     * Indicate the end of player input
     */
    COMMIT("commit", ""),
    /**
     * Show a list of available cards for the current player
     */
    SHOW_CARDS("showcards", ""),
    /**
     * Show the available troops for advancement for the current player
     */
    SHOW_AVAILABLE_ARMIES("showtroops", ""),
    /**
     * Show a list of players in the game
     */
    SHOW_PLAYERS("showplayers", "");


    // Arguments for commands
    /**
     * The "add" operation used for arguments
     */
    public static final String ADD = "-add";
    /**
     * The "remove" operation used for arguments
     */
    public static final String REMOVE = "-remove";

    /**
     * The "AGGRESSIVE" operation used for game player bots
     */
    public static final String AGGRESSIVE = "-aggressive";
    /**
     * The "BENEVOLENT" operation used for game player bots
     */
    public static final String BENEVOLENT = "-benevolent";
    /**
     * The "CHEATER" operation used for game player bots
     */
    public static final String CHEATER = "-cheater";
    /**
     * The "RANDOM" operation used for game player bots
     */
    public static final String RANDOM = "-random";

    /**
     * The "HUMAN" operation used for the default player in the game.
     */
    public static final String HUMAN = "-human";

    /**
     * The map option for the tournament command
     */
    public static final String MAPS_OPTION = "-M";
    /**
     * The player option for the tournament command
     */
    public static final String PLAYER_OPTION = "-P";
    /**
     * The games option for the tournament command
     */
    public static final String GAMES_OPTION = "-G";
    /**
     * The turns option for the tournament command
     */
    public static final String TURNS_OPTION = "-D";

    /**
     * The command string
     */
    private final String d_command;

    /**
     * The help message for the command string
     */
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
