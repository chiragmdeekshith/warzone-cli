package com.fsociety.warzone.asset.phase.play.playsetup;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.player.strategy.*;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * This Class implements the commands that are valid during the Start-Up phase of gameplay after a map has been loaded.
 */
public class PlayPostLoad extends PlaySetup {

    /**
     * This method compiles and prints a help message of valid commands for the PlayPostLoad phase when the 'help'
     * command is entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.SAVE_GAME, Command.LOAD_GAME, Command.GAME_PLAYER, Command.ASSIGN_COUNTRIES, Command.SHOW_PLAYERS};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(l_help);
    }

    /**
     * This method calls the map to be printed once loaded using the 'showmap' command.
     */
    @Override
    public void showMap() {
        GameplayController.getPlayMap().showMap();
    }

    /**
     * This method prints the players active in the game using the 'showplayers' command.
     */
    public void showPlayers() {
        if (GameplayController.getPlayerNameMap().isEmpty()) {
            Console.print("List of players is empty.");
        } else {
            GameplayController.printPlayers();
        }
    }

    /**
     * This method randomly assigns countries to the players in round-robin fashion before starting the main gameplay
     * loop when the player enters the 'assigncountries' command.
     */
    @Override
    public void assignCountries() {
        PlayMap l_playMap = GameplayController.getPlayMap();
        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();

        if(l_playerNameMap.size() < 2) {
            Console.print("Please add at least two players to the game to continue.");
            return;
        }

        // If there are more players than there are countries in the map
        if (l_playerNameMap.size() > GameplayController.getPlayMap().getNeighbours().keySet().size()) {
            Console.print("Too many players for this map. Please remove players to continue.");
            return;
        }

        GameplayController.finalizePlayers();
        GameplayController.initTruces();
        GameplayController.materializeNeutralPlayer();

        List<Integer> l_countryIds = new ArrayList<>(l_playMap.getNeighbours().keySet());
        List<Player> l_players = GameplayController.getPlayers();
        java.util.Random l_random = new java.util.Random();

        // Randomly assign countries to players
        int l_counter = 0;
        while (!l_countryIds.isEmpty()) {
            Player l_player = l_players.get(l_counter % l_players.size()); // Cycles through the players in round-robin
            int randomIndex = l_random.nextInt(l_countryIds.size());
            int l_countryId = l_countryIds.remove(randomIndex);
            l_playMap.updateCountry(l_countryId, l_player.getId(), 0);
            l_player.addCountry(l_playMap.getCountryState(l_countryId));
            l_playMap.getCountryState(l_countryId).setPlayer(l_player);
            l_counter++;
        }

        // Start the main loop
        GameplayController.gamePlayLoop(true);
    }

    /**
     * This method allows the user to edit the list of players using the 'gameplayer' command.
     */
    @Override
    public void gamePlayer(Map<String, String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove) {
        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        for(String l_playerName : p_gamePlayersToAdd.keySet()) {
            if(l_playerNameMap.containsKey(l_playerName)) {
                Console.print("Player " + l_playerName + " is already present. Add operation failed.");
            } else {
                Strategy l_playerStrategy = null;
                switch (p_gamePlayersToAdd.get(l_playerName)) {
                    case Command.HUMAN -> l_playerStrategy = new Human();
                    case Command.AGGRESSIVE -> l_playerStrategy = new Aggressive();
                    case Command.BENEVOLENT -> l_playerStrategy = new Benevolent();
                    case Command.CHEATER -> l_playerStrategy = new Cheater();
                    case Command.RANDOM -> l_playerStrategy = new Random();
                }
                l_playerNameMap.put(l_playerName, new Player(l_playerName, l_playerStrategy));
                Console.print("Player " + l_playerName + " added.");
            }
        }
        for(String l_playerName : p_gamePlayersToRemove) {
            if(l_playerNameMap.containsKey(l_playerName)) {
                l_playerNameMap.remove(l_playerName);
                Console.print("Player " + l_playerName + " removed.");
            } else {
                Console.print("Player " + l_playerName + " does not exist. Remove operation failed.");
            }
        }
    }
}
