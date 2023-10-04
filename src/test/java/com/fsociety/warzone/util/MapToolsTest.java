package com.fsociety.warzone.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapToolsTest {

    public String informationMissing;
    public String continentWithNoCountry;
    public String countryWithNoNeighbour;
    public String duplicateNeighbours;
    public String continentNotConnected;
    public String validMap;
    @BeforeEach
    void setUp() {
        informationMissing = "informationMissing.map";
        continentWithNoCountry = "continentWithNoCountry.map";
        countryWithNoNeighbour = "countryWithNoNeighbour.map";
        duplicateNeighbours = "duplicateNeighbours.map";
        continentNotConnected = "continentNotConnected.map";
        validMap = "validMap.map";
    }

    @Test
    void testInformationMissing() {
        System.out.println("\nTest Case 1: Missing Information");
        assertNull(MapTools.loadAndValidateMap(informationMissing));
    }

    @Test
    void testContinentWithNoCountry() {
        System.out.println("\nTest Case 2: Continent With No Country");
        assertNull(MapTools.loadAndValidateMap(continentWithNoCountry));
    }

    @Test
    void testCountryWithNoNeighbour() {
        System.out.println("\nTest Case 3: Country With No Neighbour");
        assertNull(MapTools.loadAndValidateMap(countryWithNoNeighbour));
    }

    @Test
    void testDuplicateNeighbours() {
        System.out.println("\nTest Case 4: Duplicate Neighbours");
        assertNull(MapTools.loadAndValidateMap(duplicateNeighbours));
    }

    @Test
    void testContinentNotConnected() {
        System.out.println("\nTest Case 6: Continent Not Connected");
        assertNull(MapTools.loadAndValidateMap("continentNotConnected.map"));
    }

    @Test
    void testValidMap() {
        System.out.println("\nTest Case 5: Valid Map");
        assertNotNull(MapTools.loadAndValidateMap(validMap));
    }
}