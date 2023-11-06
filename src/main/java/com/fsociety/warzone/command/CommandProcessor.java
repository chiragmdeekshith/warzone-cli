package com.fsociety.warzone.command;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.phase.Phase;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.*;

public class CommandProcessor {

    private static final Map<String, Command> d_commands;

    static {
        d_commands = new HashMap<>();
        for (Command l_command : Command.values()) {
            d_commands.put(l_command.getCommand(), l_command);
        }
    }

    public static void processCommand(String p_rawCommand) {
        if(p_rawCommand.isEmpty()) {
            Console.print("Command is empty. It cannot be empty.");
            return;
        }
        String[] l_splitCommand = p_rawCommand.split(" ");
        if(!validateCommand(l_splitCommand)) {
            return;
        }
        executeCommand(l_splitCommand);
    }

    private static boolean validateCommand(String[] p_splitCommand) {
        String l_commandName = p_splitCommand[0];

        // If the command is not present in the list of valid commands, the command is not valid
        if(!d_commands.containsKey(l_commandName)) {
            return false;
        }

        switch (d_commands.get(l_commandName)) {
            case PLAY_GAME, MAP_EDITOR, BACK, EXIT, SHOW_MAP, VALIDATE_MAP,
                    ASSIGN_COUNTRIES, COMMIT -> {
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
                            if(l_continentId < 0 || l_continentValue < 0) {
                                Console.print("Continent ID or bonus value cannot be less than 0");
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
                                Console.print("Continent ID or bonus value cannot be less than 0.");
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
                            if(l_countryId < 0 || l_neighbourId < 0) {
                                Console.print("Value of countryID and neighbourID must not be lesser than 0.");
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
                            if(l_countryId < 0 ||  l_neighbourId < 0) {
                                Console.print("Value of countryID and neighbourID must not be lesser than 0.");
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
                        if(!StartupCommand.ADD.equals(l_operation) && !StartupCommand.REMOVE.equals(l_operation)) {
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
                int countryId, armies;
                try {
                    countryId = Integer.parseInt(p_splitCommand[1]);
                    armies = Integer.parseInt(p_splitCommand[2]);
                } catch(NumberFormatException e) {
                    Console.print("This command is not valid because the second or third argument is not an integer.");
                    return false;
                }
                if(countryId < 1 && armies < 1) {
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
                int fromCountryId, toCountryId, armies;
                try {
                    fromCountryId = Integer.parseInt(p_splitCommand[1]);
                    toCountryId = Integer.parseInt(p_splitCommand[2]);
                    armies = Integer.parseInt(p_splitCommand[3]);
                } catch(NumberFormatException e) {
                    Console.print("This command is not valid because the second or third argument is not an integer.");
                    return false;
                }
                if(fromCountryId < 1 && toCountryId < 1 && armies < 1) {
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
                int countryId;
                try {
                    countryId = Integer.parseInt(p_splitCommand[1]);
                } catch(NumberFormatException e) {
                    Console.print("Country ID must be an integer.");
                    return false;
                }
                if(countryId < 1) {
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

    private static void executeCommand(String[] p_splitCommand) {
        Phase l_phase = GameRunner.getPhase();
        String l_commandName = p_splitCommand[0];
        switch (d_commands.get(l_commandName)) {
            case PLAY_GAME -> l_phase.playGame();
            case MAP_EDITOR -> l_phase.mapEditor();
            case BACK -> l_phase.back();
            case EXIT -> l_phase.exit();
            case SHOW_MAP -> l_phase.showMap();
            case EDIT_CONTINENT -> {
                Map<Integer, Integer> l_continentsToAdd = new HashMap<>();
                Set<Integer> l_continentsToRemove = new HashSet<>();

                int l_i = 1;
                while(l_i < p_splitCommand.length) {
                    String l_operation = p_splitCommand[l_i++];
                    int l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentBonusValue = Integer.parseInt(p_splitCommand[l_i++]);
                            l_continentsToAdd.put(l_continentId, l_continentBonusValue);
                        }
                        case MapEditorCommand.REMOVE -> l_continentsToRemove.add(l_continentId);
                    }
                }

                l_phase.editContinent(l_continentsToAdd, l_continentsToRemove);
            }
            case EDIT_COUNTRY -> {
                Map<Integer, Integer> l_countriesToAdd = new HashMap<>();
                Set<Integer> l_countriesToRemove = new HashSet<>();

                int l_i = 1;
                while(l_i < p_splitCommand.length) {
                    String l_operation = p_splitCommand[l_i++];
                    int l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                            l_countriesToAdd.put(l_countryId, l_continentId);
                        }
                        case MapEditorCommand.REMOVE -> l_countriesToRemove.add(l_countryId);
                    }
                }

                l_phase.editCountry(l_countriesToAdd, l_countriesToRemove);
            }
            case EDIT_NEIGHBOUR -> {
                Map<Integer, Integer> l_neighboursToAdd = new HashMap<>();
                Map<Integer, Integer> l_neighboursToRemove = new HashMap<>();

                int l_i = 1;
                while(l_i < p_splitCommand.length) {
                    String l_operation = p_splitCommand[l_i++];

                    switch (l_operation) {
                        case MapEditorCommand.ADD -> {
                            int l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                            int l_neighbourCountryId = Integer.parseInt(p_splitCommand[l_i++]);
                            l_neighboursToAdd.put(l_countryId, l_neighbourCountryId);
                        }
                        case MapEditorCommand.REMOVE -> {
                            int l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                            int l_neighbourCountryId = Integer.parseInt(p_splitCommand[l_i++]);
                            l_neighboursToRemove.put(l_countryId, l_neighbourCountryId);
                        }
                    }
                }

                l_phase.editNeighbour(l_neighboursToAdd, l_neighboursToRemove);
            }
            case VALIDATE_MAP -> l_phase.validateMap();
            case EDIT_MAP -> l_phase.editMap(p_splitCommand[1]);
            case SAVE_MAP -> l_phase.saveMap(p_splitCommand[1]);
            case LOAD_MAP -> l_phase.loadMap(p_splitCommand[1]);
            case GAME_PLAYER -> {
                Set<String> l_gamePlayersToAdd = new HashSet<>();
                Set<String> l_gamePlayersToRemove = new HashSet<>();

                for (int i = 1; i < p_splitCommand.length; i+=2) {

                    String l_operation = p_splitCommand[i];
                    String l_playerName = p_splitCommand[i+1];

                    switch (l_operation) {
                        case Command.ADD -> l_gamePlayersToAdd.add(l_playerName);
                        case Command.REMOVE -> l_gamePlayersToRemove.add(l_playerName);
                    }
                }

                l_phase.gamePlayer(l_gamePlayersToAdd, l_gamePlayersToRemove);
            }
            case ASSIGN_COUNTRIES -> l_phase.assignCountries();
            case DEPLOY -> {
                int l_countryId = Integer.parseInt(p_splitCommand[1]);
                int l_troopsCount = Integer.parseInt(p_splitCommand[2]);
                l_phase.deploy(l_countryId, l_troopsCount);
            }
            case ADVANCE -> {
                int l_sourceCountryId = Integer.parseInt(p_splitCommand[1]);
                int l_targetCountryId = Integer.parseInt(p_splitCommand[2]);
                int l_troopsCount = Integer.parseInt(p_splitCommand[3]);
                l_phase.advance(l_sourceCountryId, l_targetCountryId, l_troopsCount);
            }
            case BOMB -> {
                int l_targetCountryId = Integer.parseInt(p_splitCommand[1]);
                l_phase.bomb(l_targetCountryId);
            }
            case AIRLIFT -> {
                int l_sourceCountryId = Integer.parseInt(p_splitCommand[1]);
                int l_targetCountryId = Integer.parseInt(p_splitCommand[2]);
                int l_troopsCount = Integer.parseInt(p_splitCommand[3]);
                l_phase.airlift(l_sourceCountryId, l_targetCountryId, l_troopsCount);
            }
            case NEGOTIATE -> {
                int l_targetCountryId = Integer.parseInt(p_splitCommand[1]);
                l_phase.negotiate(l_targetCountryId);
            }
            case BLOCKADE -> {
                int l_countryId = Integer.parseInt(p_splitCommand[1]);
                l_phase.blockade(l_countryId);
            }
            case COMMIT -> l_phase.commit();
        }
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
