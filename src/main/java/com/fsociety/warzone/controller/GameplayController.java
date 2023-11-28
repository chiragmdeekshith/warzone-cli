package com.fsociety.warzone.controller;

import com.fsociety.warzone.controller.gameplay.ExecuteOrder;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.controller.gameplay.AssignReinforcements;
import com.fsociety.warzone.controller.gameplay.Tournament;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.map.PlayMap;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.asset.phase.end.End;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.util.MapTools;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.view.log.Log;

import java.util.*;

/**
 * This class controls the turn-based gameplay loop.
 */
public class GameplayController {

    /**
     * The finalized list of players in the game after the startup phase
     */
    private static ArrayList<Player> d_players;

    /**
     * The map which holds players while they are being added and removed in the startup phase
     */
    private static Map<String, Player> d_playerNameMap = new HashMap<>();

    /**
     * A map to access the player name by playerId
     */
    private static Map<Integer, String> d_playerIdMap = new HashMap<>();

    /**
     * The Gameplay Map on which the game runs.
     */
    private static PlayMap d_playMap;

    /**
     * A collection of player ID to the list of players they have a truce with for the round.
     */
    private static HashMap<Integer, HashSet<Integer>> d_truces;

    /**
     * A variable to track if the game has finished
     */
    private static boolean d_gameWon = false;

    private static Tournament d_currentTournament = null;

    /**
     * The player who has won the game
     */
    public static Player d_winner;

    /**
     * This method implements the loop through the three main game phases: Assign Reinforcements, Issue Orders, and
     * Execute Orders. Print statements update the user as to which phase is taking place. The game ends when the win
     * condition is met, which causes a change to the End Game phase. At the end of each round, the round is reset. If
     * a tournament is taking place, this method sets the winner of the current game for the tournament.
     */
    public static void gamePlayLoop() {
        Console.print("Game Start!");
        int l_turns = 0;
        while (true) {

            l_turns++;
            Console.print("Turn " + l_turns,true);

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

            if (d_currentTournament == null && d_gameWon) {
                Console.print(d_winner.getName() + " has conquered the map and won the game! Congratulations!",true);
                Log.flushToFile();
                GameEngine.setPhase(new End());
                return;
            } else if (d_currentTournament != null) {
                if (d_gameWon) {
                    Console.print(d_winner.getName() + " has conquered the map and won the game! Congratulations!",true);
                    Log.flushToFile();
                    d_currentTournament.setLastWinner(d_winner);
                    GameplayController.resetGameState();
                    return;
                } else if (l_turns == d_currentTournament.getMaxNumberOfTurns()) {
                    Console.print(d_currentTournament.getMaxNumberOfTurns() + " turns reached. Draw!", true);
                    Log.flushToFile();
                    d_currentTournament.setLastWinner(null);
                    GameplayController.resetGameState();
                    return;
                }
            }

            resetRound();

            Console.print("All orders executed. Turn " + l_turns + " over.");
            Log.flushToFile();
        }

    }

    /**
     * This method resets variables that must be in a certain state at the beginning of each round and removes any
     * players that were eliminated during the round. The values being reset are the list of truces (used for the
     * Diplomacy card), whether the player has committed their orders, and whether the player has drawn a card.
     */
    private static void resetRound() {
        List<Player> l_playersToRemove = new ArrayList<>();
        for (Player l_player : d_players) {
            if (l_player.isEliminated()) {
                l_playersToRemove.add(l_player);
                continue;
            }
            d_truces.put(l_player.getId(), new HashSet<>());
            l_player.resetCommitted();
            l_player.resetCardDrawn();
        }
        for (Player l_player : l_playersToRemove) {
            removePlayer(l_player);
            Console.print(l_player.getName() + " was eliminated!",true);
        }
    }


    /**
     * This method allows the use to save the map being worked on to a file.
     * @param p_fileName the file name to be saved to
     * @return true if the file was saved successfully, false otherwise
     */
    public static boolean saveGame(String p_fileName) {
        return MapTools.saveGameFile(d_playMap, d_players, GameEngine.getPhase(),p_fileName);
    }

    /**
     * This method removes references to the player from several data structures used during gameplay.
     * @param p_player the player to be removed
     */
    private static void removePlayer(Player p_player) {
        d_players.remove(p_player);
        d_playerNameMap.remove(p_player.getName());
        d_playerIdMap.remove(p_player.getId());
    }

    /**
     * This method checks whether one player owns every country on the map.
     * @return true if the win condition is met, false otherwise
     */
    public static boolean checkWinCondition() {
        HashSet<Integer> l_playerIds = new HashSet<>();
        // Gather the IDs of the players that own countries, and return true if this set contains only 1 player
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
     * This method creates a list of Player objects and map of Player ID to Player Names to be stored in the
     * GameplayController.
     */
    public static void finalizePlayers() {
        d_players = new ArrayList<>(d_playerNameMap.values());
        d_playerIdMap = new HashMap<>();
        for(Player l_player: d_players) {
            d_playerIdMap.put(l_player.getId(), l_player.getName());
        }
    }

    /**
     * This method resets the state of the game when a player returns to the main menu. This ensures the user can
     * start a new game after finishing a previous one.
     */
    public static void resetGameState() {
        d_players = null;
        d_playerNameMap = new HashMap<>();
        d_playerIdMap = new HashMap<>();
        d_playMap = null;
        d_truces = null;
        d_gameWon = false;
        d_winner = null;
    }

    /**
     * This method initializes the map of truces, creating one set per player in the game.
     */
    public  static void initTruces() {
        d_truces = new HashMap<>();
        for (Player l_player : d_players) {
            d_truces.put(l_player.getId(), new HashSet<>());
        }
    }

    /**
     * This method prints the current list of players.
     */
    public static void printPlayers() {
        StringBuilder l_players = new StringBuilder();
        for (Player l_player : d_playerNameMap.values()) {
            l_players.append(l_player.getName()).append("\n");
        }
        Console.print(l_players.toString());
    }


    // Getters and Setters

    /**\
     * Get the list of active players.
     * @return the list of active players
     */
    public static ArrayList<Player> getPlayers() {
        return d_players;
    }

    /**
     * Get the map of Player names to Player objects.
     * @return the map of names to objects for Players
     */
    public static Map<String, Player> getPlayerNameMap() {
        return d_playerNameMap;
    }

    /**
     * Get the map of the current game.
     * @return the map
     */
    public static PlayMap getPlayMap() {
        return d_playMap;
    }

    /**
     * Get the map of truces for the current game.
     * @return the map of truces
     */
    public static HashMap<Integer, HashSet<Integer>> getTruces() {
        return d_truces;
    }

    /**
     * Set the map for the current game.
     * @param p_playMap the map to be set
     */
    public static void setPlayMap(PlayMap p_playMap) {
        d_playMap = p_playMap;
    }

    /**
     * Get the name of the player given their ID.
     * @param p_playerId the ID of the player
     * @return the name of the player
     */
    public static String getPlayerNameFromId(Integer p_playerId) {
        return d_playerIdMap.get(p_playerId);
    }

    /**
     * Get the player object given their ID.
     * @param p_playerId the ID of the player
     * @return the Player object
     */
    public static Player getPlayerFromId(Integer p_playerId) {
        return d_playerNameMap.get(d_playerIdMap.get(p_playerId));
    }

    /**
     * Sets the Tournament variable if a tournament is taking place.
     * @param p_tournament the current tournament
     */
    public static void setTournament(Tournament p_tournament) {
        d_currentTournament = p_tournament;
    }

}
