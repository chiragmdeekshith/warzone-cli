package com.fsociety.warzone.asset.order;

/**
 * This class handles everything related to Advancing armies
 */
public class Advance extends Attack {

    private final int d_troopsCount;
    private final int d_sourceCountryId;
    private final int d_targetCountryId;
    private final int d_playerId;

    public Advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount, int p_playerId) {
        super(p_sourceCountryId, p_targetCountryId, p_troopsCount, p_playerId, true);
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
        this.d_troopsCount = p_troopsCount;
        this.d_playerId = p_playerId;
    }

    /**
     * This method implements the Advance command as per the Warzone rules and updates the map accordingly: troops are
     * moved from one country to a neighbouring country, and attack the target country if it is owned by another player.
     * The Advance command will go through as long as the source country still belongs to the player,
     * and it still has troops.
     */
    @Override
    public void execute() {
        super.execute();
    }
}
