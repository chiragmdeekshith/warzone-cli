package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the Issue Orders phase of the gameplay, players are prompted to issue orders.
 */
public class IssueOrder {

    public static HashMap<Integer, Integer> d_availableTroopsOnMap;
    private static Player d_currentPlayer;

    /**
     * This method calls the issueOrder() method of each player in round-robin fashion until all players have committed
     * their orders. If a player has committed their orders, they are skipped.
     *
     * @param p_players the list of players of the game
     */
    public static void issueOrders(ArrayList<Player> p_players) {

        d_availableTroopsOnMap = new HashMap<>();
        for (Country l_country : GameplayController.getPlayMap().getCountries().values()) {
            d_availableTroopsOnMap.put(l_country.getCountryId(), l_country.getArmies());
        }

        while (true) {
            int l_committed = 0;
            for (Player l_player : p_players) {
                d_currentPlayer = l_player;
                if (l_player.hasCommitted()) {
                    l_committed++;
                } else {
                    Console.print(l_player.getName() + ": ");
                    l_player.issueOrder();
                }
            }
            if (l_committed == p_players.size()) {
                break;
            }
        }
    }

    /**
     * This method prints the troops available per country for a player during gameplay. Since the map is only updated
     * after order execution, this allows players to see their troops on the map that they have not yet advanced.
     */
    public static void showAvailableTroops(Player p_player) {
        StringBuilder l_output = new StringBuilder(p_player.getName() + "'s Available Armies: \n");
        for (int l_countryId : p_player.getCountryIds()) {
            l_output.append("Country ")
                    .append(l_countryId)
                    .append(" has ")
                    .append(d_availableTroopsOnMap.get(l_countryId))
                    .append(" available armies.\n");
        }
        Console.print(l_output.toString());
    }

    /**
     * This method returns the Player object of the player whose turn it is.
     * @return The player whose turn it is
     */
    public static Player getCurrentPlayer() {
        return d_currentPlayer;
    }

    /**
     * This method allows the reference to the player whose turn it is to be updated as the turn progresses.
     * @param p_player The player whose turn it is
     */
    public static void setCurrentPlayer(Player p_player) {
        d_currentPlayer = p_player;
    }

}
