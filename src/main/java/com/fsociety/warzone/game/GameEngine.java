package com.fsociety.warzone.game;

import com.fsociety.warzone.game.mainloop.ExecuteOrder;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.game.startup.StartUp;
import com.fsociety.warzone.map.WZMap;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

public class GameEngine {

    private static ArrayList<Player> d_players;
    private static WZMap d_wzMap;

    public static ArrayList<Player> getPlayers() {
        return d_players;
    }
    public static void setPlayers(ArrayList<Player> p_players) {
        d_players = p_players;
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
        System.out.println("Main game loop has Started!");
        while (true) {

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

}
