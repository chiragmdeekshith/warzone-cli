package com.fsociety.warzone.game.order.card;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Order;
import com.fsociety.warzone.util.Console;

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
        GameEngine.getTruces().get(d_playerId).add(d_enemyId);
        GameEngine.getTruces().get(d_enemyId).add(d_playerId);
        String l_outcome = GameEngine.getPlayerNameFromId(d_playerId) + " has declared a truce with " + GameEngine.getPlayerNameFromId(d_enemyId) + ".";
        Console.print(l_outcome);
    }

    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}