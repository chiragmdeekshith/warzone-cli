package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

/**
 * This class implements the issueOrders() method.
 */
public class IssueOrder {

    /**
     * This method calls the issueOrder() method of each player in round-robin fashion until no player has any
     * reinforcement armies left to deploy. This is done by keeping count of the total number of available
     * reinforcement armies that all players have. If a player has no available reinforcements, they are skipped.
     *
     * @param p_players the list of players of the game
     * @return returns false if a player enters the 'back' command within the issueOrder() method of the Player class.
     *         This causes the game to return to the main menu.
     */
    public static boolean issueOrders(ArrayList<Player> p_players) {
        int l_totalReinforcements = 0;
        for (Player pPlayer : p_players) {
            l_totalReinforcements += pPlayer.getAvailableReinforcements();
        }
        while (l_totalReinforcements > 0) {
            for (Player pPlayer : p_players) {
                if (pPlayer.getAvailableReinforcements() > 0) {
                    if (!pPlayer.issueOrder()) {
                        return false;
                    }
                }
            }
            l_totalReinforcements = 0;
            for (Player pPlayer : p_players) {
                l_totalReinforcements += pPlayer.getAvailableReinforcements();
            }
        }
        return true;
    }
}
