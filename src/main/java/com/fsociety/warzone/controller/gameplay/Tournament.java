package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.play.playsetup.PlayPostLoad;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.player.strategy.*;
import com.fsociety.warzone.model.player.strategy.Random;
import com.fsociety.warzone.util.DominationMapTools;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This class implements a Tournament object which stores the input parameters, runs the tournament on them, and then
 * prints the results.
 */
public class Tournament {

    private final int d_numberOfGames;
    private final int d_maxNumberOfTurns;
    private final ArrayList<String> d_botPlayers;
    private final ArrayList<String> d_maps;
    private final String[][] d_results;
    private Player d_lastWinner;

    /**
     * Constructor for a Tournament object created with the specified parameters.
     * @param p_numberOfGames the number of games
     * @param p_maxNumberOfTurns the max number of turns
     * @param p_botPlayers the list of player strategy types
     * @param p_maps the list of map file names
     */
    public Tournament(int p_numberOfGames, int p_maxNumberOfTurns, ArrayList<String> p_botPlayers, ArrayList<String> p_maps) {
        this.d_numberOfGames = p_numberOfGames;
        this.d_maxNumberOfTurns = p_maxNumberOfTurns;
        this.d_botPlayers = p_botPlayers;
        this.d_maps = p_maps;
        this.d_results = new String[d_maps.size()][p_numberOfGames];
    }

    /**
     * This method runs a tournament by setting up each game according to the input and recording the results in
     * an array.
     */
    public void runTournament() {

        GameplayController.setTournament(this);

        // Initialize players with names
        Map<String, String> l_botPlayerNames = new HashMap<>();
        for (String l_strategy : d_botPlayers) {
            String l_name = "";
            switch (l_strategy) {
                case Command.AGGRESSIVE -> l_name = "Aggressive";
                case Command.BENEVOLENT -> l_name = "Benevolent";
                case Command.CHEATER -> l_name = "Cheater";
                case Command.RANDOM -> l_name = "Random";
            }
            int j = 0;
            while (true) {
                if (l_botPlayerNames.containsKey(l_name + "Bot" + j)) {
                    j++;
                } else {
                    break;
                }
            }
            l_botPlayerNames.put(l_name + "Bot" + j, l_strategy);
        }

        for (int i = 0; i < d_maps.size(); i++) {

            for (int j = 0; j < d_numberOfGames; j++) {

                // Load and validate map for next game
                PlayMap l_playMap = DominationMapTools.loadAndValidatePlayableMap(d_maps.get(i));
                if(null == l_playMap) {
                    Console.print("Failed to load map" + d_maps.get(i) + "! Returning to Main Menu.");
                    return;
                }
                GameplayController.setPlayMap(l_playMap);
                Console.print("Loaded map \"" + d_maps.get(i) + "\"");

                // Reinitialize players
                Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
                for(String l_playerName : l_botPlayerNames.keySet()) {
                    Strategy l_playerStrategy = null;
                    switch (l_botPlayerNames.get(l_playerName)) {
                        case Command.AGGRESSIVE -> l_playerStrategy = new Aggressive();
                        case Command.BENEVOLENT -> l_playerStrategy = new Benevolent();
                        case Command.CHEATER -> l_playerStrategy = new Cheater();
                        case Command.RANDOM -> l_playerStrategy = new Random();
                    }
                    l_playerNameMap.put(l_playerName, new Player(l_playerName, l_playerStrategy));
                    Console.print("Player " + l_playerName + " added.");
                }

                // Assign Countries and start current game
                new PlayPostLoad().assignCountries();

                logResult(d_lastWinner, i, j);
                }
            }

        GameplayController.setTournament(null);
        printResults();
    }

    /**
     * This method sets the winner for the last game of a tournament.
     * @param p_winner the winning Player
     */
    public void setLastWinner(Player p_winner) {
        this.d_lastWinner = p_winner;
    }

    private void logResult(Player p_lastWinner, int p_currMap, int p_currGame) {
        String l_result;
        if (p_lastWinner == null) {
            l_result = "Draw";
        } else {
            l_result = p_lastWinner.getPlayerStrategy().toString();
        }
        d_results[p_currMap][p_currGame] = l_result;
    }

    /**
     * This method formats the results of a tournament as a table and prints it.
     */
    private void printResults() {
        StringBuilder l_resultsString = new StringBuilder("\nTournament Results: \n");
        l_resultsString.append("--------");
        l_resultsString.append("------------".repeat(d_numberOfGames));
        l_resultsString.append("\n");
        l_resultsString.append("\t\t");
        for (int j = 0; j < d_numberOfGames; j++) {
            l_resultsString.append("Game ").append(j + 1).append("\t\t");
        }
        l_resultsString.append("\n");
        for (int i = 0; i < d_maps.size(); i++) {
            l_resultsString.append("Map ").append(i + 1).append("\t");
            for (int j = 0; j < d_numberOfGames; j++) {
                l_resultsString.append(d_results[i][j]);
                if (d_results[i][j].equals("Aggressive") || d_results[i][j].equals("Benevolent")) {
                    l_resultsString.append("\t");
                } else {
                    l_resultsString.append("\t\t");
                }
            }
            l_resultsString.append("\n");
        }
        l_resultsString.append("--------");
        l_resultsString.append("------------".repeat(d_numberOfGames));
        l_resultsString.append("\n");
        Console.print(l_resultsString.toString());
    }

    /**
     * This method returns the maximum number of turns.
     * @return the maximum number of turns
     */
    public int getMaxNumberOfTurns() {
        return this.d_maxNumberOfTurns;
    }

}
