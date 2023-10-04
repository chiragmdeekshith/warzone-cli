package com.fsociety.warzone.util;

import com.fsociety.warzone.map.WZMap;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Set;

/**
 * This class provides all the functionality to read from Domination type .map files and store them in WZMap.
 * It can also validate the map and save the map into a new file.
 */
public class MapTools {

    /**
     * Reads the file from system and loads it into the WZMap. Validates the map before loading it in and then
     * logically validates it again before returning it.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the WZMap object ready
     */
    public static WZMap loadAndValidateMap(String p_fileName) {
        WZMap mapValues;
        try {
            // Read the file line by line
            mapValues = new WZMap();
            String l_filePath = "src/main/resources/" + p_fileName;
            FileReader mapFile;
            try {
                mapFile = new FileReader(l_filePath);
            } catch (FileNotFoundException e) {
                File newFile = new File(l_filePath);
                boolean success = newFile.createNewFile();
                if(!success) {
                    throw new RuntimeException("Failed to create file.");
                }
                mapValues.setFileName(p_fileName);
                return mapValues;
            }
            String line;
            StringBuilder data = new StringBuilder();
            BufferedReader mapReader = new BufferedReader(mapFile);
            line = mapReader.readLine();
            while(line !=null) {
                if(!line.equals("\n")){
                    data.append(line).append("\n");
                    line = mapReader.readLine();
                }
            }

            // Ensure that there are countries, continents and neighbours
            if (data.toString().toLowerCase().contains("[continents]") && data.toString().toLowerCase().contains("[countries]") && data.toString().toLowerCase().contains("[borders]")) {
                mapValues.setFileName(p_fileName);
            } else {
                System.out.println("Missing Information/Not in correct format");
                return null;
            }

            // Load the data into the WZMap object
            String[] continentData = data.substring(data.toString().toLowerCase().indexOf("[continents]")+13, data.toString().toLowerCase().indexOf("[countries]")).split("\n");
            String[] countryData = data.substring(data.toString().toLowerCase().indexOf("[countries]")+12, data.toString().toLowerCase().indexOf("[borders]")).split("\n");
            String[] neighborData = data.substring(data.toString().toLowerCase().indexOf("[borders]")+10).split("\n");
            for(String s:continentData) {
                mapValues.addContinent(Integer.parseInt(s.substring(0,s.indexOf(" "))),Integer.parseInt(s.substring(s.indexOf(" ")+1)));
            }
            for(String s:countryData) {
                mapValues.addCountry(Integer.parseInt(s.substring(0,s.indexOf(" "))),Integer.parseInt(s.substring(s.indexOf(" ")+1)));
            }
            for(String s:neighborData) {
                String[] temp = s.split(" ");
                int[] arr = new int[temp.length];
                int countryID= Integer.parseInt(temp[0]);
                for (int i=1;i<temp.length;i++) {
                    if(Arrays.binarySearch(arr,Integer.parseInt(temp[i]))>0) {
                        System.out.println("Duplicate Neighbors cannot exist");
                        return null;
                    }
                    arr[i] = Integer.parseInt(temp[i]);
                    mapValues.addNeighbour(countryID,arr[i]);
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Cant parseInt");
            return null;
        }
        catch (Exception e) {
            System.out.println("File does not exist!");
            return null;
        }
        if(validateMap(mapValues)) {
            mapValues.initGameStates();
            return mapValues;
        }
        return null;
    }


    /**
     *
     * This functions validates the map before saving into a physical file on the system
     *
     * @param p_mapData - the WZMap to save to the file
     * @param p_fileNameForSave - name of the new save file
     * @return true if the file was saved successfully, false otherwise
     */
    public static boolean saveMapFile(WZMap p_mapData, String p_fileNameForSave) {

        // Ensure the map is valid
        if(!validateMap(p_mapData)) {
            return false;
        }

        // Serialise the data
        StringBuilder l_data = new StringBuilder();
        l_data.append("[continents]\n");
        p_mapData.getContinentBonusMap().forEach((key,values) -> {
            l_data.append(key).append(" ").append(values).append("\n");
        });
        l_data.append("\n[countries]\n");
        p_mapData.getContinentCountriesMap().forEach((key,values) -> {
            for(Integer c:values)
                l_data.append(c).append(" ").append(key).append("\n");
        });
        l_data.append("\n[borders]");
        p_mapData.getAdjacencyMap().forEach((key,values) -> {
            l_data.append("\n").append(key).append(" ").append(values.toString().trim().replaceAll("[\\[\\]\",]",""));
        });

        // Write the data to the file
        PrintWriter l_write;
        try {
            l_write = new PrintWriter("src/main/resources/"+p_fileNameForSave);
            l_write.write(String.valueOf(l_data));
            l_write.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Logically validates the WZMap object
     *
     * @param p_mapData - the WZMap object that needs to be validated
     * @return true if the map is valid, false otherwise
     */
    public static boolean validateMap(WZMap p_mapData) {
        if(!checkEmptyContinent(p_mapData)) {
            return !checkEmptyNeighbours(p_mapData);
        }
        else
            return false;
    }

    /**
     * Check if the continent is empty , i.e, has no countries
     * @param p_mapData - the WZMap object that needs to be checked
     * @return true if the continent doesn't have any countries, false otherwise
     */
    public static boolean checkEmptyContinent(WZMap p_mapData) {
        if(p_mapData.getContinentBonusMap().isEmpty())
            return true;
        else {
            for(Set<Integer> countries:p_mapData.getContinentCountriesMap().values()) {
                if(countries.isEmpty()) {
                    System.out.println("Continent has no countries");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are any countries with empty neighbours.
     *
     * @param p_mapData - the WZMap object that needs to be checked
     * @return true if the neighbours are empty, false otherwise
     */
    public static boolean checkEmptyNeighbours(WZMap p_mapData) {
        for(Set<Integer> neighbours:p_mapData.getAdjacencyMap().values())
            if(neighbours.isEmpty()) {
                System.out.println("Country has no neighbours");
                return true;
            }
        return false;
    }

}