package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AssignReinforcements {

    /**
     * This method evaluates the reinforcements to be assigned to a given player at the beginning of a turn. The
     * base reinforcement armies are set to 5 as per the Warzone rules, and then continent bonus armies are assigned
     * to the player if they control all the countries of a given continent. This method calls the getContinentOwner()
     * method of the Continent class to compare each continent's owner to the given player.
     *
     * @param p_player the Player object whose reinforcements are to be calculated
     */
    public static void assignReinforcements(Player p_player) {

        AtomicInteger l_reinforcements = new AtomicInteger(5); // Base reinforcements

        Map<Integer, Continent> l_continents = GameEngine.getWZMap().getContinents();
        l_continents.keySet().forEach(continentId -> {
            if (l_continents.get(continentId).getContinentOwner() != null && p_player.equals(l_continents.get(continentId).getContinentOwner())) {
                l_reinforcements.addAndGet(l_continents.get(continentId).getArmiesBonus());
            }
        });
        p_player.setAvailableReinforcements(l_reinforcements.get());
        System.out.println("Player " + p_player.getName() + " gets " + l_reinforcements + " reinforcement armies this turn.");
    }
}
