package com.warzone.model;

import com.warzone.util.IdGenerator;

public class Country {

    private final Long id;

    private final String name;

    private final String continent;

    private int playerId;

    private int armies;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getArmies() {
        return armies;
    }

    public void setPlayer(final int playerId) {
        this.playerId = playerId;
    }

    public void setArmies(final int armies) {
        this.armies = armies;
    }

    public Country(final String name, final String continent) {
        this.id = IdGenerator.generateId();
        this.name = name;
        this.continent = continent;
        this.playerId = -1;
        this.armies = 0;
    }

}
