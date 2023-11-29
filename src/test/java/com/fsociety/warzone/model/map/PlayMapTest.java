package com.fsociety.warzone.model.map;

import java.util.Map;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.player.Player;

import com.fsociety.warzone.util.ConquestMapTools;
import com.fsociety.warzone.util.DominationMapTools;
import com.fsociety.warzone.util.MapAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayMapTest {
    /**
     * PlayMap object.
     * d_player1 and d_player2 are the players.
     */
    PlayMap d_playMap;
    /**
     * Players for the test
     */
    Player d_player1, d_player2;
    /**
     * The map object
     */
    DominationMapTools d_mapTools = new MapAdapter(new ConquestMapTools());

    /**
     * Setting up the PlayMap object before each test.
     * Player1 and Player2 are added to the player list.
     */
    @BeforeEach
    void setUp() {
        //Loading and validating the map
        d_playMap = d_mapTools.loadAndValidatePlayableMap("1.map");
        //Setting up the GameplayController
        GameplayController.setPlayMap(d_playMap);
        //Setting up the players
        d_player1 = new Player("Player1");
        d_player2 = new Player("Player2");
        //Adding the players to the player list
        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        l_playerNameMap.put("Player1", d_player1);
        l_playerNameMap.put("Player2", d_player2);
        //Finalizing the players
        GameplayController.finalizePlayers();
        GameplayController.initTruces();

    }


    /**
     * Test method for {@link PlayMap#initGameMapElements()}.
     */
    @Test
    void testInitGameMapElements() {
        assertTrue(d_playMap.getCountries().containsKey(1)&&d_playMap.getCountries().containsKey(2));
        assertFalse(d_playMap.getContinents().containsKey(5));
        assertEquals(2,d_playMap.getContinents().keySet().size());
        assertEquals(4,d_playMap.getCountries().keySet().size());
    }

    /**
     * Test method for {@link PlayMap#updateCountry(int, int, int)}.
     */
    @Test
    void testUpdateCountry() {
        d_playMap.updateCountry(1, d_player1.getId(), 5);
        assertEquals(d_player1.getId(), d_playMap.getCountries().get(1).getPlayerId());
        d_playMap.updateCountry(1,10);
        assertEquals(10, d_playMap.getCountries().get(1).getArmies());
    }

    /**
     * Test method for {@link PlayMap#conquerCountry(int, int, int)}.
     */
    @Test
    void testConquerCountry() {
        //d_playMap.initGameMapElements();
        d_playMap.updateCountry(1, d_player1.getId(), 5);
        d_player1.addCountry(d_playMap.getCountryState(1));
        d_playMap.getCountryState(1).setPlayer(d_player1);
        assertEquals(d_player1.getId(), d_playMap.getCountries().get(1).getPlayerId());
        d_playMap.conquerCountry(1, d_player2.getId(), 10);
        d_player1.addCountry(d_playMap.getCountryState(1));
        d_playMap.getCountryState(1).setPlayer(d_player2);
        assertEquals(d_player2.getId(), d_playMap.getCountries().get(1).getPlayerId());
    }

    /**
     * Test method for {@link PlayMap#isNeighbourOf(int, int)}.
     */
    @Test
    void testIsNeighbourOf() {
        d_playMap.initGameMapElements();
        d_playMap.updateCountry(1, d_player1.getId(), 5);
        d_playMap.updateCountry(4, d_player2.getId(),10);
        assertTrue(d_playMap.isNeighbourOf(2, d_player1.getId()));
        assertFalse(d_playMap.isNeighbourOf(4, d_player1.getId()));
        assertTrue(d_playMap.isNeighbourOf(2,d_player2.getId()));
    }
}