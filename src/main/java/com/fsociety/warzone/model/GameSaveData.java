package com.fsociety.warzone.model;

import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.controller.gameplay.Tournament;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Represents the data associated with a game save.
 */
public class GameSaveData implements Serializable {

    /**
     * Current phase of the game
     */
    private Phase d_currentPhase;

    /**
     * The finalized list of players in the game after the startup phase
     */
    private ArrayList<Player> d_players;

    /**
     * The map which holds players while they are being added and removed in the startup phase
     */
    private Map<String, Player> d_playerNameMap;

    /**
     * A map to access the player name by playerId
     */
    private Map<Integer, String> d_playerIdMap;

    /**
     * The Gameplay Map on which the game runs.
     */
    private PlayMap d_playMap;

    /**
     * A collection of player ID to the list of players they have a truce with for the round.
     */
    private HashMap<Integer, HashSet<Integer>> d_truces;

    /**
     * A variable to track if the game has finished
     */
    private boolean d_gameWon;

    /**
     * This variable is set to a Tournament object when a tournament is in progress
     */
    private Tournament d_currentTournament;

    /**
     * The player who has won the game
     */
    private Player d_winner;

    /**
     * Holds the turn number
     */
    private static int d_turns;

    /**
     * Gets the finalized list of players in the game after the startup phase.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return d_players;
    }

    /**
     * Sets the finalized list of players in the game after the startup phase.
     *
     * @param p_players The list of players to set.
     */
    public void setPlayers(ArrayList<Player> p_players) {
        this.d_players = p_players;
    }

    /**
     * Gets the map which holds players while they are being added and removed in the startup phase.
     *
     * @return The player name map.
     */
    public Map<String, Player> getPlayerNameMap() {
        return d_playerNameMap;
    }

    /**
     * Sets the map which holds players while they are being added and removed in the startup phase.
     *
     * @param p_playerNameMap The player name map to set.
     */
    public void setPlayerNameMap(Map<String, Player> p_playerNameMap) {
        this.d_playerNameMap = p_playerNameMap;
    }

    /**
     * Gets the map to access the player name by playerId.
     *
     * @return The player ID map.
     */
    public Map<Integer, String> getPlayerIdMap() {
        return d_playerIdMap;
    }

    /**
     * Sets the map to access the player name by playerId.
     *
     * @param p_playerIdMap The player ID map to set.
     */
    public void setPlayerIdMap(Map<Integer, String> p_playerIdMap) {
        this.d_playerIdMap = p_playerIdMap;
    }

    /**
     * Gets the Gameplay Map on which the game runs.
     *
     * @return The play map.
     */
    public PlayMap getPlayMap() {
        return d_playMap;
    }

    /**
     * Sets the Gameplay Map on which the game runs.
     *
     * @param p_playMap The play map to set.
     */
    public void setPlayMap(PlayMap p_playMap) {
        this.d_playMap = p_playMap;
    }

    /**
     * Gets the collection of player ID to the list of players they have a truce with for the round.
     *
     * @return The truces map.
     */
    public HashMap<Integer, HashSet<Integer>> getTruces() {
        return d_truces;
    }

    /**
     * Sets the collection of player ID to the list of players they have a truce with for the round.
     *
     * @param p_truces The truces map to set.
     */
    public void setTruces(HashMap<Integer, HashSet<Integer>> p_truces) {
        this.d_truces = p_truces;
    }

    /**
     * Checks if the game has finished.
     *
     * @return True if the game has finished, false otherwise.
     */
    public boolean getGameWon() {
        return d_gameWon;
    }

    /**
     * Sets the variable to track if the game has finished.
     *
     * @param p_gameWon True if the game has finished, false otherwise.
     */
    public void setGameWon(boolean p_gameWon) {
        this.d_gameWon = p_gameWon;
    }

    /**
     * Gets the current tournament associated with the game.
     *
     * @return The current tournament.
     */
    public Tournament getCurrentTournament() {
        return d_currentTournament;
    }

    /**
     * Sets the current tournament associated with the game.
     *
     * @param p_currentTournament The current tournament to set.
     */
    public void setCurrentTournament(Tournament p_currentTournament) {
        this.d_currentTournament = p_currentTournament;
    }

    /**
     * Gets the player who has won the game.
     *
     * @return The winning player.
     */
    public Player getWinner() {
        return d_winner;
    }

    /**
     * Sets the player who has won the game.
     *
     * @param p_winner The winning player to set.
     */
    public void setWinner(Player p_winner) {
        this.d_winner = p_winner;
    }

    /**
     * Get the current phase
     * @return the current phase
     */
    public Phase getCurrentPhase() {
        return d_currentPhase;
    }

    /**
     * Set the current phase
     * @param d_currentPhase set the current phase
     */
    public void setCurrentPhase(Phase d_currentPhase) {
        this.d_currentPhase = d_currentPhase;
    }

    /**
     * Get the number of turns
     * @return the number of turns
     */
    public int getTurns() {
        return d_turns;
    }

    /**
     * Set the number of turns
     * @param p_turns the number of turns
     */
    public void setTurns(int p_turns) {
        d_turns = p_turns;
    }
}
