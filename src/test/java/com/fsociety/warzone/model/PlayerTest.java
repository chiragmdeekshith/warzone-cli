package com.fsociety.warzone.model;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.mainloop.AssignReinforcements;
import com.fsociety.warzone.map.WZMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Player class
 */
class PlayerTest {

    Player l_player1, l_player2;
    WZMap l_wzMap;

    /**
     * Setup mock data for testing
     */
    @BeforeEach
    void setUp() {
        //New wzMap object
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

        //Create players
        l_player1 = new Player("r");
        l_player2 = new Player("c");

        ArrayList<Player> l_players = new ArrayList<>();
        l_players.add(l_player1);
        l_players.add(l_player2);

        GameEngine.setPlayers(l_players);
        GameEngine.initPlayerList();
        GameEngine.setPlayMap(l_wzMap);

        // Change the state of the game
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
            l_wzMap.getContinents().get(continentId).computeAndSetContinentOwner();
        });

        // Asserts
        AssignReinforcements.assignReinforcements(l_player1);
        AssignReinforcements.assignReinforcements(l_player2);
    }


    /**
     * Ensures that a player cannot deploy more reinforcement armies than they currently hold.
     * Test method for {@link Player#issueDeployCommand(String[])}
     */
    @Test
    void issueDeployCommand() {
        assertFalse(l_player1.issueDeployCommand("deploy 1 13".split(" ")));
        assertTrue(l_player1.issueDeployCommand("deploy 1 2".split(" ")));
        assertFalse(l_player1.issueDeployCommand("deploy 4 2".split(" ")));
        assertTrue(l_player2.issueDeployCommand("deploy 4 2".split(" ")));
    }

    /**
     * Ensures that the Deploy command accurately affects the number of troops on the map.
     * Test method for {@link Player#nextOrder()}
     */
    @Test
    void nextOrder() {
        int armies = l_wzMap.getGameState(1).getArmies();
        int deploymentArmies = 3;
        l_player1.issueDeployCommand("deploy 1 3".split(" "));
        l_player1.nextOrder();
        assertEquals(armies + deploymentArmies, l_wzMap.getGameState(1).getArmies());
    }
}