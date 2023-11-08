package com.fsociety.warzone.models.order.card;
import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.models.order.Order;
import com.fsociety.warzone.utils.Console;

/**
 * This class handles everything related to Blockade cards
 */
public class Blockade implements Order {

    private final int d_countryId;
    private final int d_playerId;

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
        if (GameEngineController.getPlayMap().getCountryState(d_countryId).getPlayerId() == d_playerId && GameEngineController.getPlayMap().getCountryState(d_countryId).getArmies() > 0) {
            int l_troopsCount = GameEngineController.getPlayMap().getCountryState(d_countryId).getArmies();
            GameEngineController.getPlayMap().updateGameState(d_countryId, l_troopsCount * 3);
            String l_outcome = GameEngineController.getPlayerNameFromId(d_playerId) + " blockaded " + d_countryId + ".";
            Console.print(l_outcome);
        }
    }

    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}