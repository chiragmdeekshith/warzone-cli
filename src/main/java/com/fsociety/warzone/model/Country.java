package com.fsociety.warzone.model;


import com.fsociety.warzone.model.player.Player;

import java.io.Serializable;

/**
 * A POJO class which represents the Country in a map.
 */
public class Country implements Serializable {

    private int d_playerId;

    private Player d_player;

    private int d_armies;

    private final int d_countryId;


    // Getters and Setters
    /**
     * Get the ID of the owner of the country.
     *
     * @return the ID of the country owner
     */
    public int getPlayerId() {
        return this.d_playerId;
    }

    /**
     * Get the owner of the country.
     *
     * @return the country owner
     */
    public Player getPlayer() { return this.d_player; }

    /**
     * Set the owner of the country.
     * @param p_player the new owner of the country
     */
    public void setPlayer(Player p_player) { this.d_player = p_player; }

    /**
     * Get the number of troops stationed on the country.
     *
     * @return the number of troops
     */
    public int getArmies() {
        return this.d_armies;
    }

    /**
     * Get the country ID.
     *
     * @return the country ID
     */
    public int getCountryId() { return this.d_countryId; }

    /**
     * Set the ID of the owner of the country.
     *
     * @param p_player the new owner of the country
     */
    public void setPlayerId(int p_player) { this.d_playerId = p_player; }

    /**
     * Set the number of troops stationed on the country.
     *
     * @param p_armies the number of troops
     */
    public void setArmies(int p_armies) { this.d_armies = p_armies; }

    /**
     * Creates a new object of type Country
     *
     * @param p_countryId - the country ID
     */
    public Country(int p_countryId) {
        this.d_countryId = p_countryId;
        this.d_playerId = -1;
        this.d_player = null;
        this.d_armies = 0;
    }
}
