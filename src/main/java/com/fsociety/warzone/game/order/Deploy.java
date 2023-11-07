package com.fsociety.warzone.game.order;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.util.Console;

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
        if (GameEngine.getPlayMap().getCountryState(d_countryId).getPlayerId() == d_playerId) {
            GameEngine.getPlayMap().updateGameState(d_countryId, d_playerId, d_troopsCount);
            String l_outcome = GameEngine.getPlayerNameFromId(d_playerId) + " deployed " + d_troopsCount + " reinforcements to " + d_countryId + ".";
            Console.print(l_outcome);
        }
    }

    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}
