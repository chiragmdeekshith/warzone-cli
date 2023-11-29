package com.fsociety.warzone.controller.gameplay;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the Tournament mode.
 */
public class TournamentTest {

    /**
     * The Phase object used for testing
     */
    Phase d_phase;
    Tournament d_tournament;

    @BeforeEach
    void setUp() {
        d_phase = new Menu();
        GameEngine.setPhase(d_phase);
        ArrayList<String> l_botPlayers = new ArrayList<>();
        l_botPlayers.add("-benevolent");
        l_botPlayers.add("-benevolent");
        ArrayList<String> l_maps = new ArrayList<>();
        l_maps.add("1.map");
        l_maps.add("Alberta.map");
        d_tournament = new Tournament(2, 1, l_botPlayers, l_maps);
    }

    /**
     * Test to ensure that the results for each game are not null
     */
    @Test
    public void testTournamentResults() {
        d_tournament.runTournament();
        String[][] l_results = d_tournament.getResults();
        assertNotNull(l_results[0][0]);
        assertNotNull(l_results[1][0]);
        assertNotNull(l_results[0][1]);
        assertNotNull(l_results[1][1]);
    }

    /**
     * Test to ensure that the results are a draw given the types of players
     */
    @Test
    public void testDrawResults() {
        d_tournament.runTournament();
        String[][] l_results = d_tournament.getResults();
        assertEquals(l_results[0][0], "Draw");
        assertEquals(l_results[1][0], "Draw");
        assertEquals(l_results[0][1], "Draw");
        assertEquals(l_results[1][1], "Draw");
    }

}
