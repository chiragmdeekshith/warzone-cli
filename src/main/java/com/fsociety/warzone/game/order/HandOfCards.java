package com.fsociety.warzone.game.order;

import java.util.Random;

public class HandOfCards {

    private int d_bombCards;
    private int d_blockadeCards;
    private int d_airliftCards;
    private int d_diplomacyCards;
    private final String d_playerName;

    private static final Random rand = new Random();
    public enum CardType {
        BOMB, BLOCKADE, AIRLIFT, DIPLOMACY;
    }

    public HandOfCards(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_bombCards = 0;
        this.d_blockadeCards = 0;
        this.d_airliftCards = 0;
        this.d_diplomacyCards = 0;
    }

    /**
     * This method draws one card of a random type, to be called at the start of a round for each player.
     */
    public void drawCards() {
        int l_choice = rand.nextInt(0,4);
        switch (l_choice) {
            case 0:
                d_bombCards++;
                break;
            case 1:
                d_blockadeCards++;
                break;
            case 2:
                d_airliftCards++;
                break;
            case 3:
                d_diplomacyCards++;
                break;
        }
    }

    /**
     * This confirms whether the player has a card of type p_type in their hand. If so, the card is removed to be
     * played.
     *
     * @param p_type the desired card type
     * @return True if the card exists in the player's hand, and False otherwise.
     */
    public boolean playCard(CardType p_type) {
        switch (p_type) {
            case BOMB:
                if (d_bombCards > 0) {
                    d_bombCards--;
                    return true;
                }
                break;
            case BLOCKADE:
                if (d_blockadeCards > 0) {
                    d_blockadeCards--;
                    return true;
                }
                break;
            case AIRLIFT:
                if (d_airliftCards > 0) {
                    d_airliftCards--;
                    return true;
                }
                break;
            case DIPLOMACY:
                if (d_diplomacyCards > 0) {
                    d_diplomacyCards--;
                    return true;
                }
                break;
        }
        return false;
    }


    @Override
    public String toString() {
        String l_asString = d_playerName + "'s Cards:\n";
        l_asString += "Bomb Cards: " + d_bombCards + "\n";
        l_asString += "Blockade Cards: " + d_blockadeCards + "\n";
        l_asString += "Airlift Cards: " + d_airliftCards + "\n";
        l_asString += "Diplomacy Cards: " + d_diplomacyCards + "\n";
        return l_asString;
    }

}
