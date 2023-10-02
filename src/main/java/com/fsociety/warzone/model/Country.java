package com.fsociety.warzone.model;

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

    public Country setPlayerId(final int player) {
        this.d_playerId = player;
        return this;
    }

    public Country setArmies(final int armies) {
        this.d_armies = armies;
        return this;
    }

    public Country(int p_countryId) {
        this.d_countryId = p_countryId;
        this.d_playerId = -1;
        this.d_player = null;
        this.d_armies = 0;
    }

}
