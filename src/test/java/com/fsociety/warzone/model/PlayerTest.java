package com.fsociety.warzone.model;

import com.fsociety.warzone.Application;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.map.WZMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player l_player1, l_player2;
    WZMap l_wzMap;

    @BeforeEach
    void setUp() {
        l_wzMap = new WZMap();

        l_wzMap.addContinent(1, 7);
        l_wzMap.addContinent(2, 3);

        l_wzMap.addCountry(1, 1);
        l_wzMap.addCountry(2, 1);
        l_wzMap.addCountry(3, 2);
        l_wzMap.addCountry(4, 2);

        l_wzMap.addNeighbour(1,2);
        l_wzMap.addNeighbour(2,3);
        l_wzMap.addNeighbour(4,2);

        l_wzMap.initGameStates();

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
    void issueDeployCommand() {
        assertFalse(l_player1.issueDeployCommand("deploy 1 13".split(" ")));
        assertTrue(l_player1.issueDeployCommand("deploy 1 2".split(" ")));
        assertFalse(l_player1.issueDeployCommand("deploy 4 2".split(" ")));
        assertTrue(l_player2.issueDeployCommand("deploy 4 2".split(" ")));
    }

    @Test
    void nextOrder() {
        int armies = l_wzMap.getGameState(1).getArmies();
        int deploymentArmies = 3;
        l_player1.issueDeployCommand("deploy 1 3".split(" "));
        l_player1.nextOrder();
        assertEquals(armies + deploymentArmies, l_wzMap.getGameState(1).getArmies());
    }
}