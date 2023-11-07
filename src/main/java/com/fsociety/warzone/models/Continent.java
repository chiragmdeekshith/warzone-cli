package com.fsociety.warzone.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the continent objects used to calculate the bonus armies for players,
 * if they own all the countries in the particular continent.
 */
public class Continent {

    private final int d_continentId;
    private final List<Country> d_countries;
    private final int d_armiesBonus;
    private Player d_continentOwner;

    /**
     * Creates a new object of type Continent
     *
     * @param p_countryIds - list of country IDs
     * @param p_countries - the country id -> game state of country map
     * @param p_bonus - bonus armies
     */
    public Continent(int p_continentId, Set<Integer> p_countryIds, Map<Integer, Country> p_countries, int p_bonus) {
        this.d_continentId = p_continentId;
        this.d_armiesBonus = p_bonus;
        this.d_countries = new ArrayList<>();
        p_countryIds.forEach(d_countryId -> this.d_countries.add(p_countries.get(d_countryId)));
    }

    /**
     * This method computes and stores the Player object of the player that owns every country in the continent,
     * if that player exists.
     */
    public void computeAndSetContinentOwner() {
        Player l_computedContinentOwner = d_countries.get(0).getPlayer();
        // Perform this computation only if the country is owned by a player
        if (l_computedContinentOwner != null) {
            for (int i = 1; i < d_countries.size(); i++) {
                if (!l_computedContinentOwner.equals(d_countries.get(i).getPlayer())) {
                    l_computedContinentOwner = null;
                    break;
                }
            }
        }
        this.d_continentOwner = l_computedContinentOwner;
    }

    // Getters and Setters

    /**
     * Get the bonus armies value
     * @return - the bonus armies value for the continent
     */
    public int getArmiesBonus() {
        return this.d_armiesBonus;
    }

    /**
     * Get the owner of the continent.
     *
     * @return the continent owner
     */
    public Player getContinentOwner() {
        return this.d_continentOwner;
    }

    /**
     * Get the continent id
     * @return the continent id
     */
    public int getContinentId() {
        return d_continentId;
    }

    @Override
    public String toString() {
        StringBuilder l_toPrint = new StringBuilder(d_continentId + ": " + this.d_armiesBonus + " - [");
        for(int l_i = 0; l_i < d_countries.size(); l_i++) {
            l_toPrint.append(d_countries.get(l_i).getCountryId());
            if(l_i != d_countries.size()-1){
                l_toPrint.append(", ");
            }
        }
        l_toPrint.append("]");
        return l_toPrint.toString();
    }
}
