package com.fsociety.warzone.util.map;

import com.fsociety.warzone.util.map.DominationMapTools;
import com.fsociety.warzone.util.map.MapAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The Test class for DominationMapTools
 * {@link MapAdapter#loadAndValidateEditableMap(String)}
 */
class MapToolsTest {
    /**
     * Map file names
     */
    public static String d_informationMissing;
    public static String d_continentWithNoCountry;
    public static String d_countryWithNoNeighbour;
    public static String d_duplicateNeighbours;
    public static String d_continentNotConnected;
    public static String d_mapNotConnected;
    public static String d_validDominationMap;
    public static String d_validConquestMap;

    DominationMapTools d_mapTools = new MapAdapter(new ConquestMapTools());
    /**
     * The DominationMapTools object.
     */

    /**
     * Setting up mock maps for testing
     */
    @BeforeAll
    static void setUp() {
        d_informationMissing = "informationMissing.map";
        d_continentWithNoCountry = "continentWithNoCountry.map";
        d_countryWithNoNeighbour = "countryWithNoNeighbour.map";
        d_duplicateNeighbours = "duplicateNeighbours.map";
        d_continentNotConnected = "continentNotConnected.map";
        d_mapNotConnected = "mapNotConnected.map";
        d_validDominationMap = "validMap.map";
        d_validConquestMap = "Alberta.map";
    }

    /**
     * Test for missing information
     */
    @Test
    void testInformationMissing() {
        assertNull(d_mapTools.loadAndValidateEditableMap(d_informationMissing));
    }

    /**
     * Test for continent with no country
     */
    @Test
    void testContinentWithNoCountry() {
        assertNull(d_mapTools.loadAndValidateEditableMap(d_continentWithNoCountry));
    }

    /**
     * Test for country with no neighbour
     */
    @Test
    void testCountryWithNoNeighbour() {
        assertNull(d_mapTools.loadAndValidateEditableMap(d_countryWithNoNeighbour));
    }

    /**
     * Test for Duplicate Neighbours
     */
    @Test
    void testDuplicateNeighbours() {
        assertNull(d_mapTools.loadAndValidateEditableMap(d_duplicateNeighbours));
    }

    /**
     * Test for Continent not connected
     */
    @Test
    void testContinentNotConnected() {
        assertNull(d_mapTools.loadAndValidateEditableMap(d_continentNotConnected));
    }

    /**
     * Test for Map not connected
     */
    @Test
    void testMapNotConnected() {
        assertNull(d_mapTools.loadAndValidateEditableMap(d_mapNotConnected));
    }

    /**
     * Test for Valid domination map
     */
    @Test
    void testValidDominationMap() {
        assertNotNull(d_mapTools.loadAndValidateEditableMap(d_validDominationMap));
    }

    /**
     * Test for Valid conquest map
     */
    @Test
    void testValidConquestMap() {
        assertNotNull(d_mapTools.loadAndValidateEditableMap(d_validConquestMap));
    }

}