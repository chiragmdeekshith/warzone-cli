package com.fsociety.warzone.game;

import com.fsociety.warzone.game.mainloop.ExecuteOrder;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.game.startup.StartUp;
import com.fsociety.warzone.map.PlayMap;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class handles everything related to playing a game of Warzone.
 */
public class GameEngine {

    private static ArrayList<Player> d_players;
    private static HashMap<Integer, Player> d_playerList;
    private static PlayMap d_playMap;
    private static HashMap<Integer, HashSet<Integer>> d_truces;

    /**
     * This method implements a game engine by running the start-up phase, and upon success, the main loop that
     * implements the gameplay. The return statement allows the user to return to the main menu if the 'back'
     * command is used within the startUp() or mainLoop() methods.
     */
    public static void playGame() {
        if(!StartUp.startUp()){
            return; // Return to main menu
        }
        mainLoop();
    }

    /**
     * This method implements the loop through the three main game phases: Assign Reinforcements, Issue Orders, and
     * Execute Orders. At each phase, each player is looped over and the corresponding method is called.
     */
    public static void mainLoop() {
        System.out.println("Game Start!");
        int l_turns = 0;
        while (true) {
            l_turns++;
            System.out.println("Turn " + l_turns);

            // Get updated continent owner for each continent
            d_playMap.getContinents().keySet().forEach(continentId -> {
                d_playMap.getContinents().get(continentId).computeAndSetContinentOwner();
            });
            // Assign Reinforcements Phase
            for (Player l_player : d_players) {
                AssignReinforcements.assignReinforcements(l_player);
            }

            // Issue Orders Phase
            if (!IssueOrder.issueOrders(d_players)) {
                return; // Return to main menu
            }

            System.out.println("All players have deployed their reinforcements.");
            System.out.println("Executing orders...");

            // Execute Orders Phase
            ExecuteOrder.executeOrders(d_players);

            resetRound();

            // TODO: Check for winner

            System.out.println("All orders executed. Turn " + l_turns + " over.");
        }

    }

    private static void resetRound() {
        for (Player l_player : d_players) {
            d_truces.put(l_player.getId(), new HashSet<>());
            l_player.resetCardDrawn();
        }
    }


    /**
     * This method creates a map between Player objects and their playerIDs to be stored in the GameEngine.
     */
    public static void initPlayerList() {
        d_playerList = new HashMap<>();
        for (Player l_player : d_players) {
            d_playerList.put(l_player.getId(), l_player);
        }
    }

    // Getters and Setters
    public static ArrayList<Player> getPlayers() {
        return d_players;
    }
    public static void setPlayers(ArrayList<Player> p_players) {
        d_players = p_players;
    }

    public static HashMap<Integer, Player> getPlayerList() {
        return d_playerList;
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

}
