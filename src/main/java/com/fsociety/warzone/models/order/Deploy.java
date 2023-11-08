package com.fsociety.warzone.models.order;

import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.utils.Console;

/**
 * This class handles everything related to Deploying armies
 */
public class Deploy implements Order {

    private final int d_troopsCount;
    private final int d_countryId;
    private final int d_playerId;

    public Deploy(int p_countryId, int p_troopsCount, int p_playerId) {
        this.d_countryId = p_countryId;
        this.d_troopsCount = p_troopsCount;
        this.d_playerId = p_playerId;
    }

    /**
     * This method implements the Deploy command as per the Warzone rules and updates the map accordingly: troops are
     * deployed to a country owned by the player.
     * The Deploy command will go through as long as the country still belongs to the player.
     */
    @Override
    public void execute() {
        if (GameEngineController.getPlayMap().getCountryState(d_countryId).getPlayerId() == d_playerId) {
            int l_updatedTroopCount = d_troopsCount + GameEngineController.getPlayMap().getCountryState(d_countryId).getArmies();
            GameEngineController.getPlayMap().updateGameState(d_countryId, d_playerId, l_updatedTroopCount);
            String l_outcome = GameEngineController.getPlayerNameFromId(d_playerId) + " deployed " + d_troopsCount + " reinforcements to " + d_countryId + ".";
            Console.print(l_outcome);
        }
    }

    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}
