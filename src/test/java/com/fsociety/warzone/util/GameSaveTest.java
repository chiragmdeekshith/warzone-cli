package com.fsociety.warzone.util;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.util.map.ConquestMapTools;
import com.fsociety.warzone.util.map.DominationMapTools;
import com.fsociety.warzone.util.map.MapAdapter;
import com.fsociety.warzone.view.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for saving and loading the game
 */
public class GameSaveTest {
    /**
     * Players for the test
     */
    Player d_player1, d_player2, d_neutralPlayer;
    /**
     * Play Map for the test
     */
    PlayMap d_playMap;
    /**
     * The map object
     */
    DominationMapTools d_mapTools = new MapAdapter(new ConquestMapTools());

    /**
     * File name to save and load
     */
    private String d_fileName;

    /**
     * File path of saved game
     */
    private String d_filePath;
    /**
     * Setup mock data for testing
     */
    @BeforeEach
    void setUp() {
        d_playMap = d_mapTools.loadAndValidatePlayableMap("1.map");
        GameplayController.setPlayMap(d_playMap);

        d_player1 = new Player("Player1");
        d_player2 = new Player("Player2");

        d_fileName = "testSaveGame.save";
        d_filePath = "src/main/resources/saves/testSaveGame.save";

        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        l_playerNameMap.put("Player1", d_player1);
        l_playerNameMap.put("Player2", d_player2);
        GameplayController.finalizePlayers();
        GameplayController.initTruces();
        GameplayController.materializeNeutralPlayer();
        d_neutralPlayer = GameplayController.getNeutralPlayer();

        // Player1 should have 3 reinforcements and Player2 should have 8 reinforcements
        d_playMap.updateCountry(1, d_player1.getId(), 0);
        d_player1.addCountry(d_playMap.getCountryState(1));
        d_playMap.getCountryState(1).setPlayer(d_player1);
        d_playMap.updateCountry(2, d_player1.getId(), 0);
        d_player1.addCountry(d_playMap.getCountryState(2));
        d_playMap.getCountryState(2).setPlayer(d_player1);
        d_playMap.updateCountry(3, d_player1.getId(), 0);
        d_player1.addCountry(d_playMap.getCountryState(3));
        d_playMap.getCountryState(3).setPlayer(d_player1);
        d_playMap.updateCountry(4, d_player2.getId(), 0);
        d_player2.addCountry(d_playMap.getCountryState(4));
        d_playMap.getCountryState(4).setPlayer(d_player2);

        d_playMap.getContinents().keySet().forEach(continentId -> {
            d_playMap.getContinents().get(continentId).computeAndSetContinentOwner();
        });

        for (Player l_player : GameplayController.getPlayers()) {
            int l_base = Math.max(3, l_player.getCountriesCount() / 3);
            AtomicInteger l_reinforcements = new AtomicInteger(l_base); // Base reinforcements
            Map<Integer, Continent> l_continents = GameplayController.getPlayMap().getContinents();
            l_continents.keySet().forEach(continentId -> {
                if (l_continents.get(continentId).getContinentOwner() != null && l_player.equals(l_continents.get(continentId).getContinentOwner())) {
                    l_reinforcements.addAndGet(l_continents.get(continentId).getArmiesBonus());
                }
            });
            l_player.setAvailableReinforcements(l_reinforcements.get());
        }
    }

    /**
     * Tear down mock data for testing.
     */
    @AfterEach
    void tearDown() {
        GameplayController.resetGameState();
    }

    /**
     * Test for saving the game
     */
    @Test
    void testSaveGame() {
        Phase l_phase = new Attack();
        GameEngine.setPhase(l_phase);
        GameEngine.getPhase().saveGame(d_fileName);
        FileReader l_fileReader ;
        try {
            l_fileReader = new FileReader(d_filePath);
        } catch (FileNotFoundException e) {
            fail();
            return;
        }
        BufferedReader l_bufferedReader = new BufferedReader(l_fileReader);
        try {
            assertNotNull(l_bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }
    }

    /**
     * Test for loading the game
     */
    @Test
    void testLoadGame() {
        Phase l_phase = new Menu();
        GameEngine.setPhase(l_phase);

        Set<Integer> l_playerId = new HashSet<>();

        try {
            // Checks to see if the game is loaded correctly
            GameEngine.getPhase().loadGame("testSaveGame.save");
        }
        catch (NoSuchElementException e) {
            Console.print("Testing loading and saving");
        }
        int l_player1 = GameplayController.getPlayers().get(0).getId(), l_player2 = GameplayController.getPlayers().get(1).getId();
        l_playerId.add(l_player1);
        l_playerId.add(l_player2);
        // There is no tournament in the saved game
        assertNull(GameplayController.getCurrentTournament());
        // Game is not won
        assertFalse(GameplayController.getGameWon());
        // Checking for empty truces as game still in reinforcement phase
        assertTrue(GameplayController.getTruces().get(l_player1).isEmpty());
        assertTrue(GameplayController.getTruces().get(l_player2).isEmpty());
        // No winner as game is not won
        assertNull(GameplayController.getWinner());
        //Checking equal for the playerIdMap
        assertEquals(GameplayController.getPlayerIdMap().keySet(),l_playerId);
        //Checking equal for the playerNameMap
        assertEquals(GameplayController.getPlayerNameMap().get(d_player1.getName()).getId(),l_player2);
        // Saved game has two players
        assertEquals(GameplayController.getPlayers().size(),2);
        //Checking to see if the maps are the same
        assertEquals(GameplayController.getPlayMap().getFileName(),d_playMap.getFileName());
        //Checking for neutral player
        assertEquals(GameplayController.getNeutralPlayer().getId(),-1);
    }
}
