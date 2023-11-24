package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.end.End;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.asset.phase.play.playsetup.PlayPostLoad;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.player.strategy.*;
import com.fsociety.warzone.model.player.strategy.Random;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.view.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tournament {

    private int d_numberOfGames;
    private int d_maxNumberOfTurns;
    private ArrayList<String> d_botPlayers;
    private ArrayList<String> d_maps;
    private String[][] results;
    private Player d_lastWinner;

    public Tournament(int p_numberOfGames, int p_maxNumberOfTurns, ArrayList<String> p_botPlayers, ArrayList<String> p_maps) {
        this.d_numberOfGames = p_numberOfGames;
        this.d_maxNumberOfTurns = p_maxNumberOfTurns;
        this.d_botPlayers = p_botPlayers;
        this.d_maps = p_maps;
        this.results = new String[d_maps.size()][p_numberOfGames];
    }


    public void runTournament() {

        GameplayController.setTournament(this);

        // Initialize players with names
        Map<String, String> l_botPlayerNames = new HashMap<>();
        for (String l_strategy : d_botPlayers) {
            int j = 0;
            while (true) {
                if (l_botPlayerNames.containsKey(l_strategy + "Bot" + j)) {
                    j++;
                } else {
                    break;
                }
            }
            l_botPlayerNames.put(l_strategy + "Bot" + j, l_strategy);
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
                for(String l_playerName : l_botPlayerNames.values()) {
                    Strategy l_playerStrategy = null;
                    switch (l_botPlayerNames.get(l_playerName)) {
                        case Command.HUMAN -> l_playerStrategy = new Human();
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

        GameEngine.setPhase(new Menu());
        Console.print("""
                                                          \s
                 _ _ _ _____ _____ _____ _____ _____ _____\s
                | | | |  _  | __  |__   |     |   | |   __|
                | | | |     |    -|   __|  |  | | | |   __|
                |_____|__|__|__|__|_____|_____|_|___|_____|
                                                          \s""");
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
        results[p_currMap][p_currGame] = l_result;
    }

    private void printResults() {
        for (int i = 0; i < d_maps.size(); i++) {
            for (int j = 0; j < d_numberOfGames; j++) {
            }
        }
    }

    public int getMaxNumberOfTurns() {
        return this.d_maxNumberOfTurns;
    }

}
