package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

/**
 * This class implements the executeOrders() method.
 */
public class ExecuteOrder {

    /**
     * This method returns each order in each player's list of orders in round-robin fashion until no orders are left
     * on any player's list.
     *
     * @param p_players the list of players of the game
     */
    public static void executeOrders(ArrayList<Player> p_players) {

        int l_totalOrders = 0;
        for (Player p_player : p_players) {
            l_totalOrders += p_player.getOrdersCount();
        }

        ArrayList<Order> l_orders = new ArrayList<>();

        while (l_totalOrders > 0) {
            for (Player p_player : p_players) {
                Order l_currentOrder = p_player.nextOrder();
                if (l_currentOrder != null) {
                    l_orders.add(l_currentOrder);
                    l_totalOrders--;
                }
            }
        }

        for (Order l_order : l_orders) {
            if (!GameplayController.getPlayerNameMap().get(GameplayController.getPlayerNameFromId(l_order.getIssuerId())).isEliminated()) {
                l_order.execute();
                if (GameplayController.checkWinCondition()) {
                    GameplayController.setGameWon();
                    return;
                }
            }
        }
    }

}
