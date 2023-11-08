package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.models.map.PlayMap;
import com.fsociety.warzone.models.Player;
import com.fsociety.warzone.utils.Console;

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
        GameEngineController.getPlayMap().showMap();
    }

    public void showPlayers() {
        if (GameEngineController.getPlayers() == null) {
            Console.print("List of players is empty.");
        } else {
            GameEngineController.printPlayers();
        }
    }

    @Override
    public void assignCountries() {
        PlayMap l_playMap = GameEngineController.getPlayMap();
        Map<String, Player> l_playerNameMap = GameEngineController.getPlayerNameMap();

        if(l_playerNameMap.isEmpty()) {
            Console.print("Please add at least one player to the game to continue.");
            return;
        }

        if (l_playerNameMap.size() > GameEngineController.getPlayMap().getNeighbours().keySet().size()) {
            Console.print("Too many players for this map. Please remove players to continue.");
            return;
        }

        GameEngineController.finalizePlayers();
        GameEngineController.initTruces();

        List<Integer> l_countryIds = new ArrayList<>(l_playMap.getNeighbours().keySet());
        List<Player> l_players = GameEngineController.getPlayers();
        Random l_random = new Random();

        // Randomly assign countries to players
        int l_counter = 0;
        while (!l_countryIds.isEmpty()) {
            Player l_player = l_players.get(l_counter % l_players.size()); // Cycles through the players in round-robin
            int randomIndex = l_random.nextInt(l_countryIds.size());
            int l_countryId = l_countryIds.remove(randomIndex);
            l_playMap.updateGameState(l_countryId, l_player.getId(), 0);
            l_player.addCountry(l_playMap.getCountryState(l_countryId));
            l_playMap.getCountryState(l_countryId).setPlayer(l_player);
            l_counter++;
        }

        // Start the main loop
        GameEngineController.mainLoop();
    }

    @Override
    public void gamePlayer(Set<String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove) {
        Map<String, Player> l_playerNameMap = GameEngineController.getPlayerNameMap();
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
