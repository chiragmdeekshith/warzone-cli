package com.fsociety.warzone.asset.order.card;

import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.model.Continent;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.util.MapTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Diplomacy order.
 */
class DiplomacyTest {

    /**
     * Players for the test
     */
    Player d_player1, d_player2;
    /**
     * Play Map for the test
     */
    PlayMap d_playMap;

    /**
     * Setup mock data for testing
     */
    @BeforeEach
    void setUp() {
        d_playMap = MapTools.loadAndValidatePlayableMap("1.map");
        GameplayController.setPlayMap(d_playMap);

        d_player1 = new Player("Player1");
        d_player2 = new Player("Player2");

        Map<String, Player> l_playerNameMap = GameplayController.getPlayerNameMap();
        l_playerNameMap.put("Player1", d_player1);
        l_playerNameMap.put("Player2", d_player2);
        GameplayController.finalizePlayers();
        GameplayController.initTruces();

        // Player1 should have 3 reinforcements and Player2 should have 8 reinforcements
        d_playMap.updateCountry(1, d_player2.getId(), 0);
        d_player2.addCountry(d_playMap.getCountryState(1));
        d_playMap.getCountryState(1).setPlayer(d_player2);
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

        // Ensure the player has a Diplomacy card
        d_player1.getHandOfCards().drawSpecificCard(HandOfCards.Card.DIPLOMACY);

    }

    /**
     * Test for ability to attack another player's country before and after playing a Diplomacy card.
     */
    @Test
    void diplomacyTest() {
        Phase l_attack = new Attack();
        d_playMap.updateCountry(4, 3);
        d_playMap.updateCountry(1, 3);
        IssueOrder.d_availableTroopsOnMap = new HashMap<>();
        for (Country l_country : GameplayController.getPlayMap().getCountries().values()) {
            IssueOrder.d_availableTroopsOnMap.put(l_country.getCountryId(), l_country.getArmies());
        }
        // Player2 conquers one of Player1's countries
        IssueOrder.setCurrentPlayer(d_player2);
        l_attack.advance(1, 3,3);
        d_player2.nextOrder().execute();
        assertEquals(d_playMap.getCountryState(2).getPlayer(), d_player1);
        // Player1 plays the Diplomacy card
        IssueOrder.setCurrentPlayer(d_player1);
        l_attack.negotiate(d_player2.getName());
        d_player1.nextOrder().execute();
        // Player2 can no longer conquer one of Player1's countries
        IssueOrder.setCurrentPlayer(d_player2);
        l_attack.advance(4, 2,3);
        d_player2.nextOrder().execute();
        assertEquals(d_playMap.getCountryState(2).getPlayer(), d_player1);
    }

    /**
     * Test to make sure the issuing player has the Diplomacy card.
     */
    @Test
    void hasDiplomacyCardTest() {
        Phase l_attack = new Attack();
        IssueOrder.d_availableTroopsOnMap = new HashMap<>();
        IssueOrder.setCurrentPlayer(d_player2);
        l_attack.negotiate(d_player1.getName());
        assertNull(d_player2.nextOrder());
        IssueOrder.setCurrentPlayer(d_player1);
        l_attack.negotiate(d_player2.getName());
        assertNotNull(d_player1.nextOrder());
    }

}