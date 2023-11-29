package com.fsociety.warzone.asset.order;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;

import java.io.Serializable;

/**
 * This class handles everything related to Deploying armies
 */
public class Deploy implements Order {
    /**
     * The troops count
     */
    private final int d_troopsCount;
    /**
     * The country ID
     */
    private final int d_countryId;
    /**
     * The player ID
     */
    private final int d_playerId;

    /**
     * Parameterised constructor to initialise a Deploy order.
     * @param p_countryId the country to be deployed to
     * @param p_troopsCount the number of troops to be deployed
     * @param p_playerId the ID of the player issuing the order
     */
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
        if (GameplayController.getPlayMap().getCountryState(d_countryId).getPlayerId() == d_playerId) {
            int l_updatedTroopCount = d_troopsCount +
                    GameplayController.getPlayMap().getCountryState(d_countryId).getArmies();
            GameplayController.getPlayMap().conquerCountry(d_countryId, d_playerId, l_updatedTroopCount);
            String l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " deployed " + d_troopsCount +
                    " reinforcements to " + d_countryId + ".";
            Console.print(l_outcome,true);
        }
    }

    /**
     * This function returns the ID of the player who issued the order.
     * @return The player ID
     */
    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}
