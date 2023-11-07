package com.fsociety.warzone.game;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.game.mainloop.ExecuteOrder;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.game.startup.StartUp;
import com.fsociety.warzone.map.PlayMap;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.phase.end.End;
import com.fsociety.warzone.util.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class handles everything related to playing a game of Warzone.
 */
public class GameEngine {

    private static ArrayList<Player> d_players = new ArrayList<>();
    private static HashMap<Integer, Player> d_playerList;
    private static PlayMap d_playMap;
    private static HashMap<Integer, HashSet<Integer>> d_truces;
    private static boolean gameWon = false;

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
            Console.print("Turn " + l_turns);

            // Get updated continent owner for each continent
            d_playMap.getContinents().keySet().forEach(continentId -> {
                d_playMap.getContinents().get(continentId).computeAndSetContinentOwner();
            });
            // Assign Reinforcements Phase
            AssignReinforcements.assignReinforcements(d_players);

            String l_assignReinforcementsEnd = "All players have deployed their reinforcements.";
            Console.print(l_assignReinforcementsEnd);

            // Issue Orders Phase
            if (!IssueOrder.issueOrders(d_players)) {
                return; // Return to main menu
            }

            String l_issueOrdersEnd = "All players have committed their list of orders.\nExecuting orders...";
            Console.print(l_issueOrdersEnd);

            // Execute Orders Phase
            ExecuteOrder.executeOrders(d_players);

            if (gameWon) {
                GameRunner.setPhase(new End());
                endGame();
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
        return (l_playerIds.size() == 1);
    }

    /**
     * This method sets the gameWon variable to true which is checked in the main loop to assure the game has ended.
     */
    public static void setGameWon() {
        gameWon = true;
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

    /**
     * This method allows for the post-game phase when a player has won. Certain commands, such as showmap, still work
     * in this phase so that players can look back on the game they played before returning to the main menu.
     */
    public static void endGame() {
        //TODO: make this work
        //while(true) {
        //    String l_command = Console.commandPrompt();
        //    CommandProcessor.processCommand(l_command);
        //}
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

    public  static void setTruces(HashMap<Integer, HashSet<Integer>> p_truces) {
        d_truces = p_truces;
    }

}
