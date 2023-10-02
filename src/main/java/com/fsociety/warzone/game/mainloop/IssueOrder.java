package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.model.Player;

import java.util.ArrayList;

public class IssueOrder {

    public static boolean issue_orders(ArrayList<Player> p_players) {
        int l_total_troops = 0;
        for (int i = 0; i < p_players.size(); i++) {
            l_total_troops += p_players.get(i).getTroops();
            System.out.println("TESTING IssueOrder: " + l_total_troops);
        }
        while (l_total_troops > 0) {
            for (int i = 0; i < p_players.size(); i++) {
                if (p_players.get(i).getTroops() > 0) {
                    if (!p_players.get(i).issue_order()) {
                        return false;
                    }
                }
            }
            System.out.println("TESTING IssueOrder: orders issued");
            for (int i = 0; i < p_players.size(); i++) {
                l_total_troops = 0;
                l_total_troops += p_players.get(i).getTroops();
            }
            System.out.println("TESTING IssueOrder: " + l_total_troops);
        }
        return true;
    }

}
