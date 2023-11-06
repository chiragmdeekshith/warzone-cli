package com.fsociety.warzone.command;

<<<<<<< Updated upstream
import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.phase.Phase;
import com.fsociety.warzone.util.Console;

import java.util.*;
=======
import com.fsociety.warzone.phase.Phase;
import com.fsociety.warzone.util.Console;

import java.util.HashMap;
import java.util.Map;
>>>>>>> Stashed changes

public class CommandProcessor {

    private static final Map<String, Command> d_commands;

    static {
        d_commands = new HashMap<>();
        for (Command l_command : Command.values()) {
            d_commands.put(l_command.getCommand(), l_command);
        }
    }

<<<<<<< Updated upstream
    public static void processCommand(String p_rawCommand) {
=======
    public static void processCommand(String p_rawCommand, Phase p_phase) {
>>>>>>> Stashed changes
        if(p_rawCommand.isEmpty()) {
            Console.print("Command is empty. It cannot be empty.");
            return;
        }
<<<<<<< Updated upstream
        String[] l_splitCommand = p_rawCommand.split(" ");
        if(!CommandValidator.validateCommand(l_splitCommand)) {
            return;
        }
        executeCommand(l_splitCommand);
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
=======
        String[] l_parsedCommand = parseCommand(p_rawCommand);
    }

    private static String[] parseCommand(String p_rawCommand) {
        return p_rawCommand.split(" ");
    }

    private static boolean validateCommand(String[] p_parsedCommand) {
        String l_commandName = p_parsedCommand[0];

        // If the command is not present in the list of valid commands, the command is not valid
        if(!d_commands.containsKey(l_commandName)) {
            return false;
        }


        switch (d_commands.get(l_commandName)) {
            case BACK -> {

            }
        }
        return true;
    }
>>>>>>> Stashed changes

                int l_i = 1;
                while(l_i < p_splitCommand.length) {
                    String l_operation = p_splitCommand[l_i++];
                    int l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                    switch (l_operation) {
                        case Command.ADD -> {
                            int l_continentBonusValue = Integer.parseInt(p_splitCommand[l_i++]);
                            l_continentsToAdd.put(l_continentId, l_continentBonusValue);
                        }
                        case Command.REMOVE -> l_continentsToRemove.add(l_continentId);
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
                        case Command.ADD -> {
                            int l_continentId = Integer.parseInt(p_splitCommand[l_i++]);
                            l_countriesToAdd.put(l_countryId, l_continentId);
                        }
                        case Command.REMOVE -> l_countriesToRemove.add(l_countryId);
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
                        case Command.ADD -> {
                            int l_countryId = Integer.parseInt(p_splitCommand[l_i++]);
                            int l_neighbourCountryId = Integer.parseInt(p_splitCommand[l_i++]);
                            l_neighboursToAdd.put(l_countryId, l_neighbourCountryId);
                        }
                        case Command.REMOVE -> {
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
}
