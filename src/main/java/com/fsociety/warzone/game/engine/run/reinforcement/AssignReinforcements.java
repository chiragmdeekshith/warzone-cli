package com.fsociety.warzone.game.engine.run.reinforcement;

import com.fsociety.warzone.model.Player;

public class AssignReinforcements {

    public static void assign_reinforcements(Player p_player) {

        p_player.set_troops(Math.floorDiv(p_player.get_countries_count(), 3));

        /**
         * @TODO Assign bonus reinforcements based on continents owned
         */

    }

}
