package com.fsociety.warzone.controller;

import com.fsociety.warzone.controller.gameplay.ExecuteOrder;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.controller.gameplay.AssignReinforcements;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.model.map.PlayMap;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.asset.phase.end.End;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.view.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This class handles everything related to playing a game of Warzone.
 */
public class GameplayController {

    private static ArrayList<Player> d_players;
    private static Map<String, Player> d_playerNameMap = new HashMap<>();

    private static Map<Integer, String> d_playerIdMap = new HashMap<>();
    private static PlayMap d_playMap;
    private static HashMap<Integer, HashSet<Integer>> d_truces;
    private static boolean d_gameWon = false;
    public static Player d_winner;

    /**
     * This method implements the loop through the three main game phases: Assign Reinforcements, Issue Orders, and
     * Execute Orders. At each phase, each player is looped over and the corresponding method is called.
     */
    public static void gamePlayLoop() {
        Console.print("Game Start!");
        int l_turns = 0;
        while (true) {

            l_turns++;
            Console.print("Turn " + l_turns);

            // Get updated continent owner for each continent
            d_playMap.getContinents().keySet().forEach(continentId -> {
                d_playMap.getContinents().get(continentId).computeAndSetContinentOwner();
            });

            // Assign Reinforcements Phase
            GameEngine.setPhase(new Reinforcement());
            AssignReinforcements.assignReinforcements(d_players);
            Console.print("All players have deployed their reinforcements.");

            // Issue Orders Phase
            GameEngine.setPhase(new Attack());
            IssueOrder.issueOrders(d_players);
            Console.print("All players have committed their list of orders.\nExecuting orders...");

            // Execute Orders Phase
            ExecuteOrder.executeOrders(d_players);

            if (d_gameWon) {
                Console.print(d_winner.getName() + " has conquered the map and won the game! Congratulations!");
                GameEngine.setPhase(new End());
                return;
            }

            resetRound();

            Console.print("All orders executed. Turn " + l_turns + " over.");
        }

    }

    /**
     * This method resets variables that must be in a certain state at the beginning of each round, this being the set
     * of truces modified when Diplomacy cards are played, and the committed and cardDrawn variables for each player.
     */
    private static void resetRound() {
        for (Player l_player : d_players) {
            if (l_player.isEliminated()) {
                d_players.remove(l_player);
                continue;
            }
            d_truces.put(l_player.getId(), new HashSet<>());
            l_player.resetCommitted();
            l_player.resetCardDrawn();
        }
    }

    /**
     * This method checks whether one player owns every country on the map.
     */
    public static boolean checkWinCondition() {
        HashSet<Integer> l_playerIds = new HashSet<>();
        for (Country l_country : d_playMap.getCountries().values()) {
            l_playerIds.add(l_country.getPlayerId());
        }
        if (l_playerIds.size() == 1) {
            for (Player l_player : d_players) {
                if (l_playerIds.contains(l_player.getId())) {
                    d_winner = l_player;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * This method sets the gameWon variable to true which is checked in the main loop to assure the game has ended.
     */
    public static void setGameWon() {
        d_gameWon = true;
    }

    /**
     * This method creates a list of Player objects to be stored in the GameEngine.
     */
    public static void finalizePlayers() {
        d_players = new ArrayList<>(d_playerNameMap.values());
        d_playerIdMap = new HashMap<>();
        for(Player l_player: d_players) {
            d_playerIdMap.put(l_player.getId(), l_player.getName());
        }
    }

    public static void resetGameState() {
        d_players = null;
        d_playerNameMap = new HashMap<>();
        d_playerIdMap = new HashMap<>();
        d_playMap = null;
        d_truces = null;
        d_gameWon = false;
        d_winner = null;
    }

    public  static void initTruces() {
        d_truces = new HashMap<>();
        for (Player l_player : d_players) {
            d_truces.put(l_player.getId(), new HashSet<>());
        }
    }

    public static void printPlayers() {
        StringBuilder l_players = new StringBuilder();
        for (Player l_player : d_players) {
            l_players.append(l_player.getName()).append("\n");
        }
        Console.print(l_players.toString());
    }


    // Getters and Setters
    public static ArrayList<Player> getPlayers() {
        return d_players;
    }
    public static void setPlayers(ArrayList<Player> p_players) {
        d_players = p_players;
    }

    public static Map<String, Player> getPlayerNameMap() {
        return d_playerNameMap;
    }

    public static PlayMap getPlayMap() {
        return d_playMap;
    }

    public static HashMap<Integer, HashSet<Integer>> getTruces() {
        return d_truces;
    }

    public static void setPlayMap(PlayMap p_playMap) {
        d_playMap = p_playMap;
    }

    public static String getPlayerNameFromId(Integer p_playerId) {
        return d_playerIdMap.get(p_playerId);
    }
}
