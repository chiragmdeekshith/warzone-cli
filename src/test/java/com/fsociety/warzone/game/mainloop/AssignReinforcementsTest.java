package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.WZMap;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.MapTools;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AssignReinforcementsTest {

    Player l_player1, l_player2;

    @BeforeEach
    void setUp() {

        WZMap l_wzMap = MapTools.loadAndValidateMap("euro2.map");

        l_player1 = new Player("r");
        l_player2 = new Player("c");

        ArrayList<Player> l_players = new ArrayList<>();
        l_players.add(l_player1);
        l_players.add(l_player2);

        GameEngine.setPlayers(l_players);
        GameEngine.initPlayerList();
        GameEngine.setWZMap(l_wzMap);

        l_wzMap.updateGameState(1, l_player1.getId(), 0);
        l_player1.addCountry(l_wzMap.getGameState(1));
        l_wzMap.getGameState(1).setPlayer(l_player1);

        l_wzMap.updateGameState(2, l_player1.getId(), 0);
        l_player1.addCountry(l_wzMap.getGameState(2));
        l_wzMap.getGameState(2).setPlayer(l_player1);

        l_wzMap.updateGameState(3, l_player1.getId(), 0);
        l_player1.addCountry(l_wzMap.getGameState(3));
        l_wzMap.getGameState(3).setPlayer(l_player1);

        l_wzMap.updateGameState(4, l_player2.getId(), 0);
        l_player2.addCountry(l_wzMap.getGameState(4));
        l_wzMap.getGameState(4).setPlayer(l_player2);

        l_wzMap.getContinents().keySet().forEach(continentId -> {
            l_wzMap.getContinents().get(continentId).setContinentOwner();
        });

        AssignReinforcements.assignReinforcements(l_player1);
        AssignReinforcements.assignReinforcements(l_player2);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Torn down");
    }

    @Test
    void assignReinforcements() {
        Assertions.assertEquals(9, l_player1.getAvailableReinforcements());
        Assertions.assertEquals(5, l_player2.getAvailableReinforcements());
    }
}