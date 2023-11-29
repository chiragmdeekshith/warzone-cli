package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.util.map.ConquestMapTools;
import com.fsociety.warzone.util.map.DominationMapTools;
import com.fsociety.warzone.util.map.MapAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Assign Reinforcements phase.
 */
class AssignReinforcementsTest {

    Player d_player1, d_player2;
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
        d_playMap.updateCountry(2, d_player2.getId(), 0);
        d_player2.addCountry(d_playMap.getCountryState(2));
        d_playMap.getCountryState(2).setPlayer(d_player2);
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
     * Test for accuracy of number of assigned reinforcements.
     */
    @Test
    void reinforcementCalculation() {
        // Player1 has 3 reinforcements because they have the base number of reinforcements
        assertEquals(d_player1.getAvailableReinforcements(), 3);
        // Player1 has 3 reinforcements because they have the base number of reinforcements plus the bonus for
        // Continent 2
        assertEquals(d_player2.getAvailableReinforcements(), 8);
    }

}