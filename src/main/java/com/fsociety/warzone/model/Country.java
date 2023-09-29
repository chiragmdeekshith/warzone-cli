package com.fsociety.warzone.model;

public class Country {

    private int d_player;

    private int d_armies;

    // Getters and Setters

    public int getPlayer() {
        return d_player;
    }

    public int getArmies() {
        return d_armies;
    }

    public Country setPlayer(final int player) {
        this.d_player = player;
        return this;
    }

    public Country setArmies(final int armies) {
        this.d_armies = armies;
        return this;
    }

    public Country() {
        this.d_player = -1;
        this.d_armies = 0;
    }

}
