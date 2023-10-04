package com.fsociety.warzone.model;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.game.order.IOrder;
import com.fsociety.warzone.util.IdGenerator;
import com.fsociety.warzone.util.command.CommandValidator;
import com.fsociety.warzone.util.command.constant.GameplayCommand;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.ArrayList;

public class Player {

    private final String d_name;
    private final Integer d_id;
    private ArrayList<IOrder> d_orders;
    private ArrayList<Country> d_countries;
    private int d_availableReinforcements;


    public Player(String p_player_name) {
         this.d_name = p_player_name;
         d_orders = new ArrayList<>();
         d_countries = new ArrayList<>();
         d_availableReinforcements = 0;
         d_id = IdGenerator.generateId();
    }


    // Getters and setters
    public int getAvailableReinforcements() {
        return this.d_availableReinforcements;
    }
    public void setAvailableReinforcements(int p_availableReinforcements) {
        this.d_availableReinforcements = p_availableReinforcements;
    }
    public int getOrdersCount() { return this.d_orders.size(); }
    public String getName() { return this.d_name; }

    public int getId() {
        return this.d_id;
    }

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
        for (int i = 0; i < d_countries.size(); i++) {
            l_countryIds.add(d_countries.get(i).getCountryId());
        }
        return l_countryIds;
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

            System.out.print(this.getName() + ": You have " + this.getAvailableReinforcements() + " available reinforcements. ");
            System.out.println("Please issue a valid order.");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();
            String[] l_parameters = l_inputRawCommand.split(" ");

            if(CommandValidator.isValidCommand(l_inputRawCommand, Phase.GAME_PLAY)) {
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];

                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }

                if(GameplayCommand.DEPLOY.getCommand().equals(l_commandType)) {
                    if (Integer.parseInt(l_parameters[2]) <= d_availableReinforcements) {
                        if (getCountryIds().contains(Integer.parseInt(l_parameters[1]))) {
                            System.out.println(l_parameters[2] + " reinforcement armies will be deployed to " + l_parameters[1] + ".");
                            d_orders.add(new Deploy(Integer.parseInt(l_parameters[1]), Integer.parseInt(l_parameters[2]), this.d_id));
                            this.d_availableReinforcements -= Integer.parseInt(l_parameters[2]);
                            return true;
                        } else {
                            System.out.println("You do not own this country!");
                        }
                    } else {
                        System.out.println("Insufficient reinforcements!");
                    }
                }

                if(GameplayCommand.SHOW_MAP.getCommand().equals(l_commandType)) {
                    GameEngine.getWZMap().showMapForGame();
                }
            } else {
                System.out.println("Invalid command. Please use the 'deploy' command.");
            }
        }
    }

    /**
     * This method removes the first order from the player's list of orders and executes the order by calling its
     * execute() method.
     */
    public void nextOrder() {
        if (!d_orders.isEmpty()) {
            (d_orders.remove(0)).execute();
        }

    }

}
