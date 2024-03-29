package com.fsociety.warzone.asset.command;

import com.fsociety.warzone.view.Console;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * This class is used for validating commands received from the Command Processor.
 * It first ensures the command belongs to the list of valid ones, and then makes sure it has
 * the right number of arguments and the argument type for each one.
 */
public class CommandValidator {

    /**
     * A collection of valid commands from the enum. Initialised in the static block
     */
    private static final Map<String, Command> d_commands;

    /**
     * A set of all valid player strategies. This is used for validating the command arguments for GamePlayer command.
     */
    private static final Set<String> d_playerStrategies;

    private static final Set<String> d_tournamentOptions;

    static {
        d_commands = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::getCommand, Function.identity()));
        d_playerStrategies = Set.of(Command.AGGRESSIVE, Command.BENEVOLENT, Command.CHEATER, Command.RANDOM);
        d_tournamentOptions = Set.of(Command.MAPS_OPTION, Command.PLAYER_OPTION, Command.GAMES_OPTION,
                Command.TURNS_OPTION);
    }

    /**
     * This function checks the passed command and ensures that it is valid.
     *
     * @param p_splitCommand the parsed command array
     * @return boolean - true if the command is valid, false otherwise.
     */
    public static boolean validateCommand(String[] p_splitCommand) {
        String l_commandName = p_splitCommand[0];

        // If the command is not present in the list of valid commands, the command is not valid
        if(!d_commands.containsKey(l_commandName)) {
            Console.print("Unrecognized command.");
            return false;
        }

        switch (d_commands.get(l_commandName)) {
            case HELP, PLAY_GAME, MAP_EDITOR, BACK, EXIT, SHOW_MAP, VALIDATE_MAP,
                    ASSIGN_COUNTRIES, COMMIT, SHOW_CARDS, SHOW_AVAILABLE_ARMIES, SHOW_PLAYERS -> {
                return validateNoArgsCommand(p_splitCommand);
            }
            case EDIT_MAP, LOAD_MAP -> {
                return validateFilenameCommand(p_splitCommand, Command.MAP_FILE_EXTENSION);
            }
            case SAVE_MAP -> {
                if (p_splitCommand.length == 2) {
                    if(!p_splitCommand[1].endsWith(Command.MAP_FILE_EXTENSION)) {
                        Console.print("The file name passed is not a '"+Command.MAP_FILE_EXTENSION+"' file.");
                        return false;
                    } else {
                        return true;
                    }
                }
                if (p_splitCommand.length!=3) {
                    Console.print("Unexpected number of arguments for the save map command");
                    return false;
                }
                switch (p_splitCommand[2]) {
                    case Command.MAP_OPTION_DOMINATION, Command.MAP_OPTION_CONQUEST -> {
                        return true;
                    }
                    default -> {
                        Console.print("Unrecognized file option");
                        return false;
                    }
                }
            }
            case LOAD_GAME, SAVE_GAME -> {
                return validateFilenameCommand(p_splitCommand, Command.SAVE_FILE_EXTENSION);
            }
            case EDIT_CONTINENT -> {
                if(p_splitCommand.length < 3) {
                    Console.print("Unexpected number of arguments passed.");
                    return false;
                }
                int l_i = 1;
                while(l_i < p_splitCommand.length) {
                    String l_operation = p_splitCommand[l_i++];
                    switch (l_operation) {
                        case Command.ADD -> {
                            if(l_i + 1 >= p_splitCommand.length) {
                                Console.print("'-add' requires two arguments.");
                                return false;
                            }
                            int l_continentId, l_continentValue;
                            try {
                                l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                                l_continentValue = Integer.parseInt(p_splitCommand[l_i++]);
                            } catch (NumberFormatException e) {
                                Console.print("Invalid argument type passed: Integers required.");
                                return false;
                            }
                            if(l_continentId < 1 || l_continentValue < 1) {
                                Console.print("Continent ID or bonus value cannot be less than 1");
                                return false;
                            }
                        }
                        case Command.REMOVE -> {
                            if(l_i >= p_splitCommand.length) {
                                System.out.println("'-remove' requires one argument.");
                                return false;
                            }
                            int l_continentId;
                            try {
                                l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                            } catch (NumberFormatException e) {
                                Console.print("Invalid argument type passed: Integer required.");
                                return false;
                            }
                            if(l_continentId < 0) {
                                Console.print("Continent ID cannot be less than 0.");
                                return false;
                            }
                        }
                        default -> {
                            Console.print("Incorrect ordering of command options and values at index " + l_i + ".");
                            return false;
                        }
                    }
                }
            }
            case EDIT_COUNTRY -> {
                if(p_splitCommand.length < 3){
                    System.out.println("This command requires at least 2 arguments.");
                    return false;
                }

                int l_i = 1;

                while(l_i < p_splitCommand.length) {

                    String l_operation = p_splitCommand[l_i++];
                    switch (l_operation) {
                        case Command.ADD -> {
                            if(l_i + 1 >= p_splitCommand.length) {
                                Console.print("'-add' option requires two arguments.");
                                return false;
                            }
                            int l_countryId, l_continentId;
                            try {
                                l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                                l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                            } catch (NumberFormatException e) {
                                Console.print("Integers are expected for countryId and continentId.");
                                return false;
                            }
                            if(l_countryId < 0 || l_continentId < 0) {
                                Console.print("IDs cannot be less than 0.");
                                return false;
                            }
                        }
                        case Command.REMOVE -> {
                            if(l_i >= p_splitCommand.length) {
                                Console.print("'-remove' option requires one argument.");
                                return false;
                            }
                            int l_countryId;
                            try {
                                l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                            } catch (NumberFormatException e) {
                                Console.print("Integer is expected for countryId.");
                                return false;
                            }
                            if(l_countryId < 0) {
                                Console.print("ID cannot be less than 0.");
                                return false;
                            }
                        }
                        default -> {
                            Console.print("Command arguments and values are not in order at index " + l_i + ".");
                            return false;
                        }
                    }
                }
                return true;
            }
            case EDIT_NEIGHBOUR -> {
                if(p_splitCommand.length < 3) {
                    Console.print("This command needs at least two arguments.");
                    return false;
                }
                int l_i = 1;
                while(l_i < p_splitCommand.length) {
                    String l_operation = p_splitCommand[l_i++];
                    int l_countryId, l_neighbourId;
                    switch (l_operation) {
                        case Command.ADD -> {
                            if(l_i + 1 >= p_splitCommand.length) {
                                Console.print("'-add' option requires two arguments.");
                                return false;
                            }
                            try {
                                l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                                l_neighbourId = Integer.parseInt(p_splitCommand[l_i++]);
                            } catch (NumberFormatException e) {
                                Console.print("Value of ID must be an integer.");
                                return false;
                            }
                            if(l_countryId < 1 || l_neighbourId < 1) {
                                Console.print("Value of countryID and neighbourID must not be lesser than 1.");
                                return false;
                            }
                            if(l_countryId == l_neighbourId) {
                                Console.print("A country cannot be its own neighbour.");
                                return false;
                            }
                        }
                        case Command.REMOVE -> {
                            if(l_i >= p_splitCommand.length) {
                                Console.print("'-remove' option requires one argument.");
                                return false;
                            }
                            try {
                                l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                                l_neighbourId = Integer.parseInt(p_splitCommand[l_i++]);
                            } catch (NumberFormatException e) {
                                Console.print("Value of ID must be an integer.");
                                return false;
                            }
                            if(l_countryId < 1 ||  l_neighbourId < 1) {
                                Console.print("Value of countryID and neighbourID must not be lesser than 1.");
                                return false;
                            }
                        }
                        default -> {
                            Console.print("Unexpected option '" + l_operation + "'.");
                            return false;
                        }
                    }
                }
            }
            case TOURNAMENT -> {
                int l_i = 1;
                try {
                    for (int i = 0; i < d_tournamentOptions.size(); i++) {
                        switch (p_splitCommand[l_i]) {
                            case Command.MAPS_OPTION -> {
                                l_i++;
                                int l_mapsCount = 0;
                                while (p_splitCommand[l_i].endsWith(".map")) {
                                    l_mapsCount++;
                                    l_i++;
                                    if (l_i > p_splitCommand.length-1) {
                                        break;
                                    }
                                }
                                if (l_mapsCount > 5 || l_mapsCount < 1) {
                                    Console.print("Please enter between 1 and 5 map file names.");
                                    return false;
                                }
                            }
                            case Command.PLAYER_OPTION -> {
                                l_i++;
                                int l_playerCount = 0;
                                while (d_playerStrategies.contains(p_splitCommand[l_i])) {
                                    l_playerCount++;
                                    l_i++;
                                    if (l_i > p_splitCommand.length-1) {
                                        break;
                                    }
                                }
                                if (l_playerCount > 4 || l_playerCount < 2) {
                                    Console.print("Please enter between 2 and 4 player strategies.");
                                    return false;
                                }
                            }
                            case Command.GAMES_OPTION -> {
                                l_i++;
                                int l_games = 0;
                                try {
                                    l_games = Integer.parseInt(p_splitCommand[l_i++]);
                                } catch (NumberFormatException e) {
                                    System.out.println("Argument passed for number of games must be an integer.");
                                    return false;
                                }
                                if (l_games < 1 || l_games > 5) {
                                    Console.print("Please enter between 1 and 5 games to be played.");
                                    return false;
                                }
                            }
                            case Command.TURNS_OPTION -> {
                                l_i++;
                                int l_turns = 0;
                                try {
                                    l_turns = Integer.parseInt(p_splitCommand[l_i++]);
                                } catch (NumberFormatException e) {
                                    System.out.println("Argument passed for maximum number of turns must be an integer.");
                                    return false;
                                }
                                if (l_turns < 10 || l_turns > 50) {
                                    Console.print("Please enter between 10 and 50 for the maximum number of turns.");
                                    return false;
                                }
                            }
                            default -> {
                                Console.print("Unexpected argument. Please use 'help' to see the correct formatting " +
                                        "for the 'tournament' command.");
                                return false;
                            }
                        }
                    }
                    if (l_i != p_splitCommand.length) {
                        Console.print("Unexpected argument Please use 'help' to see the correct formatting for the" +
                                " 'tournament' command.");
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    Console.print("Missing arguments. Please use 'help' to see the correct formatting for the" +
                            " 'tournament' command.");
                    return false;
                }
                return true;
            }
            case GAME_PLAYER -> {
                // Ensure that there are arguments
                if(p_splitCommand.length == 1 ) {
                    Console.print("This command requires pairs of add/remove options and player names.");
                    return false;
                }

                String l_argument = p_splitCommand[1];
                int l_i = 1;
                // Check if an incorrect argument is being passed.
                if(!Command.ADD.equals(l_argument) && !Command.REMOVE.equals(l_argument)) {
                    if (!d_playerStrategies.contains(l_argument)) {
                        Console.print("The argument " + l_argument + " is not recognized");
                        return false;
                    }
                    l_i = 2;
                }
                // Ensure that the remaining add or remove operations are paired with names.
                if((p_splitCommand.length - l_i) % 2 != 0) {
                    Console.print("The arguments are correct. Please check and try again.");
                    return false;
                }

                // Loop through all -add and -remove operations in the command
                for (; l_i < p_splitCommand.length - 1; l_i += 2) {
                    l_argument = p_splitCommand[l_i];
                    if (!Command.ADD.equals(l_argument) && !Command.REMOVE.equals(l_argument)) {
                        Console.print("Unrecognised operation " + l_argument + " at index " + l_i + ".");
                        return false;
                    }
                    String l_playerName = p_splitCommand[l_i + 1];
                    if (!l_playerName.matches("[A-Za-z0-9]+")) {
                        Console.print("Player name must be alphanumeric.");
                        return false;
                    }
                }

                return true;
            }
            case DEPLOY -> {
                if(p_splitCommand.length != 3) {
                    Console.print("This command does not have required 2 arguments.");
                    return false;
                }
                int l_countryId, l_armies;
                try {
                    l_countryId = Integer.parseInt(p_splitCommand[1]);
                    l_armies = Integer.parseInt(p_splitCommand[2]);
                } catch(NumberFormatException e) {
                    Console.print("This command is not valid because the second or third argument is not an integer.");
                    return false;
                }
                if(l_countryId < 1 || l_armies < 1) {
                    Console.print("The Country ID and the Armies have to be greater than 0.");
                    return false;
                }
                return true;
            }
            case ADVANCE, AIRLIFT -> {
                if(p_splitCommand.length != 4) {
                    Console.print("This command does not have required 3 arguments.");
                    return false;
                }
                int l_fromCountryId, l_toCountryId, l_armies;
                try {
                    l_fromCountryId = Integer.parseInt(p_splitCommand[1]);
                    l_toCountryId = Integer.parseInt(p_splitCommand[2]);
                    l_armies = Integer.parseInt(p_splitCommand[3]);
                } catch(NumberFormatException e) {
                    Console.print("This command is not valid because the second or third argument is not an integer.");
                    return false;
                }
                if(l_fromCountryId < 1 || l_toCountryId < 1 || l_armies < 1) {
                    Console.print("The Country IDs and the Armies have to be greater than 0");
                    return false;
                }
                return true;
            }
            case BOMB, BLOCKADE -> {
                if(p_splitCommand.length != 2) {
                    Console.print("This command does not have required 1 argument.");
                    return false;
                }
                int l_countryId;
                try {
                    l_countryId = Integer.parseInt(p_splitCommand[1]);
                } catch(NumberFormatException e) {
                    Console.print("Country ID must be an integer.");
                    return false;
                }
                if(l_countryId < 1) {
                    Console.print("The Country ID has to be greater than 0");
                    return false;
                }
                return true;
            }
            case NEGOTIATE -> {
                if(p_splitCommand.length != 2) {
                    Console.print("This command does not have required 1 argument.");
                    return false;
                }
                String l_playerName = p_splitCommand[1];
                if(!l_playerName.matches("[A-Za-z0-9]+")) {
                    Console.print("Player name must be alphanumeric.");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * A common command for checking commands having no arguments
     *
     * @param p_splitCommand the parsed command array
     * @return boolean - true if the command has no arguments, false otherwise.
     */
    static boolean validateNoArgsCommand(String[] p_splitCommand) {
        if(p_splitCommand.length != 1) {
            Console.print("This command has too many arguments, so its invalid.");
            return false;
        }
        return true;
    }

    /**
     * A common function for checking commands related to filename.
     *
     * @param p_splitCommand the parsed command array
     * @param p_fileType the type of file that needs to be checked
     * @return boolean - true if the command has 1 argument filename ending with the fileType passed, false otherwise.
     */
    static boolean validateFilenameCommand(String[] p_splitCommand, String p_fileType) {
        if(p_splitCommand.length != 2){
            Console.print("Unexpected number of arguments. Expected one argument: filename.");
            return false;
        }
        String l_fileName = p_splitCommand[1];
        if(!l_fileName.endsWith(p_fileType)) {
            Console.print("The file passed is not a '" + p_fileType + "' file.");
            return false;
        }
        return true;
    }
}
