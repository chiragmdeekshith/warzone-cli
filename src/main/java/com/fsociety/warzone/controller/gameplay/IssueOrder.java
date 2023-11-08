package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the issueOrders() method.
 */
public class IssueOrder {

    public static HashMap<Integer, Integer> d_availableTroopsOnMap;
    private static Player d_currentPlayer;

    /**
     * This method calls the issueOrder() method of each player in round-robin fashion until all players have committed
     * their orders. If a player has committed their orders, they are skipped.
     *
     * @param p_players the list of players of the game
     * @return returns false if a player enters the 'back' command within the issueOrder() method of the Player class.
     *         This causes the game to return to the main menu.
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

    public static Player getCurrentPlayer() {
        return d_currentPlayer;
    }

    public static void setCurrentPlayer(Player p_player) {
        d_currentPlayer = p_player;
    }

}
