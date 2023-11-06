package com.fsociety.warzone.model;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.game.order.card.HandOfCards;
import com.fsociety.warzone.game.order.Order;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.util.IdGenerator;
import com.fsociety.warzone.util.command.CommandValidator;
import com.fsociety.warzone.util.command.constant.GameplayCommand;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

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
    private boolean cardDrawn;
    private boolean committed;


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
         this.cardDrawn = false;
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
     * gameplay. The 'showmap' command can be used here to display the map.
     *
     * @return returns false if a user types in the 'back' command in order to return to the main menu
     */
    public boolean issueDeployOrder() {

        String l_inputRawCommand;

        while (true) {

            System.out.print(this.getName() + ": You have " + this.getAvailableReinforcements() + " available reinforcements. ");
            System.out.println("Please issue a valid order.");
            System.out.print("> ");
            l_inputRawCommand = Console.commandPrompt();
            String[] l_parameters = l_inputRawCommand.split(" ");

            if(CommandValidator.isValidCommand(l_inputRawCommand, Phase.GAME_PLAY)) {
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];

                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }

                if(GameplayCommand.DEPLOY.getCommand().equals(l_commandType)) {
                    if(issueDeployCommand(l_parameters)) {
                        return true;
                    }
                }

                if(GameplayCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                    GameEngine.getPlayMap().showMap();
                }
            } else {
                System.out.println("Invalid command. Please use the 'deploy' command.");
            }
        }
    }

    /**
     * Creates and adds an object of type IOrder to the player's list of orders during the Issue Orders phase of
     * gameplay. The 'showmap' command can be used here to display the map.
     *
     * @return returns false if a user types in the 'back' command in order to return to the main menu
     */
    public boolean issueOrder() {

        String l_inputRawCommand;

        while (true) {

            System.out.print(this.getName() + ": ");
            System.out.println("Please issue a valid order.");
            System.out.print("> ");
            l_inputRawCommand = Console.commandPrompt();
            String[] l_parameters = l_inputRawCommand.split(" ");

            if(CommandValidator.isValidCommand(l_inputRawCommand, Phase.GAME_PLAY)) {
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];

                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }

                if(GameplayCommand.DEPLOY.getCommand().equals(l_commandType)) {
                    if(issueDeployCommand(l_parameters)) {
                        return true;
                    }
                }

                if(GameplayCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                    GameEngine.getPlayMap().showMap();
                }
            } else {
                System.out.println("Invalid command. Please use the 'deploy' command.");
            }
        }
    }

    /**
     * Issues deploy command.
     *
     * @param p_parameters - command entered by the user
     * @return true if command is issued properly, false otherwise
     */
    public boolean issueDeployCommand(String[] p_parameters) {
        if (Integer.parseInt(p_parameters[2]) <= d_availableReinforcements) {
            if (getCountryIds().contains(Integer.parseInt(p_parameters[1]))) {
                System.out.println(p_parameters[2] + " reinforcement armies will be deployed to " + p_parameters[1] + ".");
                d_orders.add(new Deploy(Integer.parseInt(p_parameters[1]), Integer.parseInt(p_parameters[2]), this.d_id));
                this.d_availableReinforcements -= Integer.parseInt(p_parameters[2]);
                return true;
            } else {
                System.out.println("You do not own this country!");
                return false;
            }
        } else {
            System.out.println("Insufficient reinforcements!");
            return false;
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
    public void drawCard() {
        if (!cardDrawn) {
            cardDrawn = true;
            d_handOfCards.drawCards();
        }
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
        return committed;
    }

    /**
     * This method resets the truth value of the committed boolean to false at the end of each turn.
     */
    public void resetCommitted() { committed = false; }

    /**
     * This method resets the cardDrawn boolean to false at the end of each turn. This ensures the player can draw a
     * card again next turn.
     */
    public void resetCardDrawn() {
        cardDrawn = false;
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

}
