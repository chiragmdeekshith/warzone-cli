package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.order.Order;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

/**
 * This class implements the issueOrders() method.
 */
public class IssueOrder {

    /**
     * This method calls the issueOrder() method of each player in round-robin fashion until all players have committed
     * their orders. If a player has committed their orders, they are skipped.
     *
     * @param p_players the list of players of the game
     * @return returns false if a player enters the 'back' command within the issueOrder() method of the Player class.
     *         This causes the game to return to the main menu.
     */
    public static boolean issueOrders(ArrayList<Player> p_players) {

        while (true) {
            int l_committed = 0;
            for (Player p_player : p_players) {
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
}
