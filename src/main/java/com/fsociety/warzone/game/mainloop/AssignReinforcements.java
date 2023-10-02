package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.model.Player;

public class AssignReinforcements {

    public static void assign_reinforcements(Player p_player) {

        int l_troops = Math.floorDiv(p_player.get_countries_count(), 3); // Base reinforcements

        /**
         * @TODO Assign bonus reinforcements based on continents owned
         */

        p_player.set_troops(l_troops);
        System.out.println("Player " + p_player.getName() + " gets " + l_troops + " reinforcement armies.");
    }

}
