package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.PlayMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.util.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PlayPostLoad extends PlaySetup{

    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.GAME_PLAYER, Command.ASSIGN_COUNTRIES};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command -flag [arguments] / -flag [arguments]";
        Console.print(help);
    }

    @Override
    public void showMap() {
        GameEngine.getPlayMap().showMap();
    }

    @Override
    public void assignCountries() {
        PlayMap l_playMap = GameEngine.getPlayMap();
        List<Player> l_players = GameEngine.getPlayers();

        if(l_players.isEmpty()) {
            Console.print("List of players cannot be empty.");
            return;
        }

        List<Integer> l_countryIds = new ArrayList<>(l_playMap.getNeighbours().keySet());

        Random l_random = new Random();

        int l_counter = 0;
        while (!l_countryIds.isEmpty()) {
            Player l_player = l_players.get(l_counter%l_players.size()); // Cycles through the players in round-robin
            int randomIndex = l_random.nextInt(l_countryIds.size());
            int l_countryId = l_countryIds.remove(randomIndex);
            l_playMap.updateGameState(l_countryId, l_player.getId(), 0);
            l_player.addCountry(l_playMap.getCountryState(l_countryId));
            l_playMap.getCountryState(l_countryId).setPlayer(l_player);
            l_counter++;
        }

        GameRunner.setPhase(new Reinforcement());
    }

    @Override
    public void gamePlayer(Set<String> p_gamePlayersToAdd, Set<String> p_gamePlayersToRemove) {
        // TODO game player stuff
    }
}
