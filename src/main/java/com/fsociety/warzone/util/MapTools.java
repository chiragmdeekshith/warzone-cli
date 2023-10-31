package com.fsociety.warzone.util;

import com.fsociety.warzone.map.EditMap;
import com.fsociety.warzone.map.PlayMap;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides all the functionality to read from Domination type .map files and store them in WZMap Objects.
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
    public static EditMap loadAndValidateMap(String p_fileName) {
        EditMap l_editMap;
        try {
            // Read the file line by line
            l_editMap = new EditMap();
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
                l_editMap.setFileName(p_fileName);
                return l_editMap;
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
                l_editMap.setFileName(p_fileName);
            } else {
                System.out.println("Missing Information/Not in correct format");
                return null;
            }

            // Load the data into the WZMap object
            String[] continentData = data.substring(data.toString().toLowerCase().indexOf("[continents]")+13, data.toString().toLowerCase().indexOf("[countries]")).split("\n");
            String[] countryData = data.substring(data.toString().toLowerCase().indexOf("[countries]")+12, data.toString().toLowerCase().indexOf("[borders]")).split("\n");
            String[] neighborData = data.substring(data.toString().toLowerCase().indexOf("[borders]")+10).split("\n");
            int l_counter = 0;
            for(String s:continentData) {
                int l_continentId = ++l_counter;
                String[] l_splitData = s.split(" ");
                int l_continentBonusValue = Integer.parseInt(l_splitData[1]);
                l_editMap.addContinent(l_continentId, l_continentBonusValue);
            }
            for(String s:countryData) {
                String[] l_splitData = s.split(" ");
                int l_countryId = Integer.parseInt(l_splitData[0]);
                int l_continentId = Integer.parseInt(l_splitData[2]);
                l_editMap.addCountry(l_countryId, l_continentId);
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
                    l_editMap.addNeighbour(countryID,arr[i]);
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
        if(validateMap(l_editMap)) {
            return l_editMap;
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
    public static boolean saveMapFile(EditMap p_mapData, String p_fileNameForSave) {

        // Ensure the map is valid
        if(!validateMap(p_mapData)) {
            return false;
        }

        // Serialise the data
        StringBuilder l_data = new StringBuilder();
        l_data.append("[continents]\n");
        p_mapData.getContinentBonuses().forEach((key,values) -> {
            l_data.append(key).append(" ").append(key).append(" ").append(values).append("\n");
        });
        l_data.append("\n[countries]\n");
        p_mapData.getCountriesInContinent().forEach((key,values) -> {
            for(Integer c:values)
                l_data.append(c).append(" ").append(c).append(" ").append(key).append("\n");
        });
        l_data.append("\n[borders]");
        p_mapData.getNeighbours().forEach((key,values) -> {
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
    public static boolean validateMap(EditMap p_mapData) {
        if(!checkEmptyContinent(p_mapData)) {
            if(!checkEmptyNeighbours(p_mapData)) {
                return checkConnectedContinent(p_mapData);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Check if the continent is empty , i.e, has no countries
     *
     * @param p_mapData - the WZMap object that needs to be checked
     * @return true if the continent doesn't have any countries, false otherwise
     */
    public static boolean checkEmptyContinent(EditMap p_mapData) {
        if(p_mapData.getContinentBonuses().isEmpty())
            return true;
        else {
            for(Set<Integer> countries:p_mapData.getCountriesInContinent().values()) {
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
    public static boolean checkEmptyNeighbours(EditMap p_mapData) {
        for(Set<Integer> neighbours:p_mapData.getNeighbours().values())
            if(neighbours.isEmpty()) {
                System.out.println("Country has no neighbours");
                return true;
            }
        return false;
    }

    /**
     * Checks if the continents are connected
     *
     * @param p_mapData - the WZMap object that needs to be checked
     * @return true if the continent is a connected graph, false otherwise
     */
    public static boolean checkConnectedContinent(EditMap p_mapData) {
        boolean isContinentConnected = false;
        for (Map.Entry<Integer, Set<Integer>> entry : p_mapData.getCountriesInContinent().entrySet()) {
            Integer l_continentId = entry.getKey();
            Set<Integer> l_countryIDs = entry.getValue();
            if(p_mapData.getCountriesInContinent().get(l_continentId).size() == 1) {
                continue;
            }
            for (Integer l_countryID : l_countryIDs) {
                isContinentConnected=false;
                for (Integer l_neighbourID : p_mapData.getNeighbours().get(l_countryID)) {
                    if(Objects.equals(p_mapData.getContinentIdForCountry(l_neighbourID), p_mapData.getContinentIdForCountry(l_countryID))) {
                        isContinentConnected = true;
                        break;
                    }
                }
                if(!isContinentConnected) {
                    System.out.println("Continent " + l_continentId + " is not connected");
                    return false;
                }
            }
        }
        return true;
    }

    // TODO: Implement this for Playable map.
    public static PlayMap loadAndValidateMapForPlayMap(String p_fileName) {
        return new PlayMap();
        // Rename the function to something more sensible. At the end Return a playable map.
        // After validating the map, call the initGameMapElements() method before returning it.
        // Create objects / whatever similar to whats happening in EditMap.
        // Explicitly do the validation and create the class objects here. The addContinent and other add
        // functions are not available in this class. Figure out a way plis
    }
}