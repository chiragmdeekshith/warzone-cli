package com.fsociety.warzone.game.mainloop;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.WZMap;
import com.fsociety.warzone.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Test class for AssignReinforcements
 */
class AssignReinforcementsTest {

    Player d_player1, d_player2;
    WZMap d_wzMap;

    /**
     * Setup function to prepare mock data for testing
     */
    @BeforeEach
    void setUp() {

        // new WZmap object
        d_wzMap = new WZMap();

        d_wzMap.addContinent(1, 7);
        d_wzMap.addContinent(2, 3);

        d_wzMap.addCountry(1, 1);
        d_wzMap.addCountry(2, 1);
        d_wzMap.addCountry(3, 2);
        d_wzMap.addCountry(4, 2);

        d_wzMap.addNeighbour(1,2);
        d_wzMap.addNeighbour(2,3);
        d_wzMap.addNeighbour(4,2);

        d_wzMap.initGameStates();

        //create players
        d_player1 = new Player("r");
        d_player2 = new Player("c");

        ArrayList<Player> l_players = new ArrayList<>();
        l_players.add(d_player1);
        l_players.add(d_player2);

        GameEngine.setPlayers(l_players);
        GameEngine.initPlayerList();
        GameEngine.setWZMap(d_wzMap);

        // Start modifying the maps
        d_wzMap.updateGameState(1, d_player1.getId(), 0);
        d_player1.addCountry(d_wzMap.getGameState(1));
        d_wzMap.getGameState(1).setPlayer(d_player1);

        d_wzMap.updateGameState(2, d_player1.getId(), 0);
        d_player1.addCountry(d_wzMap.getGameState(2));
        d_wzMap.getGameState(2).setPlayer(d_player1);

        d_wzMap.updateGameState(3, d_player1.getId(), 0);
        d_player1.addCountry(d_wzMap.getGameState(3));
        d_wzMap.getGameState(3).setPlayer(d_player1);

        d_wzMap.updateGameState(4, d_player2.getId(), 0);
        d_player2.addCountry(d_wzMap.getGameState(4));
        d_wzMap.getGameState(4).setPlayer(d_player2);

        d_wzMap.getContinents().keySet().forEach(continentId -> {
            d_wzMap.getContinents().get(continentId).setContinentOwner();
        });

        //Assert
        AssignReinforcements.assignReinforcements(d_player1);
        AssignReinforcements.assignReinforcements(d_player2);
    }


    /**
     * Verifies bonus reinforcements are being properly allocated to continent owners using created game state.
     * Test method for {@link com.fsociety.warzone.game.mainloop.AssignReinforcements#assignReinforcements(Player)}
     */
    @Test
    void assignReinforcements() {
        Assertions.assertEquals(12, d_player1.getAvailableReinforcements());
        Assertions.assertEquals(5, d_player2.getAvailableReinforcements());
    }
}