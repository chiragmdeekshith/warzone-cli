package com.fsociety.warzone.model;

public class Country {

    private int d_player;

    private int d_armies;

    private final int d_countryId;

    private final int d_continentId;

    // Getters and Setters

    public int getPlayer() {
        return this.d_player;
    }

    public int getArmies() {
        return this.d_armies;
    }

    public int getCountryId() { return this.d_countryId; }

    public int getContinentId() { return this.d_continentId; }

    public Country setPlayer(final int player) {
        this.d_player = player;
        return this;
    }

    public Country setArmies(final int armies) {
        this.d_armies = armies;
        return this;
    }

    public Country(int p_countryId, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_continentId = p_continentId;
        this.d_player = -1;
        this.d_armies = 0;
    }

}
