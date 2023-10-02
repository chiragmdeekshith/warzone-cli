package com.fsociety.warzone.model;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.game.order.IOrder;
import com.fsociety.warzone.util.IdGenerator;
import com.fsociety.warzone.util.command.CommandHandler;
import com.fsociety.warzone.util.command.constant.GameplayCommand;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.ArrayList;

public class Player {

    private String l_name;

    private Integer l_id;
    private ArrayList<IOrder> l_orders;
    private ArrayList<Country> l_countries;
    private int l_available_troops;


    public Player(String p_player_name) {
         this.l_name = p_player_name;
         l_orders = new ArrayList<>();
         l_countries = new ArrayList<>();
         l_available_troops = 0;
         l_id = IdGenerator.generateId();
    }


    // Relevant getters and setters
    public int get_countries_count() {
        return this.l_countries.size();
    }
    public int getTroops() {
        return this.l_available_troops;
    }
    public void setTroops(int p_troops) {
        this.l_available_troops = p_troops;
    }
    public int get_orders_count() {
        return this.l_orders.size();
    }
    public String getName() { return this.l_name; }

    public int getId() {
        return this.l_id;
    }

    public void addCountry(Country p_country) {
        this.l_countries.add(p_country);
    }

    public ArrayList<Integer> getCountryIds() {
        ArrayList<Integer> d_countryIds = new ArrayList<>();
        for (int i = 0; i < l_countries.size(); i++) {
            d_countryIds.add(l_countries.get(i).getCountryId());
        }
        return d_countryIds;
    }

    public boolean issue_order() {
        // Add an order to orders list during issue orders phase

        String l_inputRawCommand;

        while (true) {

            System.out.print(this.getName() + ": You have " + this.getTroops() + " available reinforcements. ");
            System.out.println("Please input a valid order.");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();
            String[] l_parameters = l_inputRawCommand.split(" ");

            if(CommandHandler.isValidCommand(l_inputRawCommand, Phase.GAME_PLAY)) {
                String[] l_splitCommand = l_inputRawCommand.split(" ");
                String l_commandType = l_splitCommand[0];

                if(StartupCommand.BACK.getCommand().equals(l_commandType)) {
                    return false;
                }

                // DEPLOY command is given
                if(GameplayCommand.DEPLOY.getCommand().equals(l_commandType)) {
                    if (Integer.parseInt(l_parameters[2]) <= l_available_troops) {
                        if (getCountryIds().contains(Integer.parseInt(l_parameters[1]))) {
                            System.out.println(l_parameters[2] + " reinforcement armies will be deployed to " + l_parameters[1] + ".");
                            l_orders.add(new Deploy(Integer.parseInt(l_parameters[1]), Integer.parseInt(l_parameters[2]), this.l_id));
                            this.l_available_troops -= Integer.parseInt(l_parameters[2]);
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

    public void next_order() {
        // Returns first order from orders during execute orders phase

        if (!l_orders.isEmpty()) {
            (l_orders.remove(0)).execute();
        }

    }

}
