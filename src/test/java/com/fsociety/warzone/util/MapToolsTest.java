package com.fsociety.warzone.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The Test class for MapTools
 * {@link MapTools#loadAndValidateEditableMap(String)}
 */
class MapToolsTest {

    public static String informationMissing;
    public static String continentWithNoCountry;
    public static String countryWithNoNeighbour;
    public static String duplicateNeighbours;
    public static String continentNotConnected;
    public static String mapNotConnected;
    public static String validMap;

    /**
     * Setting up mack maps for testing
     */
    @BeforeAll
    static void setUp() {
        informationMissing = "informationMissing.map";
        continentWithNoCountry = "continentWithNoCountry.map";
        countryWithNoNeighbour = "countryWithNoNeighbour.map";
        duplicateNeighbours = "duplicateNeighbours.map";
        continentNotConnected = "continentNotConnected.map";
        mapNotConnected = "mapNotConnected.map";
        validMap = "validMap.map";
    }

    /**
     * Test for missing information
     */
    @Test
    void testInformationMissing() {
        System.out.println("Test Case 1: Missing Information");
        assertNull(MapTools.loadAndValidateEditableMap(informationMissing));
    }

    /**
     * Test for continent with no country
     */
    @Test
    void testContinentWithNoCountry() {
        System.out.println("Test Case 2: Continent With No Country");
        assertNull(MapTools.loadAndValidateEditableMap(continentWithNoCountry));
    }

    /**
     * Test for country with no neighbour
     */
    @Test
    void testCountryWithNoNeighbour() {
        System.out.println("Test Case 3: Country With No Neighbour");
        assertNull(MapTools.loadAndValidateEditableMap(countryWithNoNeighbour));
    }

    /**
     * Test for Duplicate Neighbours
     */
    @Test
    void testDuplicateNeighbours() {
        System.out.println("Test Case 4: Duplicate Neighbours");
        assertNull(MapTools.loadAndValidateEditableMap(duplicateNeighbours));
    }

    /**
     * Test for Continent not connected
     */
    @Test
    void testContinentNotConnected() {
        System.out.println("Test Case 5: Continent Not Connected");
        assertNull(MapTools.loadAndValidateEditableMap(continentNotConnected));
    }

    /**
     * Test for Map not connected
     */
    @Test
    void testMapNotConnected() {
        System.out.println("Test Case 6: Map Not Connected");
        assertNull(MapTools.loadAndValidateEditableMap(mapNotConnected));
    }

    /**
     * Test for Valid map
     */
    @Test
    void testValidMap() {
        System.out.println("Test Case 7: Valid Map");
        assertNotNull(MapTools.loadAndValidateEditableMap(validMap));
    }

}