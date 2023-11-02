package com.fsociety.warzone.game.order;

/**
 * This class handles everything related to Advancing armies
 */
public class Advance implements Order {

    private final int d_troopsCount;
    private final int d_countryId;
    private final int d_playerId;

    public Advance(int p_countryId, int p_troopsCount, int p_playerId) {
        this.d_countryId = p_countryId;
        this.d_troopsCount = p_troopsCount;
        this.d_playerId = p_playerId;
    }

    /**
     * This method implements the Advance command as per the Warzone rules and updates the map accordingly.
     */
    @Override
    public void execute() {

    }
}
