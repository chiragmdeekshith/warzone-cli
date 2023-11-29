package com.fsociety.warzone.model.player;

import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.asset.order.card.HandOfCards;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.player.strategy.Human;
import com.fsociety.warzone.model.player.strategy.Strategy;
import com.fsociety.warzone.util.IdGenerator;
import com.fsociety.warzone.view.Console;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a Player object.
 */
public class Player implements Serializable {

    /**
     * Name of the player
     */
    private final String d_name;
    /**
     * ID of the player
     */
    private final Integer d_id;
    /**
     * A list of orders for the player
     */
    private final ArrayList<Order> d_orders;
    /**
     * List of countries the player owns
     */
    private final ArrayList<Country> d_countries;
    /**
     * The available reinforcements for the player
     */
    private int d_availableReinforcements;
    /**
     * The Hand Of Cards a player hols
     */
    private final HandOfCards d_handOfCards;
    /**
     * A flag to check if a player has drawn a card
     */
    private boolean d_cardDrawn;
    /**
     * A flag to check if the player has committed
     */
    private boolean d_committed;
    /**
     * A flag to check if  order has been issues
     */
    private boolean d_orderIssued;
    /**
     * The strategy of the player - Can be Human, or other Computer players
     */
    private Strategy d_playerStrategy;


    /**
     * Parameterised constructor to initialise the Player object with default Human player strategy.
     *
     * @param p_player_name - The name of the player
     */
    public Player(String p_player_name) {
        this.d_name = p_player_name;
        this.d_orders = new ArrayList<>();
        this.d_countries = new ArrayList<>();
        this.d_availableReinforcements = 0;
        this.d_handOfCards = new HandOfCards(this.d_name);
        this.d_id = IdGenerator.generateId();
        this.d_playerStrategy = new Human();
    }

    /**
     * Parameterised constructor to initialise the Player object along with strategy.
     *
     * @param p_player_name - The name of the player
     * @param p_playerStrategy - The type of Player - either human, or AI / Computer
     */
    public Player(String p_player_name, Strategy p_playerStrategy) {
         this.d_name = p_player_name;
         this.d_orders = new ArrayList<>();
         this.d_countries = new ArrayList<>();
         this.d_availableReinforcements = 0;
         this.d_handOfCards = new HandOfCards(this.d_name);
         this.d_id = IdGenerator.generateId();
         this.d_playerStrategy = p_playerStrategy;
    }

    /**
     * Add country to the list countries owned by player.
     *
     * @param p_country - the country object to be added
     */
    public void addCountry(Country p_country) {
        this.d_countries.add(p_country);
    }

    /**
     * Remove a country from the list countries owned by player.
     *
     * @param p_countryId - the ID of the country object to be removed
     */
    public void removeCountry(int p_countryId) {
        this.d_countries.remove(GameplayController.getPlayMap().getCountries().get(p_countryId));
    }

    /**
     * Compiles a list of country IDs for the list of countries the player currently controls.
     *
     * @return l_countryIds the list of country IDs
     */
    public ArrayList<Integer> getCountryIds() {
        ArrayList<Integer> l_countryIds = new ArrayList<>();
        for (Country dCountry : d_countries) {
            l_countryIds.add(dCountry.getCountryId());
        }
        return l_countryIds;
    }

    /**
     * Creates and adds an object of type Order to the player's list of orders during the Issue Orders phase of
     * gameplay.
     */
    public void issueOrder() {
        while (true) {
            d_playerStrategy.issueOrder(d_name);
            if(d_orderIssued) {
                d_orderIssued = false;
                return;
            }
        }
    }

    /**
     * This method removes and returns the first order from the player's list of orders if it exists.
     * @return the first order on the player's list
     */
    public Order nextOrder() {
        if (!d_orders.isEmpty()) {
            return d_orders.remove(0);
        }
        return null;
    }

    /**
     * This method adds a card to the player's hand. It is called once per turn when they conquer a country.
     * @return the String confirmation statement that a card was drawn, or null if the draw fails
     */
    public String drawCard() {
        if (!d_cardDrawn) {
            d_cardDrawn = true;
            return d_handOfCards.drawCards();
        }
        return null;
    }

    /**
     * This method is called when a player commits their orders during the Issue Orders phase. This means they can no
     * longer issue orders for the given turn.
     */
    public void commit() {
        d_committed = true;
        Console.print(d_name + " has committed.", true);
    }

    /**
     * This method validates whether a players list of countries is empty, meaning they have been defeated and should
     * be removed from the game.
     * @return whether the player's list of countries is empty
     */
    public boolean isEliminated() {
        return d_countries.isEmpty();
    }

    /**
     * This method adds an order to the player's list of orders.
     * @param p_order the order to be added to the list
     */
    public void addOrder(Order p_order) {
        d_orders.add(p_order);
    }

    /**
     * This method confirms whether the player has committed their orders for the current turn.
     * @return True if the player has committed their orders, False otherwise
     */
    public boolean hasCommitted() {
        return d_committed;
    }

    /**
     * This method resets the truth value of the d_committed boolean to false at the end of each turn. This ensures the
     * player can issue orders again next turn.
     */
    public void resetCommitted() { d_committed = false; }

    /**
     * This method resets the d_cardDrawn boolean to false at the end of each turn. This ensures the player can draw a
     * card again next turn.
     */
    public void resetCardDrawn() {
        d_cardDrawn = false;
    }

    /**
     * This method sets the d_orderIssued boolean to true when the player successfully issues an order. This ensures
     * that their turn is not skipped if they enter an invalid or non-order command.
     */
    public void setOrderIssued() {
        d_orderIssued = true;
    }

    // Getters and setters

    /**
     * Get the available reinforcements
     * @return the available reinforcements
     */
    public int getAvailableReinforcements() {
        return this.d_availableReinforcements;
    }

    /**
     * Set the available reinforcements
     * @param p_availableReinforcements - the available reinforcements
     */
    public void setAvailableReinforcements(int p_availableReinforcements) {
        this.d_availableReinforcements = p_availableReinforcements;
    }

    /**
     * Get the number of orders
     * @return orders count
     */
    public int getOrdersCount() { return this.d_orders.size(); }

    /**
     * Get the name of the player
     * @return the player's names
     */
    public String getName() { return this.d_name; }

    /**
     * Return the ID of the player
     * @return the player id
     */
    public int getId() {
        return this.d_id;
    }

    /**
     * Return the number of countries the player owns
     * @return the number of countries
     */
    public int getCountriesCount() { return this.d_countries.size(); }

    /**
     * Return the list of countries the player owns
     * @return the list of countries
     */
    public ArrayList<Country> getCountries() { return this.d_countries; }

    /**
     * This method returns the player's hand of cards
     * @return the player's hand of cards
     */
    public HandOfCards getHandOfCards() {
        return d_handOfCards;
    }

    /**
     * Retrieves the current strategy of the player
     * @return The player strategy class
     */
    public Strategy getPlayerStrategy() {
        return d_playerStrategy;
    }

    /**
     * Updates or sets a strategy for a player
     * @param d_playerStrategy The new strategy for this player
     */
    public void setPlayerStrategy(Strategy d_playerStrategy) {
        this.d_playerStrategy = d_playerStrategy;
    }
}
