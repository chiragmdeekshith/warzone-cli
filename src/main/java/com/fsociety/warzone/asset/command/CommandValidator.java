package com.fsociety.warzone.asset.command;

import com.fsociety.warzone.view.Console;

import java.util.HashMap;
import java.util.Map;

public class CommandValidator {

    private static final Map<String, Command> d_commands;

    static {
        d_commands = new HashMap<>();
        for (Command l_command : Command.values()) {
            d_commands.put(l_command.getCommand(), l_command);
        }
    }
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
            case EDIT_MAP, SAVE_MAP, LOAD_MAP -> {
                return validateFilenameCommand(p_splitCommand);
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
            case GAME_PLAYER -> {
                if(p_splitCommand.length == 1 || p_splitCommand.length % 2 == 0) {
                    Console.print("This command requires pairs of add/remove options and player names.");
                    return false;
                }

                String l_operation = p_splitCommand[1];

                // Loop through all -add and -remove operations in the command
                for(int l_i = 1; l_i < p_splitCommand.length; l_i++) {
                    if(l_i % 2 == 1) {
                        if(!Command.ADD.equals(l_operation) && !Command.REMOVE.equals(l_operation)) {
                            Console.print("Unrecognised operation " + l_operation + " at index " + l_i + ".");
                            return false;
                        }
                    }
                    if(l_i % 2 == 0) {
                        String l_playerName = p_splitCommand[l_i];
                        if(!l_playerName.matches("[A-Za-z0-9]+")) {
                            Console.print("Player name must be alphanumeric.");
                            return false;
                        }
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

    private static boolean validateNoArgsCommand(String[] p_splitCommand) {
        if(p_splitCommand.length != 1) {
            Console.print("This command has too many arguments, so its invalid.");
            return false;
        }
        return true;
    }

    private static boolean validateFilenameCommand(String[] p_splitCommand) {
        if(p_splitCommand.length != 2){
            Console.print("Unexpected number of arguments. Expected one argument: filename.");
            return false;
        }
        String l_fileName = p_splitCommand[1];
        if(!l_fileName.endsWith(".map")) {
            Console.print("The file passed is not a '.map' file.");
            return false;
        }
        return true;
    }
}
