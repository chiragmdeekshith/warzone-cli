package com.fsociety.warzone.models.order;

import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.models.map.PlayMap;

/**
 * This class handles everything related to Deploying armies
 */
public class Deploy implements IOrder {

    private final int d_troopsCount;
    private final int d_countryId;
    private final int d_playerId;

    public Deploy(int p_countryId, int p_troopsCount, int p_playerId) {
        this.d_countryId = p_countryId;
        this.d_troopsCount = p_troopsCount;
        this.d_playerId = p_playerId;
    }

    /**
     * This method implements the Deploy command as per the Warzone rules and updates the map accordingly.
     */
    @Override
    public void execute() {
        //PlayMap.getPlayMap().updateGameState(d_countryId, d_playerId, d_troopsCount);
        System.out.println(PlayMap.getPlayerList().get(d_playerId).getName() + " deployed " + d_troopsCount + " reinforcements to " + d_countryId);
    }
}
