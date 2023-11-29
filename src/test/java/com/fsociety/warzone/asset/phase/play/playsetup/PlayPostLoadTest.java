package com.fsociety.warzone.asset.phase.play.playsetup;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.edit.EditPreLoad;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.view.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the PlayPostLoad phase.
 */
class PlayPostLoadTest {

    /**
     * The Phase object used for testing
     */
    Phase d_phase;
    PlayMap d_playMap;

    /**
     * The setup function to reset the phase
     */
    @BeforeEach
    void setUp() {
        // Load the map
        d_phase = new PlayPreLoad();
        d_phase.loadMap("1.map");
        d_playMap = GameplayController.getPlayMap();

        // Set the players
        d_phase = new PlayPostLoad();
        Map<String, String> l_gamePlayersToAdd = new HashMap<>();
        Set<String> l_gamePlayersToRemove = new HashSet<>();
        l_gamePlayersToAdd.put("Player1", "-human");
        l_gamePlayersToAdd.put("Player2", "-benevolent");
        d_phase.gamePlayer(l_gamePlayersToAdd, l_gamePlayersToRemove);
    }

    /**
     * Tear down mock data for testing.
     */
    @AfterEach
    void tearDown() {
        GameplayController.resetGameState();
    }

    /**
     * Test for the creation of players.
     */
    @Test
    void testCreatePlayers() {
        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        assertEquals(l_playerNameMap.size(), 2);
        assertEquals(l_playerNameMap.get("Player1").getPlayerStrategy().toString(),"Human");
        assertEquals(l_playerNameMap.get("Player2").getPlayerStrategy().toString(),"Benevolent");
    }

    /**
     * Test that no country has no player.
     */
    @Test
    void testCountriesForPlayer() {
        try {
            d_phase.assignCountries();
        }
        catch (NoSuchElementException e) {
            Console.print("Testing country ownership");
        }
        Map<Integer, Country> l_countries = GameplayController.getPlayMap().getCountries();
        for (Country l_country : l_countries.values()) {
            assertNotNull(l_country.getPlayer());
        }
    }

    /**
     * Test that countries are equally distributed among players.
     */
    @Test
    void testCountryDistribution() {
        try {
            d_phase.assignCountries();
        }
        catch (NoSuchElementException e) {
            Console.print("Testing country distribution");
        }
        ArrayList<Player> l_players = GameplayController.getPlayers();
        for (Player l_player : l_players) {
            assertEquals(l_player.getCountries().size(), 2);
        }
    }

    /**
     * Test to ensure that it doesn't remain in the same state if BACK command is given by the user
     */
    @Test
    public void testBack() {
        d_phase.back();
        assertEquals(Menu.class, GameEngine.getPhase().getClass());
    }
}