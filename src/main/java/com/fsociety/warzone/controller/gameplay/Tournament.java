package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.play.playsetup.PlayPostLoad;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.player.strategy.*;
import com.fsociety.warzone.model.player.strategy.Random;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tournament {

    private int d_numberOfGames;
    private int d_maxNumberOfTurns;
    private ArrayList<String> d_botPlayers;
    private ArrayList<String> d_maps;
    private String[][] d_results;
    private Player d_lastWinner;

    public Tournament(int p_numberOfGames, int p_maxNumberOfTurns, ArrayList<String> p_botPlayers, ArrayList<String> p_maps) {
        this.d_numberOfGames = p_numberOfGames;
        this.d_maxNumberOfTurns = p_maxNumberOfTurns;
        this.d_botPlayers = p_botPlayers;
        this.d_maps = p_maps;
        this.d_results = new String[d_maps.size()][p_numberOfGames];
    }


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
                PlayMap l_playMap = MapTools.loadAndValidatePlayableMap(d_maps.get(i));
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

    private void printResults() {
        String l_resultsString = "\nTournament Results: \n";
        l_resultsString += "----------------------------\n";
        l_resultsString += "\t\t";
        for (int j = 0; j < d_numberOfGames; j++) {
            l_resultsString += "Game " + (j+1) + "\t\t";
        }
        l_resultsString += "\n";
        for (int i = 0; i < d_maps.size(); i++) {
            l_resultsString += "Map " + (i+1) + "\t";
            for (int j = 0; j < d_numberOfGames; j++) {
                l_resultsString += d_results[i][j];
                if (d_results[i][j].equals("Aggressive") || d_results[i][j].equals("Benevolent")) {
                    l_resultsString += "\t";
                } else {
                    l_resultsString += "\t\t";
                }
            }
            l_resultsString += "\n";
        }
        l_resultsString += "----------------------------\n";
        Console.print(l_resultsString);
    }

    public int getMaxNumberOfTurns() {
        return this.d_maxNumberOfTurns;
    }

}
