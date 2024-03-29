package com.fsociety.warzone.asset.order;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;

import java.io.Serializable;

/**
 * This class handles everything related to Attacking
 */
public abstract class Attack implements Order {

    /**
     * The number of troops
     */
    protected int d_troopsCount;
    /**
     * Country ID of the source country
     */
    protected int d_sourceCountryId;
    /**
     * Country ID of the target country
     */
    protected int d_targetCountryId;
    /**
     * The ID of the player
     */
    protected int d_playerId;
    /**
     * The flag to know whether this class is the Advance class
     */
    private final boolean d_isAdvance;

    /**
     * Parameterised constructor for the abstract Attack order called by its inheritors.
     * @param p_sourceCountryId the source country
     * @param p_targetCountryId the target country
     * @param p_troopsCount the number of troops to be moved
     * @param p_playerId the ID of the player issuing the order
     * @param p_advance whether the order will be an Advance order or an Airlift order (for printing)
     */
    public Attack(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount, int p_playerId, boolean p_advance) {
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
        this.d_troopsCount = p_troopsCount;
        this.d_playerId = p_playerId;
        this.d_isAdvance = p_advance;
    }

    /**
     * This method implements attacking as per the Warzone rules and updates the map accordingly.
     * We assume all killed troops die after the battle is over.
     */
    @Override
    public void execute() {
        // If players are not in a truce (no Diplomacy card has been played)
        if (GameplayController.getTruces().get(d_playerId).contains(GameplayController.getPlayMap().getCountryState(d_targetCountryId).getPlayerId())) {
            return;
        }

        int l_currTroops = GameplayController.getPlayMap().getCountryState(d_sourceCountryId).getArmies();

        // If the player owns the source country and has enough troops to attack
        if (!(GameplayController.getPlayMap().getCountryState(d_sourceCountryId).getPlayerId() == d_playerId && l_currTroops > 0)) {
            return;
        }

        int l_advancingTroops = Math.min(d_troopsCount, l_currTroops);
        GameplayController.getPlayMap().updateCountry(d_sourceCountryId, l_currTroops - l_advancingTroops);
        String l_outcome;

        // If the player owns the target country, just advance troops
        // Otherwise, a battle takes place
        if (GameplayController.getPlayMap().getCountryState(d_targetCountryId).getPlayerId() == d_playerId) {
            GameplayController.getPlayMap().updateCountry(d_targetCountryId, GameplayController.getPlayMap().getCountryState(d_targetCountryId).getArmies() + l_advancingTroops);
            if (d_isAdvance) {
                l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " advanced " + l_advancingTroops + " from " + d_sourceCountryId + " to " + d_targetCountryId + ".";
            } else {
                l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " airlifted " + l_advancingTroops + " from " + d_sourceCountryId + " to " + d_targetCountryId + ".";
            }
        } else {
            int l_enemyTroops = GameplayController.getPlayMap().getCountryState(d_targetCountryId).getArmies();
            int l_kills = 0; // Number of enemies killed
            int l_enemyKills = 0; // Number of own troops killed
            for (int i = 0; i < l_enemyTroops; i++) {
                if (Math.random() < 0.7) {
                    l_enemyKills++;
                }
            }
            for (int i = 0; i < l_advancingTroops; i++) {
                if (Math.random() < 0.6) {
                    l_kills++;
                }
            }
            l_enemyTroops -= l_kills;
            l_enemyTroops = Math.max(l_enemyTroops, 0);
            l_advancingTroops -= l_enemyKills;
            l_advancingTroops = Math.max(l_advancingTroops, 0);
            // If the enemy army is vanquished, move remaining troops to the conquered country
            // Otherwise, remaining troops return to the source country
            if (l_enemyTroops == 0) {
                GameplayController.getPlayMap().conquerCountry(d_targetCountryId, d_playerId, l_advancingTroops);
                l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " conquered " + d_targetCountryId + " and has stationed " + l_advancingTroops + " armies there.";
                String l_card = GameplayController.getPlayerFromId(d_playerId).drawCard(); // Draw a card since the player conquered a country
                if (null != l_card) {
                    l_outcome += "\n" + l_card;
                }
            } else {
                GameplayController.getPlayMap().updateCountry(d_targetCountryId, l_enemyTroops);
                GameplayController.getPlayMap().updateCountry(d_sourceCountryId, GameplayController.getPlayMap().getCountryState(d_sourceCountryId).getArmies() + l_advancingTroops);
                l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " failed to conquer " + d_targetCountryId + ". " + l_advancingTroops + " troops returning home.";
            }
        }
        Console.print(l_outcome,true);
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
