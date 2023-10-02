package com.fsociety.warzone.game;

import com.fsociety.warzone.game.mainloop.ExecuteOrder;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.game.startup.StartUp;
import com.fsociety.warzone.map.WZMap;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {

    private static ArrayList<Player> d_players;
    private static HashMap<Integer, Player> d_playerList;
    private static WZMap d_wzMap;

    public static ArrayList<Player> getPlayers() {
        return d_players;
    }
    public static void setPlayers(ArrayList<Player> p_players) {
        d_players = p_players;
    }

    public static HashMap<Integer, Player> getPlayerList() {
        return d_playerList;
    }

    public static WZMap getWZMap() {
        return d_wzMap;
    }

    public static void setWZMap(WZMap p_wzMap) {
        d_wzMap = p_wzMap;
    }

    public static void playGame() {
        if(!StartUp.start_up()){
            return;
        }
        mainLoop();
    }

    public static void mainLoop() {
        System.out.println("Game Start!");
        while (true) {

            // Check continent owner for each continent
            d_wzMap.getContinents().keySet().forEach(continentId -> {
                d_wzMap.getContinents().get(continentId).setContinentOwner();
            });
            // Assign Reinforcements Phase
            for (int i = 0; i < d_players.size(); i++) {
                AssignReinforcements.assign_reinforcements(d_players.get(i));
            }

            // Issue Orders Phase
            IssueOrder.issue_orders(d_players);

            // Execute Orders Phase
            ExecuteOrder.execute_orders(d_players);
        }

    }

    /**
     * @TODO Implement the "show map" function to work at any point during gameplay
     */

    public static void initPlayerList() {
        d_playerList = new HashMap<Integer, Player>();
        for (int i = 0; i < d_players.size(); i++) {
            d_playerList.put(d_players.get(i).getId(), d_players.get(i));
        }
    }


}
