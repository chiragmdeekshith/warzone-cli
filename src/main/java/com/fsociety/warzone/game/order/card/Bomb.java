package com.fsociety.warzone.game.order.card;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Order;

/**
 * This class handles everything related to Bomb cards
 */
public class Bomb implements Order {

    private final int d_sourceCountryId;
    private final int d_targetCountryId;
    private final int d_playerId;

    public Bomb (int p_sourceCountryId, int p_targetCountryId, int p_playerId) {
        this.d_playerId = p_playerId;
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
    }

    /**
     * This method implements the Bomb card as per the Warzone rules and updates the map accordingly: the player bombs
     * a neighbouring enemy country and destroys half of its armies.
     * The Bomb command will go through as long as the source country still belongs to the attacking player
     * and the target country still belongs to the defending player.
     */
    @Override
    public void execute() {
        if (GameEngine.getPlayMap().getCountryState(d_sourceCountryId).getPlayerId() == d_playerId && GameEngine.getPlayMap().getCountryState(d_targetCountryId).getPlayerId() != d_playerId) {
            int l_troopsCount = GameEngine.getPlayMap().getCountryState(d_targetCountryId).getArmies();
            GameEngine.getPlayMap().updateGameState(d_targetCountryId, l_troopsCount/2);
            System.out.println(GameEngine.getPlayerList().get(d_playerId).getName() + " bombed " + d_targetCountryId + " and destroyed " + (l_troopsCount - l_troopsCount/2) + " armies.");
        }
    }

}
