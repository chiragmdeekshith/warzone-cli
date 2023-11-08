package com.fsociety.warzone.asset.phase.play.playsetup;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Map;

public class PlayPostLoad extends PlaySetup{

    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.GAME_PLAYER, Command.ASSIGN_COUNTRIES, Command.SHOW_PLAYERS};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(l_help);
    }

    @Override
    public void showMap() {
        GameplayController.getPlayMap().showMap();
    }

    public void showPlayers() {
        if (GameplayController.getPlayerNameMap().isEmpty()) {
            Console.print("List of players is empty.");
        } else {
            GameplayController.printPlayers();
        }
    }

    @Override
    public void assignCountries() {
        PlayMap l_playMap = GameplayController.getPlayMap();
        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();

        if(l_playerNameMap.size() < 2) {
            Console.print("Please add at least two players to the game to continue.");
            return;
        }

        if (l_playerNameMap.size() > GameplayController.getPlayMap().getNeighbours().keySet().size()) {
            Console.print("Too many players for this map. Please remove players to continue.");
            return;
        }

        GameplayController.finalizePlayers();
        GameplayController.initTruces();

        List<Integer> l_countryIds = new ArrayList<>(l_playMap.getNeighbours().keySet());
        List<Player> l_players = GameplayController.getPlayers();
        Random l_random = new Random();

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
        GameplayController.gamePlayLoop();
    }

    @Override
    public void gamePlayer(Set<String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove) {
        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        for(String l_playerName : p_gamePlayersToAdd) {
            if(l_playerNameMap.containsKey(l_playerName)) {
                Console.print("Player " + l_playerName + " is already present. Add operation failed.");
            } else {
                l_playerNameMap.put(l_playerName, new Player(l_playerName));
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
