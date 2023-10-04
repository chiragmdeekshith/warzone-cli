package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

/**
 * This class implements the executeOrders() method.
 */
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
        for (Player pPlayer : p_players) {
            l_totalOrders += pPlayer.getOrdersCount();
        }
        while (l_totalOrders > 0) {
            for (Player pPlayer : p_players) {
                if (pPlayer.getOrdersCount() > 0) {
                    pPlayer.nextOrder();
                }
            }
            l_totalOrders = 0;
            for (Player pPlayer : p_players) {
                l_totalOrders += pPlayer.getOrdersCount();
            }
        }
    }
}
