package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

public class ExecuteOrder {

    public static void execute_orders(ArrayList<Player> p_players) {
        int l_total_orders = 0;
        for (int i = 0; i < p_players.size(); i++) {
            l_total_orders += p_players.get(i).get_orders_count();
        }
        while (l_total_orders > 0) {
            for (int i = 0; i < p_players.size(); i++) {
                if (p_players.get(i).get_orders_count() > 0) {
                    p_players.get(i).next_order();
                }
            }
            l_total_orders = 0;
            for (int i = 0; i < p_players.size(); i++) {
                l_total_orders += p_players.get(i).get_orders_count();
            }
        }
    }

}
