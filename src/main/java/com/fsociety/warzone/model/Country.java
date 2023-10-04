package com.fsociety.warzone.model;

import java.util.ArrayList;

/**
 * A POJO class which represents the Country in a map.
 */
public class Country {

    private int d_playerId;

    private Player d_player;

    private int d_armies;

    private final int d_countryId;

    // Getters and Setters
    public int getPlayerId() {
        return this.d_playerId;
    }
    public Player getPlayer() { return this.d_player; }
    public void setPlayer(Player p_player) { this.d_player = p_player; }

    public int getArmies() {
        return this.d_armies;
    }

    public int getCountryId() { return this.d_countryId; }

    public void setPlayerId(final int player) { this.d_playerId = player; }

    public void setArmies(final int armies) { this.d_armies = armies; }

    public Country(int p_countryId) {
        this.d_countryId = p_countryId;
        this.d_playerId = -1;
        this.d_player = null;
        this.d_armies = 0;
    }

    /**
     * Print the country object
     *
     * @param countries - list of countries
     * @return printedCountries - Countried to be printed
     */
    public static String printCountries(ArrayList<Country> countries) {
        StringBuilder printedCountries = new StringBuilder();
        if(countries != null && !countries.isEmpty()) {
            for (int i = 0; i < countries.size(); i++ ) {
                printedCountries.append(countries.get(i).d_countryId);
                if(i!=countries.size()-1){
                    printedCountries.append(", ");}
            }
        }
        return printedCountries.toString();
    }
}
