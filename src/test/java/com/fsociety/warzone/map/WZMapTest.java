package com.fsociety.warzone.map;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;

public class WZMapTest {
    @Test
    void testAddContinent_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 7);
        Assertions.assertEquals(1, l_map.getContinentCountriesMap().size());
        Assertions.assertEquals(7, l_map.getContinentBonusMap().get(1));
    }

    @Test
    void testAddCountry_() {
        final WZMap l_map = new WZMap();
        // l_map.addCountry(1, 1);
        // Assertions.assertEquals(1, l_map.getAdjacencyMap().size());
        // Assertions.assertEquals(1, l_map.getContinentCountriesMap().get(1).size());
        // Assertions.assertEquals(new HashSet<>(Arrays.asList(1)), l_map.getContinentCountriesMap().get(1));
    }

    @Test
    void testAddNeighbour() {

    }

    @Test
    void testGetAdjacencyMap() {

    }

    @Test
    void testGetContinentBonusMap() {

    }

    @Test
    void testGetContinentCountriesMap() {

    }

    @Test
    void testGetContinents() {

    }

    @Test
    void testGetGameState() {

    }

    @Test
    void testInitGameStates() {

    }

    @Test
    void testRemoveAdjacency() {

    }

    @Test
    void testRemoveContinent() {

    }

    @Test
    void testRemoveCountry() {

    }

    @Test
    void testRemoveNeighbour() {

    }

    @Test
    void testSetFileName() {

    }

    @Test
    void testShowMapForEditor() {

    }

    @Test
    void testShowMapForGame() {

    }

    @Test
    void testUpdateGameState() {

    }
}
