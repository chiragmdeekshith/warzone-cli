package com.fsociety.warzone.model.map;

import com.fsociety.warzone.util.DominationMapTools;
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
    EditMap d_editMap;

    /**
     * Setting up the EditMap object before each test.
     */
    @BeforeEach
    void setUp() {
        d_editMap = DominationMapTools.loadAndValidateEditableMap("1.map");
    }

    /**
     * Test method for {@link EditMap#addContinent(int, int)}.
     */
    @Test
    void testAddContinent() {
        assertTrue(d_editMap.addContinent(3, 7));
        assertFalse(d_editMap.addContinent(1, 7));
    }

    /**
     * Test method for {@link EditMap#removeContinent(int)}.
     */
    @Test
    void testRemoveContinent() {
        assertTrue(d_editMap.removeContinent(1));
        assertFalse(d_editMap.removeContinent(1));
        assertFalse(d_editMap.removeContinent(4));
    }

    /**
     * Test method for {@link EditMap#addCountry(int, int)}.
     */
    @Test
    void testAddCountry() {
        assertTrue(d_editMap.addCountry(5, 2));
        assertFalse(d_editMap.addCountry(1, 2));
        assertFalse(d_editMap.addCountry(6, 3));
    }

    /**
     * Test method for {@link EditMap#removeCountry(int)}.
     */
    @Test
    void testRemoveCountry() {
        assertTrue(d_editMap.removeCountry(1));
        assertFalse(d_editMap.removeCountry(1));
        assertFalse(d_editMap.removeCountry(5));
    }

    /**
     * Test method for {@link EditMap#addNeighbour(int, int)}.
     */
    @Test
    void testAddNeighbour() {
        d_editMap.addCountry(5,1);
        assertTrue(d_editMap.addNeighbour(5, 4));
        assertFalse(d_editMap.addNeighbour(1, 1));
        assertTrue(d_editMap.addNeighbour(1, 5));
    }

    /**
     * Test method for {@link EditMap#removeNeighbour(int, int)}.
     */
    @Test
    void testRemoveNeighbour() {
        d_editMap.addCountry(5,2);
        d_editMap.addNeighbour(5,4);
        assertFalse(d_editMap.removeNeighbour(1, 1));
        assertFalse(d_editMap.removeNeighbour(1, 6));
        assertTrue(d_editMap.removeNeighbour(1, 2));
    }

    /**
     * Test method for {@link EditMap#removeAdjacency(int)}.
     */
    @Test
    void testRemoveAdjacency() {
        assertTrue(d_editMap.removeNeighbour(1, 3));
        d_editMap.removeAdjacency(1);
        assertFalse(d_editMap.removeNeighbour(1, 2));
    }
}