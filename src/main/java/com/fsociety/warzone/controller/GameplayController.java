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

    /**
     * This variable is set to a Tournament object when a tournament is in progress
     */
    private static Tournament d_currentTournament = null;

    /**
     * Holds the turn number
     */
    private static int d_turns;

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
    public static void gamePlayLoop(boolean p_isNewGame) {
        if(p_isNewGame) {
            Console.print("Game Start!");
            d_turns = 0;
        } else {
            Console.print("Game Resume!");
        }

        while (true) {
            if(!p_isNewGame) {
                Console.print("Turn " + d_turns,true);
                if(GameEngine.getPhase() instanceof Reinforcement) {
                    AssignReinforcements.assignReinforcements(d_players, p_isNewGame);
                    Console.print("All players have deployed their reinforcements.");
                }
                p_isNewGame = true;
            } else {
                d_turns++;
                Console.print("Turn " + d_turns,true);

                // Get updated continent owner for each continent
                d_playMap.getContinents().keySet().forEach(continentId -> {
                    d_playMap.getContinents().get(continentId).computeAndSetContinentOwner();
                });

                // Assign Reinforcements Phase
                GameEngine.setPhase(new Reinforcement());
                AssignReinforcements.assignReinforcements(d_players, p_isNewGame);
                Console.print("All players have deployed their reinforcements.");
            }

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
                } else if (d_turns == d_currentTournament.getMaxNumberOfTurns()) {
                    Console.print(d_currentTournament.getMaxNumberOfTurns() + " turns reached. Draw!", true);
                    Log.flushToFile();
                    d_currentTournament.setLastWinner(null);
                    GameplayController.resetGameState();
                    return;
                }
            }

            resetRound();

            Console.print("All orders executed. Turn " + d_turns + " over.");
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

    /**
     * Get the gameWon variable
     * @return the game won variable
     */
    public static boolean getGameWon() {
        return d_gameWon;
    }

    /**
     * Get the tournament object
     * @return the tournament object
     */
    public static Tournament getCurrentTournament() {
        return d_currentTournament;
    }

    /**
     * Get the player ID Map
     * @return the player ID map
     */
    public static Map<Integer, String> getPlayerIdMap() {
        return d_playerIdMap;
    }

    /**
     * Return the winner player
     * @return the winner player
     */
    public static Player getWinner() {
        return d_winner;
    }

    /**
     * Set the players
     * @param p_players the players list
     */
    public static void setPlayers(ArrayList<Player> p_players) {
        d_players = p_players;
    }

    /**
     * Set the player name map
     * @param p_playerNameMap the player name map
     */
    public static void setPlayerNameMap(Map<String, Player> p_playerNameMap) {
        d_playerNameMap = p_playerNameMap;
    }

    /**
     * Set The player ID map
     * @param p_playerIdMap The player ID map
     */
    public static void setPlayerIdMap(Map<Integer, String> p_playerIdMap) {
        d_playerIdMap = p_playerIdMap;
    }

    /**
     * Set the truces
     * @param p_truces the truces
     */
    public static void setTruces(HashMap<Integer, HashSet<Integer>> p_truces) {
        d_truces = p_truces;
    }

    /**
     * Set the game won variable
     * @param p_gameWon the game won variable
     */
    public static void setGameWon(boolean p_gameWon) {
        d_gameWon = p_gameWon;
    }

    /**
     * Set the current tournament
     * @param p_currentTournament the current tournament
     */
    public static void setCurrentTournament(Tournament p_currentTournament) {
        d_currentTournament = p_currentTournament;
    }

    /**
     * Set the winner
     * @param p_winner the winner
     */
    public static void setWinner(Player p_winner) {
        d_winner = p_winner;
    }

    /**
     * Set the game won variable
     * @param p_gameWon the game won variable data
     */
    public static void setGameWonForLoad(boolean p_gameWon) {
        d_gameWon = p_gameWon;
    }

    /**
     * Get the number of turns
     * @return the number of turns
     */
    public static int getTurns() {
        return d_turns;
    }

    /**
     * Set the number of turns
     * @param p_turns the number of turns
     */
    public static void setTurns(int p_turns) {
        d_turns = p_turns;
    }
}
