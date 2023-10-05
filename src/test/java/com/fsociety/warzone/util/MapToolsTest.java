package com.fsociety.warzone.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The Test class for MapTools
 * {@link MapTools#loadAndValidateMap(String)}
 */
class MapToolsTest {

    public String informationMissing;
    public String continentWithNoCountry;
    public String countryWithNoNeighbour;
    public String duplicateNeighbours;
    public String continentNotConnected;
    public String validMap;

    /**
     * Setting up mack maps for testing
     */
    @BeforeEach
    void setUp() {
        informationMissing = "informationMissing.map";
        continentWithNoCountry = "continentWithNoCountry.map";
        countryWithNoNeighbour = "countryWithNoNeighbour.map";
        duplicateNeighbours = "duplicateNeighbours.map";
        continentNotConnected = "continentNotConnected.map";
        validMap = "validMap.map";
    }

    /**
     * Test for missing information
     */
    @Test
    void testInformationMissing() {
        System.out.println("Test Case 1: Missing Information");
        assertNull(MapTools.loadAndValidateMap(informationMissing));
    }

    /**
     * Test for continent with no country
     */
    @Test
    void testContinentWithNoCountry() {
        System.out.println("Test Case 2: Continent With No Country");
        assertNull(MapTools.loadAndValidateMap(continentWithNoCountry));
    }

    /**
     * Test for country with no neighbour
     */
    @Test
    void testCountryWithNoNeighbour() {
        System.out.println("Test Case 3: Country With No Neighbour");
        assertNull(MapTools.loadAndValidateMap(countryWithNoNeighbour));
    }

    /**
     * Test for Duplicate Neighbours
     */
    @Test
    void testDuplicateNeighbours() {
        System.out.println("Test Case 4: Duplicate Neighbours");
        assertNull(MapTools.loadAndValidateMap(duplicateNeighbours));
    }

    /**
     * Test for Continent not connected
     */
    @Test
    void testContinentNotConnected() {
        System.out.println("Test Case 6: Continent Not Connected");
        assertNull(MapTools.loadAndValidateMap("continentNotConnected.map"));
    }

    /**
     * Test for Valid map
     */
    @Test
    void testValidMap() {
        System.out.println("Test Case 5: Valid Map");
        assertNotNull(MapTools.loadAndValidateMap(validMap));
    }
}