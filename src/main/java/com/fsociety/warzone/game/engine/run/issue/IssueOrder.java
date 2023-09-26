package com.fsociety.warzone.game.engine.run.issue;

import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

public class IssueOrder {

    public static void issue_orders(ArrayList<Player> p_players) {
        int l_total_troops = 0;
        for (int i = 0; i < p_players.size(); i++) {
            l_total_troops += p_players.get(i).get_troops();
        }
        while (l_total_troops > 0) {
            for (int i = 0; i < p_players.size(); i++) {
                if (p_players.get(i).get_troops() > 0) {
                    p_players.get(i).issue_order();
                }
            }
            for (int i = 0; i < p_players.size(); i++) {
                l_total_troops += p_players.get(i).get_troops();
            }
        }
    }

}
