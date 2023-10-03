package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AssignReinforcements {

    public static void assign_reinforcements(Player p_player) {

        AtomicInteger l_reinforcements = new AtomicInteger(5); // Base reinforcements

        Map<Integer, Continent> d_continents = GameEngine.getWZMap().getContinents();
        d_continents.keySet().forEach(continentId -> {
            if (d_continents.get(continentId).getContinentOwner() != null && p_player.equals(d_continents.get(continentId).getContinentOwner())) {
                l_reinforcements.addAndGet(d_continents.get(continentId).getArmiesBonus());
            }
        });

        p_player.setTroops(l_reinforcements.get());
        System.out.println("Player " + p_player.getName() + " gets " + l_reinforcements + " reinforcement armies this turn.");
    }

}
