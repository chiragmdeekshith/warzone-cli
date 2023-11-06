package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;

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
    public static boolean issueOrders(ArrayList<Player> p_players) {

        d_availableTroopsOnMap = new HashMap<>();
        for (Country l_country : GameEngine.getPlayMap().getCountries().values()) {
            d_availableTroopsOnMap.put(l_country.getCountryId(), l_country.getArmies());
        }

        while (true) {
            int l_committed = 0;
            for (Player p_player : p_players) {
                d_currentPlayer = p_player;
                if (p_player.hasCommitted()) {
                    l_committed++;
                } else {
                    if (!p_player.issueOrder()) {
                        return false;
                    }
                }
            }
            if (l_committed == p_players.size()) {
                break;
            }
        }
        return true;
    }

    public static void showActiveTroops(Player p_player) {
        String l_output = p_player.getName() + "'s Available Armies: \n";
        for (int l_countryId : p_player.getCountryIds()) {
            l_output += "Country " + l_countryId + " has " + d_availableTroopsOnMap.get(l_countryId) + " available armies.\n";
        }
        Console.print(l_output);
    }

    public static Player getCurrentPlayer() {
        return d_currentPlayer;
    }

}
