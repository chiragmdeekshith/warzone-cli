package com.fsociety.warzone.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The Test class for DominationMapTools
 * {@link DominationMapTools#loadAndValidateEditableMap(String)}
 */
class DominationMapToolsTest {
    /**
     * Map file names
     */
    public static String d_informationMissing;
    public static String d_continentWithNoCountry;
    public static String d_countryWithNoNeighbour;
    public static String d_duplicateNeighbours;
    public static String d_continentNotConnected;
    public static String d_mapNotConnected;
    public static String d_validMap;

    /**
     * The DominationMapTools object.
     */
    DominationMapTools mapTools = new DominationMapTools();

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
        d_validMap = "validMap.map";
    }

    /**
     * Test for missing information
     */
    @Test
    void testInformationMissing() {
        System.out.println("Test Case 1: Missing Information");
        assertNull(mapTools.loadAndValidateEditableMap(d_informationMissing));
    }

    /**
     * Test for continent with no country
     */
    @Test
    void testContinentWithNoCountry() {
        System.out.println("Test Case 2: Continent With No Country");
        assertNull(mapTools.loadAndValidateEditableMap(d_continentWithNoCountry));
    }

    /**
     * Test for country with no neighbour
     */
    @Test
    void testCountryWithNoNeighbour() {
        System.out.println("Test Case 3: Country With No Neighbour");
        assertNull(mapTools.loadAndValidateEditableMap(d_countryWithNoNeighbour));
    }

    /**
     * Test for Duplicate Neighbours
     */
    @Test
    void testDuplicateNeighbours() {
        System.out.println("Test Case 4: Duplicate Neighbours");
        assertNull(mapTools.loadAndValidateEditableMap(d_duplicateNeighbours));
    }

    /**
     * Test for Continent not connected
     */
    @Test
    void testContinentNotConnected() {
        System.out.println("Test Case 5: Continent Not Connected");
        assertNull(mapTools.loadAndValidateEditableMap(d_continentNotConnected));
    }

    /**
     * Test for Map not connected
     */
    @Test
    void testMapNotConnected() {
        System.out.println("Test Case 6: Map Not Connected");
        assertNull(mapTools.loadAndValidateEditableMap(d_mapNotConnected));
    }

    /**
     * Test for Valid map
     */
    @Test
    void testValidMap() {
        System.out.println("Test Case 7: Valid Map");
        assertNotNull(mapTools.loadAndValidateEditableMap(d_validMap));
    }

}