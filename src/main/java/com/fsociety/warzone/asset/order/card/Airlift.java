package com.fsociety.warzone.asset.order.card;

import com.fsociety.warzone.asset.order.Attack;

/**
 * This class handles everything related to Airlift cards
 */
public class Airlift extends Attack {

    private final int d_troopsCount;
    private final int d_sourceCountryId;
    private final int d_targetCountryId;
    private final int d_playerId;

    public Airlift (int p_sourceCountryId, int p_targetCountryId, int p_troopsCount, int p_playerId) {
        super(p_sourceCountryId, p_targetCountryId, p_troopsCount, p_playerId);
        this.d_playerId = p_playerId;
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
        this.d_troopsCount = p_troopsCount;
    }

    /**
     * This method implements the Airlift card as per the Warzone rules and updates the map accordingly: troops are
     * moved from one country to another, and attack the target country if it is owned by another player.
     * The Airlift command will go through as long as the source country still belongs to the player,
     * and it still has troops.
     */
    public void execute() {
        super.execute();
    }

}