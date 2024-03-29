package com.fsociety.warzone.asset.order.card;

import com.fsociety.warzone.view.Console;

import java.io.Serializable;
import java.util.Random;

/**
 * This class implements a hand of cards for the player which can be added to and played from.
 */
public class HandOfCards implements Serializable {

    /**
     * A counter to track the list of BOMB cards that a player owns
     */
    private int d_bombCards;

    /**
     * A counter to track the list of BLOCKADE cards that a player owns
     */
    private int d_blockadeCards;

    /**
     * A counter to track the list of AIRLIFT cards that a player owns
     */
    private int d_airliftCards;

    /**
     * A counter to track the list of DIPLOMACY cards that a player owns
     */
    private int d_diplomacyCards;

    /**
     * The name of the player who owns these hand of cards
     */
    private final String d_playerName;

    /**
     * A the object used to randomly assign a new card to the player
     */
    private static final Random rand = new Random();

    /**
     * This enum contains the possible card types.
     */
    public enum Card {
        /**
         * Used to bomb an enemy country
         */
        BOMB,
        /**
         * Used to blockade an allied country
         */
        BLOCKADE,
        /**
         * Used to move troops to any country on the map
         */
        AIRLIFT,
        /**
         * Used to negotiate with an enemy and call a truce
         */
        DIPLOMACY;
    }

    /**
     * Parameterised constructor to initialise HandOfCards.
     * @param p_playerName the name of the player
     */
    public HandOfCards(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_bombCards = 0;
        this.d_blockadeCards = 0;
        this.d_airliftCards = 0;
        this.d_diplomacyCards = 0;
    }

    /**
     * This method draws one card of a random type and adds it to the player's hand.
     * @return the String confirmation statement that a player drew a card
     */
    public String drawCards() {
        int l_choice = rand.nextInt(0,4);
        String l_type = "";
        switch (l_choice) {
            case 0 -> {
                d_bombCards++;
                l_type = "Bomb";
            }
            case 1 -> {
                d_blockadeCards++;
                l_type = "Blockade";
            }
            case 2 -> {
                d_airliftCards++;
                l_type = "Airlift";
            }
            case 3 -> {
                d_diplomacyCards++;
                l_type = "Diplomacy";
            }
        }
        return d_playerName + " drew a " + l_type + " card.";
    }

    /**
     * This method confirms whether the player has a card of type p_type in their hand. If so, the card is removed to be
     * played.
     *
     * @param p_type the desired card type
     * @return True if the card exists in the player's hand, and False otherwise.
     */
    public boolean playCard(Card p_type) {
        switch (p_type) {
            case BOMB -> {
                if (d_bombCards > 0) {
                    d_bombCards--;
                    return true;
                }
            }
            case BLOCKADE -> {
                if (d_blockadeCards > 0) {
                    d_blockadeCards--;
                    return true;
                }
            }
            case AIRLIFT -> {
                if (d_airliftCards > 0) {
                    d_airliftCards--;
                    return true;
                }
            }
            case DIPLOMACY -> {
                if (d_diplomacyCards > 0) {
                    d_diplomacyCards--;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method prints the cards in a players hand during the game.
     */
    public void showCards() {
        String l_asString = d_playerName + "'s Cards:\n";
        l_asString += "Bomb Cards: " + d_bombCards + "\n";
        l_asString += "Blockade Cards: " + d_blockadeCards + "\n";
        l_asString += "Airlift Cards: " + d_airliftCards + "\n";
        l_asString += "Diplomacy Cards: " + d_diplomacyCards + "\n";
        Console.print(l_asString);
    }

    /**
     * Draws a card of a specific type; used for testing.
     * @param p_card the type of card to draw
     */
    public void drawSpecificCard(Card p_card) {
        switch (p_card) {
            case BOMB -> {
                d_bombCards++;
            }
            case BLOCKADE -> {
                d_blockadeCards++;
            }
            case AIRLIFT -> {
                d_airliftCards++;
            }
            case DIPLOMACY -> {
                d_diplomacyCards++;
            }
        }
    }

}
