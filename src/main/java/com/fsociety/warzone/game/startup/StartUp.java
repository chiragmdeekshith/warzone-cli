package com.fsociety.warzone.game.startup;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.play.PlayMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.command.CommandHandler;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.ArrayList;

public class StartUp {

    public static void start_up() {
        System.out.println("New game selected. Please start by loading a map.");
        String l_inputRawCommand;
        do {
            System.out.println("Enter command.");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();

            if(CommandHandler.isValidCommand(l_inputRawCommand, Phase.START_UP) && l_inputRawCommand.split(" ")[0].equals("loadmap")){
                loadMap(l_inputRawCommand.split(" ")[1]);
                l_inputRawCommand = editPlayers();
                break;
            } else {
                System.out.println("Please start by loading a map.");
            }
        } while(!l_inputRawCommand.equals(StartupCommand.BACK.getCommand()));

        if (l_inputRawCommand.equals(StartupCommand.BACK.getCommand())) {
            // go back to main menu
        } else {
            assignCountries();
        }
    }

    /**
     * @TODO Implement map loading using FileIO
     */
    public static void loadMap(String p_fileName) {

        PlayMap l_map = new PlayMap();

        GameEngine.set_map(l_map);

    }

    /**
     * @TODO Implement command parser to edit list of players
     */
    public static String editPlayers() {

        ArrayList<Player> l_players = new ArrayList<>();

        System.out.println("Please create list of players.");
        String l_inputRawCommand = "";
        while(!l_inputRawCommand.equals(StartupCommand.BACK.getCommand())) {
            System.out.println("Enter command.");
            System.out.print("> ");
            l_inputRawCommand = Application.SCANNER.nextLine();
            if(CommandHandler.isValidCommand(l_inputRawCommand, Phase.START_UP) && l_inputRawCommand.split(" ")[0].equals("gameplayer")){
                String l_name = l_inputRawCommand.split(" ")[2];
                if (l_inputRawCommand.split(" ")[0].equals("-add")) {
                    if (!containsPlayer(l_players, l_name)) {
                        l_players.add(new Player(l_name));
                    } else {
                        System.out.println("Player " + l_name + " already exists.");
                    }

                }
            } else if ((CommandHandler.isValidCommand(l_inputRawCommand, Phase.START_UP) && l_inputRawCommand.split(" ")[0].equals("assigncountries"))) {
                if (l_players.size() == 1) {
                    System.out.println("Please add at least one player to the game to continue.");
                } else {
                    break;
                }
            }
        }
        if (l_inputRawCommand.equals(StartupCommand.BACK.getCommand())) {
            return "back";
        } else {
            GameEngine.set_players(l_players);
            return "assigncountries";
        }
    }

    public static boolean containsPlayer(ArrayList<Player> p_players, String p_name) {
        for (int i = 0; i < p_players.size(); i++) {
            if (p_players.get(i).getName().equals(p_name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @TODO Assign countries randomly to each player based on map
     */
    public static void assignCountries() {
        // Assign countries randomly
        // Then start the game loop

    }

}
