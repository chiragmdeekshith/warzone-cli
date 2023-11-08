package com.fsociety.warzone.model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * The Test class for EditMap
 */
class EditMapTest {
    /**
     *EditMap object.
     */
    EditMap l_map;

    /**
     * Setting up the EditMap object before each test.
     */
    @BeforeEach
    void setUp() {
        l_map = new EditMap();
    }

    /**
     * Test method for {@link EditMap#addContinent(int, int)}.
     */
    @Test
    void testAddContinent() {
        assertTrue(l_map.addContinent(1, 7));
        assertFalse(l_map.addContinent(1, 7));
    }

    /**
     * Test method for {@link EditMap#removeContinent(int)}.
     */
    @Test
    void testRemoveContinent() {
        l_map.addContinent(1, 7);
        assertTrue(l_map.removeContinent(1));
        assertFalse(l_map.removeContinent(1));
    }

    /**
     * Test method for {@link EditMap#addCountry(int, int)}.
     */
    @Test
    void testAddCountry() {
        l_map.addContinent(1, 7);
        assertTrue(l_map.addCountry(1, 1));
        assertFalse(l_map.addCountry(1, 2));
        assertFalse(l_map.addCountry(1, 1));
    }

    /**
     * Test method for {@link EditMap#removeCountry(int)}.
     */
    @Test
    void testRemoveCountry() {
        l_map.addContinent(1, 7);
        l_map.addCountry(1, 1);
        assertTrue(l_map.removeCountry(1));
        assertFalse(l_map.removeCountry(1));
    }

    /**
     * Test method for {@link EditMap#addNeighbour(int, int)}.
     */
    @Test
    void testAddNeighbour() {
        l_map.addContinent(1, 7);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        assertFalse(l_map.addNeighbour(1, 1));
        assertFalse(l_map.addNeighbour(1, 3));
        assertTrue(l_map.addNeighbour(1, 2));
    }

    /**
     * Test method for {@link EditMap#removeNeighbour(int, int)}.
     */
    @Test
    void testRemoveNeighbour() {
        l_map.addContinent(1, 7);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addNeighbour(1, 2);
        assertFalse(l_map.removeNeighbour(1, 1));
        assertFalse(l_map.removeNeighbour(1, 3));
        assertTrue(l_map.removeNeighbour(1, 2));
    }

    /**
     * Test method for {@link EditMap#removeAdjacency(int)}.
     */
    @Test
    void testRemoveAdjacency() {
        l_map.addContinent(1, 7);
        l_map.addCountry(1, 1);
        l_map.addCountry(2, 1);
        l_map.addNeighbour(1, 2);
        l_map.removeAdjacency(1);
        assertFalse(l_map.removeNeighbour(1, 2));
    }
}