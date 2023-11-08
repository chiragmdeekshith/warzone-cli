package com.fsociety.warzone.model;

import com.fsociety.warzone.asset.command.CommandProcessor;
import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.asset.order.card.HandOfCards;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.util.IdGenerator;

import java.util.ArrayList;

/**
 * This class represents a Player object and handles issuing orders for the player.
 */
public class Player {

    private final String d_name;
    private final Integer d_id;
    private final ArrayList<Order> d_orders;
    private final ArrayList<Country> d_countries;
    private int d_availableReinforcements;
    private final HandOfCards d_handOfCards;
    private boolean d_cardDrawn;
    private boolean d_committed;
    private boolean d_orderIssued;


    /**
     * Parameterised constructor to initialise the object.
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
    }

    /**
     * Add country to the list countries owned by player.
     *
     * @param p_country - the country object to be added
     */
    public void addCountry(Country p_country) {
        this.d_countries.add(p_country);
    }

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
     * Creates and adds an object of type IOrder to the player's list of orders during the Issue Orders phase of
     * gameplay.
     */
    public void issueOrder() {
        while (true) {
            String l_command = Console.commandPrompt();
            CommandProcessor.processCommand(l_command);
            if(d_orderIssued) {
                d_orderIssued = false;
                return;
            }
        }
    }

    /**
     * This method removes the first order from the player's list of orders and executes the order by calling its
     * execute() method.
     */
    public Order nextOrder() {
        if (!d_orders.isEmpty()) {
            return d_orders.remove(0);
        }
        return null;
    }

    /**
     * This method adds a card to the player's hand. It is called once per turn when they conquer a country.
     */
    public String drawCard() {
        if (!d_cardDrawn) {
            d_cardDrawn = true;
            return d_handOfCards.drawCards();
        }
        return null;
    }

    public void commit() {
        d_committed = true;
    }

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
     * This method resets the truth value of the committed boolean to false at the end of each turn.
     */
    public void resetCommitted() { d_committed = false; }

    /**
     * This method resets the cardDrawn boolean to false at the end of each turn. This ensures the player can draw a
     * card again next turn.
     */
    public void resetCardDrawn() {
        d_cardDrawn = false;
    }

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

    public HandOfCards getHandOfCards() {
        return d_handOfCards;
    }

}
