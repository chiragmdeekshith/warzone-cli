package com.fsociety.warzone.game.order;

import com.fsociety.warzone.game.GameEngine;

public class Deploy implements IOrder {

    private int l_troopsCount;
    private int l_countryId;
    private int l_playerId;

    public Deploy(int p_countryId, int p_troopsCount, int p_playerId) {
        this.l_countryId = p_countryId;
        this.l_troopsCount = p_troopsCount;
        this.l_playerId = p_playerId;
    }

    /**
     * This method implements the Deploy command as per the Warzone rules and updates the map accordingly.
     */
    @Override
    public void execute() {
        GameEngine.getWZMap().updateGameState(l_countryId, l_playerId, l_troopsCount);
        System.out.println(GameEngine.getPlayerList().get(l_playerId).getName() + " deployed " + l_troopsCount + " reinforcements to " + l_countryId);
    }
}
