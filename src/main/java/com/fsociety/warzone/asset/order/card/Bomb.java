package com.fsociety.warzone.asset.order.card;
import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;

/**
 * This class handles everything related to Bomb cards
 */
public class Bomb implements Order {

    private final int d_targetCountryId;
    private final int d_playerId;

    public Bomb (int p_targetCountryId, int p_playerId) {
        this.d_playerId = p_playerId;
        this.d_targetCountryId = p_targetCountryId;
    }

    /**
     * This method implements the Bomb card as per the Warzone rules and updates the map accordingly: the player bombs
     * a neighbouring enemy country and destroys half of its armies.
     * The Bomb command will go through as long as the target country does not belong to the attacking player and it
     * still borders a country belonging to the attacking player.
     */
    @Override
    public void execute() {
        if (GameplayController.getPlayMap().isNeighbourOf(d_targetCountryId, d_playerId) && GameplayController.getPlayMap().getCountryState(d_targetCountryId).getPlayerId() != d_playerId) {
            int l_troopsCount = GameplayController.getPlayMap().getCountryState(d_targetCountryId).getArmies();
            GameplayController.getPlayMap().updateCountry(d_targetCountryId, l_troopsCount/2);
            String l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " bombed " + d_targetCountryId + " and destroyed " + (l_troopsCount - l_troopsCount/2) + " armies.";
            Console.print(l_outcome,true);
        }
    }

    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}
