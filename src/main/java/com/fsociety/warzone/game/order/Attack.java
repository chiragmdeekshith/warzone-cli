package com.fsociety.warzone.game.order;

import com.fsociety.warzone.game.GameEngine;

/**
 * This class handles everything related to Attacking
 */
public abstract class Attack implements Order {

    private final int d_troopsCount;
    private final int d_sourceCountryId;
    private final int d_targetCountryId;
    private final int d_playerId;

    public Attack(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount, int p_playerId) {
        this.d_sourceCountryId = p_sourceCountryId;
        this.d_targetCountryId = p_targetCountryId;
        this.d_troopsCount = p_troopsCount;
        this.d_playerId = p_playerId;
    }

    /**
     * This method implements attacking as per the Warzone rules and updates the map accordingly.
     * We assume all killed troops die after the battle is over.
     */
    @Override
    public void execute() {
        // If players are not in a truce (no Diplomacy card has been played)
        if (!GameEngine.getTruces().get(d_playerId).contains(GameEngine.getPlayMap().getCountryState(d_targetCountryId).getPlayerId())) {
            int l_currTroops = GameEngine.getPlayMap().getCountryState(d_sourceCountryId).getArmies();
            // If the player owns the source country and has enough troops to attack
            if (GameEngine.getPlayMap().getCountryState(d_sourceCountryId).getPlayerId() == d_playerId && l_currTroops > 0) {
                int l_advancingTroops = Math.min(d_troopsCount, l_currTroops);
                GameEngine.getPlayMap().updateGameState(d_sourceCountryId, l_currTroops - l_advancingTroops);
                String l_outcome;
                // If the player owns the target country, just advance troops
                if (GameEngine.getPlayMap().getCountryState(d_targetCountryId).getPlayerId() == d_playerId) {
                    GameEngine.getPlayMap().updateGameState(d_targetCountryId, GameEngine.getPlayMap().getCountryState(d_targetCountryId).getArmies() + l_advancingTroops);
                    l_outcome = GameEngine.getPlayerList().get(d_playerId).getName() + " advanced " + l_advancingTroops + " from " + d_sourceCountryId + " to " + d_targetCountryId + ".";
                // Otherwise a battle takes place
                } else {
                    int l_enemyTroops = GameEngine.getPlayMap().getCountryState(d_targetCountryId).getArmies();
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
                    l_advancingTroops -= l_enemyKills;
                    // If the enemy army is vanquished, move remaining troops to the conquered country
                    if (l_enemyTroops == 0) {
                        GameEngine.getPlayMap().updateGameState(d_targetCountryId, d_playerId, l_advancingTroops);
                        GameEngine.getPlayers().get(d_playerId).drawCard();
                        l_outcome = GameEngine.getPlayerList().get(d_playerId).getName() + " conquered " + d_targetCountryId + " and has stationed " + l_advancingTroops + " armies there.";
                    // Otherwise, remaining troops return to the source country
                    } else {
                        GameEngine.getPlayMap().updateGameState(d_targetCountryId, l_enemyTroops);
                        GameEngine.getPlayMap().updateGameState(d_sourceCountryId, GameEngine.getPlayMap().getCountryState(d_sourceCountryId).getArmies() + l_advancingTroops);
                        l_outcome = GameEngine.getPlayerList().get(d_playerId).getName() + " failed to conquer " + d_targetCountryId + ".";
                    }
                }
                System.out.println(l_outcome);
            }
        }
    }
}
