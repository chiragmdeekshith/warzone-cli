package com.fsociety.warzone.game;

import com.fsociety.warzone.game.mainloop.ExecuteOrder;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.game.startup.StartUp;
import com.fsociety.warzone.map.play.PlayMap;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

public class GameEngine {

    private static ArrayList<Player> l_Players;
    private static PlayMap l_Map;

    public static void set_players(ArrayList<Player> p_players) {
        l_Players = p_players;
    }

    public static void set_map(PlayMap p_map) {
        l_Map = p_map;
    }

    public static void play_game() {
        if(!StartUp.start_up()){
            return;
        }
        main_loop();
    }

    public static void main_loop() {
        System.out.println("Main game loop has Started!");
        while (true) {

            // Assign Reinforcements Phase
            for (int i = 0; i < l_Players.size(); i++) {
                AssignReinforcements.assign_reinforcements(l_Players.get(i));
            }

            // Issue Orders Phase
            IssueOrder.issue_orders(l_Players);

            // Execute Orders Phase
            ExecuteOrder.execute_orders(l_Players);
        }

    }

    /**
     * @TODO Implement the "show map" function to work at any point during gameplay
     */

}
