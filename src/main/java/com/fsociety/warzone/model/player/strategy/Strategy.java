package com.fsociety.warzone.model.player.strategy;

import java.io.Serializable;

/**
 * This interface is a bse class for different kinds of Player strategies.
 */
public interface Strategy extends Serializable {

    /**
     * This function creates an order and then adds it to the order execution list.
     * @param p_playerName The name of the player that is to be prompted for an input. (Applicable only for Human players)
     */
    void issueOrder(String p_playerName);
}
