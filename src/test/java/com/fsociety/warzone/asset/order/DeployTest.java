package com.fsociety.warzone.asset.order;

import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.util.ConquestMapTools;
import com.fsociety.warzone.util.DominationMapTools;
import com.fsociety.warzone.util.MapAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Deploy order.
 */
class DeployTest {

    /**
     * Players for the test
     */
    Player d_player1, d_player2;

    /**
     * The test map
     */
    PlayMap d_playMap;
    /**
     * The map object
     */
    DominationMapTools d_mapTools = new MapAdapter(new ConquestMapTools());

    /**
     * Setup mock data for testing
     */
    @BeforeEach
    void setUp() {
        d_playMap = d_mapTools.loadAndValidatePlayableMap("1.map");
        GameplayController.setPlayMap(d_playMap);

        d_player1 = new Player("Player1");
        d_player2 = new Player("Player2");

        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        l_playerNameMap.put("Player1", d_player1);
        l_playerNameMap.put("Player2", d_player2);
        GameplayController.finalizePlayers();
        GameplayController.initTruces();

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
     * Test for target country validation.
     */
    @Test
    void countryValidationTest() {
        Phase l_reinforcement = new Reinforcement();
        IssueOrder.setCurrentPlayer(d_player1);
        l_reinforcement.deploy(1, 1);
        IssueOrder.setCurrentPlayer(d_player2);
        l_reinforcement.deploy(1, 1);
        assertNotNull(d_player1.nextOrder());
        assertNull(d_player2.nextOrder());
    }

    /**
     * Ensures that a player cannot deploy more reinforcement armies than they currently hold.
     */
    @Test
    void issueDeployCommand() {
        Phase l_reinforcement = new Reinforcement();
        IssueOrder.setCurrentPlayer(d_player1);
        l_reinforcement.deploy(1, 10);
        IssueOrder.setCurrentPlayer(d_player2);
        l_reinforcement.deploy(4, 10);
        assertNull(d_player1.nextOrder());
        assertNull(d_player2.nextOrder());
    }

    /**
     * Ensures that the Deploy command accurately affects the number of troops on the map.
     */
    @Test
    void nextOrder() {
        Phase l_reinforcement = new Reinforcement();
        IssueOrder.setCurrentPlayer(d_player1);
        l_reinforcement.deploy(1, 2);
        IssueOrder.setCurrentPlayer(d_player2);
        l_reinforcement.deploy(4, 5);
        d_player1.nextOrder().execute();
        d_player2.nextOrder().execute();
        assertEquals(2, d_playMap.getCountryState(1).getArmies());
        assertEquals(5, d_playMap.getCountryState(4).getArmies());
    }

}