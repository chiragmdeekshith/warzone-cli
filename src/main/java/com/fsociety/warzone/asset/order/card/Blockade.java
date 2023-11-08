package com.fsociety.warzone.asset.order.card;
import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;

/**
 * This class handles everything related to Blockade cards
 */
public class Blockade implements Order {

    private final int d_countryId;
    private final int d_playerId;

    /**
     * Parameterised constructor to initialise a Blockade order.
     * @param p_countryId the country to be blockaded
     * @param p_playerId the ID of the player issuing the order
     */
    public Blockade (int p_countryId , int p_playerId) {
        this.d_playerId = p_playerId;
        this.d_countryId = p_countryId;
    }

    /**
     * This method implements the Blockade card as per the Warzone rules and updates the map accordingly: troops on
     * the target country are tripled.
     * The Blockade command will go through as long as the source country still belongs to the player
     * and the country still has troops.
     */
    @Override
    public void execute() {
        if (GameplayController.getPlayMap().getCountryState(d_countryId).getPlayerId() == d_playerId &&
                GameplayController.getPlayMap().getCountryState(d_countryId).getArmies() > 0) {
            int l_troopsCount = GameplayController.getPlayMap().getCountryState(d_countryId).getArmies();
            GameplayController.getPlayMap().updateCountry(d_countryId, l_troopsCount * 3);
            String l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " blockaded " + d_countryId + ".";
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