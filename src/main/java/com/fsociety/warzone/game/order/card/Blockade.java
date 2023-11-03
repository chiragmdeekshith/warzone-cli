package com.fsociety.warzone.game.order.card;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Order;

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
        if (GameEngine.getPlayMap().getCountryState(d_countryId).getPlayerId() == d_playerId && GameEngine.getPlayMap().getCountryState(d_countryId).getArmies() > 0) {
            int l_troopsCount = GameEngine.getPlayMap().getCountryState(d_countryId).getArmies();
            GameEngine.getPlayMap().updateGameState(d_countryId, l_troopsCount * 3);
            System.out.println(GameEngine.getPlayerList().get(d_playerId).getName() + " blockaded " + d_countryId + ".");
        }
    }
}