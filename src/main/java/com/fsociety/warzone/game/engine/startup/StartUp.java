package com.fsociety.warzone.game.engine.startup;

import com.fsociety.warzone.game.engine.GameEngine;
import com.fsociety.warzone.game.map.play.PlayMap;
import com.fsociety.warzone.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class StartUp {

    public static void start_up() {
        load_map();
        edit_players();
        assign_countries();
    }

    /**
     * @TODO Implement map loading using FileIO
     */
    public static void load_map() {

        PlayMap l_map = new PlayMap();

        GameEngine.set_map(l_map);

    }

    /**
     * @TODO Implement command parser to edit list of players
     */
    public static void edit_players() {

        ArrayList<Player> l_players = new ArrayList<>();

        GameEngine.set_players(l_players);
    }

    /**
     * @TODO Assign countries randomly to each player based on map
     */
    public static void assign_countries() {

    }

}
