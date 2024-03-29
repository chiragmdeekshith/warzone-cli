package com.fsociety.warzone.asset.command;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This Class implements command processing, where the input command leads to
 * the appropriate method being called on the
 * current phase.
 */
public class CommandProcessor {

    private static final Map<String, Command> d_commands;

    private static final Set<String> d_tournamentOptions;

    static {
        d_commands = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::getCommand, Function.identity()));

        d_tournamentOptions = Set.of(Command.MAPS_OPTION,
                Command.PLAYER_OPTION,
                Command.GAMES_OPTION,
                Command.TURNS_OPTION);

    }

    /**
     * This method takes in a command and splits it for execution.
     * 
     * @param p_rawCommand the input command
     */
    public static void processCommand(String p_rawCommand) {
        if (p_rawCommand.isEmpty()) {
            Console.print("Command is empty. It cannot be empty.");
            return;
        }
        final String[] l_splitCommand = p_rawCommand.split(" ");
        if (!CommandValidator.validateCommand(l_splitCommand)) {
            return;
        }
        executeCommand(l_splitCommand);
    }

    private static void executeCommand(String[] p_splitCommand) {
        Phase l_phase = GameEngine.getPhase();
        String l_commandName = p_splitCommand[0];
        switch (d_commands.get(l_commandName)) {
            case HELP -> l_phase.help();
            case PLAY_GAME -> l_phase.playGame();
            case MAP_EDITOR -> l_phase.mapEditor();
            case TOURNAMENT -> {
                ArrayList<String> l_playerStrategies = new ArrayList<>();
                ArrayList<String> l_mapFiles = new ArrayList<>();
                int l_numberOfGames = 0;
                int l_maxTurns = 0;

                int l_i = 1;
                for (int i = 0; i < d_tournamentOptions.size(); i++) {
                    switch (p_splitCommand[l_i]) {
                        case Command.MAPS_OPTION -> {
                            l_i++;
                            while (!d_tournamentOptions.contains(p_splitCommand[l_i])) {
                                l_mapFiles.add(p_splitCommand[l_i]);
                                l_i++;
                                if (l_i > p_splitCommand.length - 1) {
                                    break;
                                }
                            }
                        }
                        case Command.PLAYER_OPTION -> {
                            l_i++;
                            while (!d_tournamentOptions.contains(p_splitCommand[l_i])) {
                                l_playerStrategies.add(p_splitCommand[l_i]);
                                l_i++;
                                if (l_i > p_splitCommand.length - 1) {
                                    break;
                                }
                            }
                        }
                        case Command.GAMES_OPTION -> {
                            l_i++;
                            l_numberOfGames = Integer.parseInt(p_splitCommand[l_i++]);
                        }
                        case Command.TURNS_OPTION -> {
                            l_i++;
                            l_maxTurns = Integer.parseInt(p_splitCommand[l_i++]);
                        }
                    }
                }
                l_phase.tournamentMode(l_numberOfGames, l_maxTurns, l_playerStrategies, l_mapFiles);
            }
            case BACK -> l_phase.back();
            case EXIT -> l_phase.exit();
            case SHOW_MAP -> l_phase.showMap();
            case EDIT_CONTINENT -> {
                Map<Integer, Integer> l_continentsToAdd = new HashMap<>();
                Set<Integer> l_continentsToRemove = new HashSet<>();
                int l_i = 1;
                while (l_i < p_splitCommand.length) {
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
                while (l_i < p_splitCommand.length) {
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
                while (l_i < p_splitCommand.length) {
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
            case SAVE_MAP -> l_phase.saveMap(p_splitCommand);
            case LOAD_MAP -> l_phase.loadMap(p_splitCommand[1]);
            case SAVE_GAME -> l_phase.saveGame(p_splitCommand[1]);
            case LOAD_GAME -> l_phase.loadGame(p_splitCommand[1]);
            case GAME_PLAYER -> {
                Map<String, String> l_gamePlayersToAdd = new HashMap<>();
                Set<String> l_gamePlayersToRemove = new HashSet<>();

                int l_i = 1;
                String l_playerStrategy = Command.HUMAN;
                if (!Command.ADD.equals(p_splitCommand[1]) && !Command.REMOVE.equals(p_splitCommand[1])) {
                    l_playerStrategy = p_splitCommand[1];
                    l_i++;
                }

                for (; l_i < p_splitCommand.length - 1; l_i += 2) {

                    String l_operation = p_splitCommand[l_i];
                    String l_playerName = p_splitCommand[l_i + 1];

                    switch (l_operation) {
                        case Command.ADD -> l_gamePlayersToAdd.put(l_playerName, l_playerStrategy);
                        case Command.REMOVE -> l_gamePlayersToRemove.add(l_playerName);
                        default -> Console.print("Invalid syntax.");
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
                String l_playerName = p_splitCommand[1];
                l_phase.negotiate(l_playerName);
            }
            case BLOCKADE -> {
                int l_countryId = Integer.parseInt(p_splitCommand[1]);
                l_phase.blockade(l_countryId);
            }
            case COMMIT -> l_phase.commit();
            case SHOW_CARDS -> l_phase.showCards();
            case SHOW_AVAILABLE_ARMIES -> l_phase.showAvailableArmies();
            case SHOW_PLAYERS -> l_phase.showPlayers();
        }
    }
}
