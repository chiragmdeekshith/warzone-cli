package com.fsociety.warzone.map;

import com.fsociety.warzone.models.map.WZMap;
import org.junit.jupiter.api.Test;

import com.fsociety.warzone.models.Continent;

import java.util.*;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for WZMap
 */
public class WZMapTest {
    /**
     * Test method for {@link WZMap#addContinent(Integer, Integer)}
     */
    @Test
    void testAddContinent_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 7);

        Assertions.assertEquals(1, l_map.getContinentCountriesMap().size());
        Assertions.assertEquals(7, l_map.getContinentBonusMap().get(1));
    }

    /**
     * Test method for {@link WZMap#addCountry(Integer, Integer)}
     */
    @Test
    void testAddCountry_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 3);
        l_map.addCountry(1, 1);

        Assertions.assertEquals(1, l_map.getAdjacencyMap().size());
        Assertions.assertEquals(1, l_map.getContinentCountriesMap().get(1).size());
        Assertions.assertEquals(new HashSet<>(List.of(1)), l_map.getContinentCountriesMap().get(1));
    }

    /**
     * Test method for {@link WZMap#addNeighbour(int, int)}
     */
    @Test
    void testAddNeighbour_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addNeighbour(1, 2);

        Assertions.assertEquals(2, l_map.getContinentCountriesMap().get(1).size());
        Assertions.assertEquals(1, l_map.getAdjacencyMap().get(1).size());
        Assertions.assertEquals(1, l_map.getAdjacencyMap().get(2).size());
    }

    /**
     * Test method for {@link WZMap#getAdjacencyMap()}
     */
    @Test
    void testGetAdjacencyMap_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addNeighbour(1, 2);
        final Map<Integer, Set<Integer>> l_adjacencyMap = l_map.getAdjacencyMap();
        Assertions.assertEquals(2, l_adjacencyMap.size());
        Assertions.assertEquals(1, l_adjacencyMap.get(1).size());
        Assertions.assertEquals(1, l_adjacencyMap.get(2).size());
    }

    /**
     * Test method for {@link WZMap#getContinentBonusMap()}
     */
    @Test
    void testGetContinentBonusMap_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);

        Assertions.assertEquals(5, l_map.getContinentBonusMap().get(1));
    }

    /**
     * Test method for {@link WZMap#getContinentCountriesMap()}
     */
    @Test
    void testGetContinentCountriesMap_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);

        Assertions.assertEquals(2, l_map.getContinentCountriesMap().get(1).size());
    }

    /**
     * Test method for {@link WZMap#getContinents()}
     */
    @Test
    void testGetContinents_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.initGameStates();
        final Map<Integer, Continent> l_continents = l_map.getContinents();

        Assertions.assertEquals(1, l_continents.size());
        Assertions.assertNull(l_map.getContinents().get(1).getContinentOwner());
        Assertions.assertEquals(5, l_map.getContinents().get(1).getArmiesBonus());
    }

    /**
     * Test method for {@link WZMap#getGameState(int)}
     */
    @Test
    void testGetGameState() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.initGameStates();
        l_map.getGameState(1);

        Assertions.assertEquals(1, l_map.getContinents().size());
    }

    /**
     * Test method for {@link WZMap#initGameStates()}
     */
    @Test
    void testInitGameStates_success() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.initGameStates();

        Assertions.assertEquals(1, l_map.getContinents().size());
    }

    /**
     * Test method for {@link WZMap#initGameStates()}
     */
    @Test
    void testInitGameStates_failure() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        
        Assertions.assertEquals(0, l_map.getContinents().size());
        Assertions.assertEquals(0, l_map.getContinents().size());
    }

    /**
     * Test method for {@link WZMap#removeAdjacency(int)}
     */
    @Test
    void testRemoveAdjacency() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addNeighbour(1, 2);
        
        l_map.removeAdjacency(1);
        Assertions.assertEquals(1, l_map.getAdjacencyMap().size());

        l_map.removeAdjacency(2);
        Assertions.assertEquals(0, l_map.getAdjacencyMap().size());
    }

    /** 
     * Test method for {@link WZMap#removeContinent(Integer)}
     */
    @Test
    void testRemoveContinent() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.removeContinent(1);

        Assertions.assertEquals(0, l_map.getContinentCountriesMap().size());
        Assertions.assertEquals(0, l_map.getContinentBonusMap().size());
    }

    /**
     * Test method for {@link WZMap#removeCountry(int)}
     */
    @Test
    void testRemoveCountry() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.removeCountry(1);

        Assertions.assertEquals(0, l_map.getContinentCountriesMap().get(1).size());
        Assertions.assertEquals(0, l_map.getAdjacencyMap().size());
    }

    /**
     * Test method for {@link WZMap#removeNeighbour(int, int)}
     */
    @Test
    void testRemoveNeighbour() {
        final WZMap l_map = new WZMap();
        l_map.addContinent(1, 5);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addNeighbour(1, 2);
        l_map.removeNeighbour(1, 2);

        Assertions.assertEquals(0, l_map.getAdjacencyMap().get(1).size());
    }
}
