package com.fsociety.warzone.asset.order.card;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.view.Console;

/**
 * This class handles everything related to Diplomacy cards
 */
public class Diplomacy implements Order {

    private final int d_playerId;
    private final int d_enemyId;

    public Diplomacy(int p_playerId, int p_enemyId) {
        this.d_playerId = p_playerId;
        this.d_enemyId = p_enemyId;
    }

    /**
     * This method implements the Diplomacy card as per the Warzone rules: players are added to each other's truce list,
     * meaning they cannot attack each other until the end of the turn.
     */
    @Override
    public void execute() {
        GameplayController.getTruces().get(d_playerId).add(d_enemyId);
        GameplayController.getTruces().get(d_enemyId).add(d_playerId);
        String l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " has declared a truce with "
                + GameplayController.getPlayerNameFromId(d_enemyId) + ".";
        Console.print(l_outcome);
    }

    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}