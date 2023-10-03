package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

public class ExecuteOrder {

    /**
     * This method calls the execute() method of each order in each player's list of orders in round-robin fashion
     * until no orders are left on any player's list. This is done by keeping count of the total number of pending
     * orders that all players have. If a player has no pending orders, they are skipped.
     *
     * @param p_players the list of players of the game
     */
    public static void executeOrders(ArrayList<Player> p_players) {
        int l_totalOrders = 0;
        for (int i = 0; i < p_players.size(); i++) {
            l_totalOrders += p_players.get(i).getOrdersCount();
        }
        while (l_totalOrders > 0) {
            for (int i = 0; i < p_players.size(); i++) {
                if (p_players.get(i).getOrdersCount() > 0) {
                    p_players.get(i).nextOrder();
                }
            }
            l_totalOrders = 0;
            for (int i = 0; i < p_players.size(); i++) {
                l_totalOrders += p_players.get(i).getOrdersCount();
            }
        }
    }
}
