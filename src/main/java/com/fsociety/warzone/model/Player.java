package com.fsociety.warzone.model;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.game.order.IOrder;
import com.fsociety.warzone.util.IdGenerator;

import java.util.Scanner;
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
    public int get_troops() {
        return this.l_available_troops;
    }
    public void set_troops(int p_troops) {
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


    public void issue_order() {
        // Add an order to orders list during issue orders phase

        /**
         * @TODO Format commands using the command parser
         */
        String[] l_parameters;
        boolean l_is_valid_order = false;
        while (!l_is_valid_order) {
            System.out.print(this.getName() + ": You have " + this.get_troops() + " available reinforcements. ");
            System.out.println("Please input a valid order.");
            String l_input = Application.SCANNER.nextLine();;
            l_parameters = l_input.split(" ");
            if (l_parameters.length == 0) {
                continue;
            }
            switch(l_parameters[0].toLowerCase()) {
                case "deploy":
                    try {
                        if (Integer.parseInt(l_parameters[2]) <= get_troops()) {
                            l_is_valid_order = true;
                            System.out.println(l_parameters[2] + " reinforcement armies will be deployed to " + l_parameters[1] + ".");
                            l_orders.add(new Deploy(Integer.parseInt(l_parameters[1]), Integer.parseInt(l_parameters[2]), this.l_id));
                            this.l_available_troops -= Integer.parseInt(l_parameters[2]);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Please input valid parameters for the deploy order.");
                        continue;
                    }
                    break;
                default:
                    System.out.println("Please input a valid order.");
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
