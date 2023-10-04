package com.fsociety.warzone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is not a physical continent but a representation used for purely calculating the bonus armies for players
 * if they own all the countries in the particular continent.
 */
public class Continent {
    // ids of players -> Set of ids of countries
    private final Map<Integer, Integer> d_playerCountryCountMap;

    private final ArrayList<Country> d_countries;
    private final int d_armiesBonus;
    private Player d_continentOwner;

    /**
     * Creates a new object of type Continent
     *
     * @param p_countryIds - list of country IDs
     * @param p_countryGameStateMap - the country id -> game state of country map
     * @param p_bonus - bonus armies
     */
    public Continent(Set<Integer> p_countryIds, Map<Integer, Country> p_countryGameStateMap, int p_bonus) {
        this.d_armiesBonus = p_bonus;
        this.d_countries = new ArrayList<>();
        p_countryIds.forEach(d_countryId -> {
            this.d_countries.add(p_countryGameStateMap.get(d_countryId));
        });
        this.d_playerCountryCountMap = new HashMap<>();
    }

    /**
     *  Initialize the country count with player ID
     *
     * @param p_playerId - The player ID
     */
    public void initCountryCount(final int p_playerId) {
        this.d_playerCountryCountMap.put(p_playerId, 1);
    }

    /**
     *  Update the country count.
     *
     * @param p_currentPlayerId - the ID of current player
     * @param p_newPlayerId - the ID of the new player
     */
    public void updateCountryCount(
            final int p_currentPlayerId,
            final int p_newPlayerId) {
        this.d_playerCountryCountMap.put(p_currentPlayerId, this.d_playerCountryCountMap.get(p_currentPlayerId) - 1);
        if (this.d_playerCountryCountMap.get(p_currentPlayerId) == 0) {
            this.d_playerCountryCountMap.remove(p_currentPlayerId);
        }
        this.d_playerCountryCountMap.put(p_newPlayerId, this.d_playerCountryCountMap.get(p_newPlayerId) + 1);
    }

    /**
     * This method computes and stores the Player object of the player that owns every country in the continent,
     * if that player exists.
     */
    public void setContinentOwner() {
        Player l_computedContinentOwner = d_countries.get(0).getPlayer();
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

    /**
     *  Print the Continent values
     *
     * @param continentId - the continent ID
     * @param continent - The continent object
     */
    public void printContinent(int continentId, Continent continent) {
        if(d_playerCountryCountMap != null && !d_playerCountryCountMap.isEmpty()) {
            System.out.println(continentId + ": " + continent.d_armiesBonus + " - [" + Country.printCountries(continent.d_countries) + "]");
        }
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
}
