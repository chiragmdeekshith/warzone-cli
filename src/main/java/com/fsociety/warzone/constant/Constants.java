package com.fsociety.warzone.constant;

public class Constants {

    /**
     * Private constructor to prevent instantiation
     */
    private Constants() {
    }

    // Help messages for Cards
    public static final String DEPLOY_HELP_MESSAGE = "place some armies on one of the current player’s territories";
    public static final String ADVANCE_HELP_MESSAGE = "move some armies from one of the current player’s territories (source) to an "
            +
            "adjacent territory (target). If the target territory belongs to the current player, the armies are moved "
            +
            "to the target territory. If the target territory belongs to another player, an attack happens between " +
            "the two territories. ";
    public static final String BOMB_HELP_MESSAGE = "destroy half of the armies located on an opponent’s territory";
    public static final String BLOCKADE_HELP_MESSAGE = "triple the number of armies on one of the current player’s territories";
    public static final String AIRLIFT_HELP_MESSAGE = "advance some armies from one of the current player’s territories";
    public static final String NEGOTIATE_HELP_MESSAGE = "prevent attacks between the current player and another player until the end of the turn.";
}