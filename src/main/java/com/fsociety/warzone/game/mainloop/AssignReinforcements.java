package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Order;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements the assignReinforcements() method.
 */
public class AssignReinforcements {

    /**
     * This method evaluates the reinforcements to be assigned to a given player at the beginning of a turn. The
     * base reinforcement armies are set to the number of countries held divided by 3, and then continent bonus armies
     * are assigned to the player if they control all the countries of a given continent. This method calls the
     * getContinentOwner() method of the Continent class to compare each continent's owner to the given player.
     *
     * @param p_players the list of Player object whose reinforcements are to be calculated
     */
    public static void assignReinforcements(ArrayList<Player> p_players) {

        for (Player l_player : p_players) {

            int l_base = Math.max(3, l_player.getCountriesCount() / 3);

            AtomicInteger l_reinforcements = new AtomicInteger(l_base); // Base reinforcements

            Map<Integer, Continent> l_continents = GameEngine.getPlayMap().getContinents();
            l_continents.keySet().forEach(continentId -> {
                if (l_continents.get(continentId).getContinentOwner() != null && l_player.equals(l_continents.get(continentId).getContinentOwner())) {
                    l_reinforcements.addAndGet(l_continents.get(continentId).getArmiesBonus());
                }
            });
            l_player.setAvailableReinforcements(l_reinforcements.get());
            Console.print("Player " + l_player.getName() + " gets " + l_reinforcements + " reinforcement armies this turn.");
        }

        // Issue and Execute the Deploy orders before moving to the attack phase, where are other orders are processed
        issueDeployOrder(p_players);
        executeDeployOrders(p_players);
    }

    /**
     * This method ensures that all Players have deployed their available reinforcements before proceeding to the
     * attack phase. Each player is called to deploy in round-robin fashion unless they have no available
     * reinforcements.
     *
     * @param p_players the list of players of the game
     */
    public static void issueDeployOrder(ArrayList<Player> p_players) {
        int l_total_troops = 0;
        for (Player l_player : p_players) {
            l_total_troops += l_player.getAvailableReinforcements();
        }
        while (l_total_troops > 0) {
            for (Player l_player : p_players) {
                IssueOrder.setCurrentPlayer(l_player);
                if (l_player.getAvailableReinforcements() > 0) {
                    Console.print(l_player.getName() + ": You have " + l_player.getAvailableReinforcements() + " available reinforcements.");
                    l_player.issueOrder();
                }
            }
            l_total_troops = 0;
            for (Player l_player : p_players) {
                l_total_troops += l_player.getAvailableReinforcements();
            }
        }
    }

    /**
     * This method ensures that all Players have deployed their available reinforcements before proceeding to the
     * attack phase. Each player is called to deploy in round-robin fashion unless they have no available
     * reinforcements.
     *
     * @param p_players the list of players of the game
     */
    public static void executeDeployOrders(ArrayList<Player> p_players) {
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
            l_order.execute();
        }
    }

}
