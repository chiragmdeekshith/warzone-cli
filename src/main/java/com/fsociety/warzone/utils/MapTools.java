package com.fsociety.warzone.utils;

import com.fsociety.warzone.models.map.AbstractMap;
import com.fsociety.warzone.models.map.EditMap;
import com.fsociety.warzone.controllers.GamePlayController;
import com.fsociety.warzone.models.map.PlayMap;

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
     * Reads the file from system and loads it into the EditMap. Verifies the format of the map file before loading it in and then
     * logically validates it again before returning it.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the EditMap object ready
     */
    public static EditMap loadAndValidateEditableMap(String p_fileName) {
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
            if (validateFileFormat(l_editMap, data, p_fileName)) {
                if (!loadDataFromFile(l_editMap, data)) {
                    return null;
                }
            }
            else {
                return null;
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
     * Reads the file from system and loads it into the PlayMap. Verifies the format of the map file before loading it in and then
     * logically validates it again before returning it.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the PlayMap object ready
     */
    public static PlayMap loadAndValidatePlayableMap(String p_fileName) {
        PlayMap l_playMap;
        EditMap l_loadMap;
        try {
            l_loadMap = new EditMap();
            String l_filePath = "src/main/resources/" + p_fileName;
            FileReader mapFile;
            mapFile = new FileReader(l_filePath);
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
            if (validateFileFormat(l_loadMap, data, p_fileName)) {
                if (!loadDataFromFile(l_loadMap, data)) {
                    return null;
                }
                l_playMap = new PlayMap(l_loadMap);
            }
            else {
                return null;
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
        if(validateMap(l_playMap)) {
            l_playMap.initGameMapElements();
            return l_playMap;
        }
        return null;
    }

    /**
     *
     * This functions checks if the file is in the correct format
     *
     * @param p_map - the Map object that we need to load data into
     * @param p_data - the StringBuilder object that contains the data from the file
     * @return true if the data is loaded in correctly, false otherwise
     */
    public static boolean loadDataFromFile(EditMap p_map, StringBuilder p_data) {
        String[] continentData = p_data.substring(p_data.toString().toLowerCase().indexOf("[continents]")+13, p_data.toString().toLowerCase().indexOf("[countries]")).split("\n");
        String[] countryData = p_data.substring(p_data.toString().toLowerCase().indexOf("[countries]")+12, p_data.toString().toLowerCase().indexOf("[borders]")).split("\n");
        String[] neighborData = p_data.substring(p_data.toString().toLowerCase().indexOf("[borders]")+10).split("\n");
        int l_counter = 0;
        for(String s:continentData) {
            int l_continentId = ++l_counter;
            String[] l_splitData = s.split(" ");
            int l_continentBonusValue = Integer.parseInt(l_splitData[1]);
            p_map.addContinent(l_continentId, l_continentBonusValue);
        }
        for(String s:countryData) {
            String[] l_splitData = s.split(" ");
            int l_countryId = Integer.parseInt(l_splitData[0]);
            int l_continentId = Integer.parseInt(l_splitData[2]);
            p_map.addCountry(l_countryId, l_continentId);
        }
        for(String s:neighborData) {
            String[] temp = s.split(" ");
            int[] arr = new int[temp.length];
            int countryID= Integer.parseInt(temp[0]);
            for (int i=1;i<temp.length;i++) {
                if(Arrays.binarySearch(arr,Integer.parseInt(temp[i]))>0) {
                    System.out.println("Duplicate Neighbors cannot exist");
                    return false;
                }
                arr[i] = Integer.parseInt(temp[i]);
                p_map.addNeighbour(countryID,arr[i]);
            }
        }
        return true;
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
     *
     * This functions checks if the file is in the correct format
     *
     * @param p_map - the EditMap object that we need to check format of
     * @param p_data - the StringBuilder object that contains the data from the file
     * @param p_fileName - name of the file
     * @return the EditMap object if the file is in the correct format, null otherwise
     */
    public static boolean validateFileFormat(AbstractMap p_map, StringBuilder p_data, String p_fileName) {
        if (p_data.toString().toLowerCase().contains("[continents]") && p_data.toString().toLowerCase().contains("[countries]") && p_data.toString().toLowerCase().contains("[borders]")) {
            p_map.setFileName(p_fileName);
            return true;
        } else {
            System.out.println("Missing Information/Not in correct format");
            return false;
        }
    }

    /**
     * Logically validates the WZMap object
     *
     * @param p_mapData - the WZMap object that needs to be validated
     * @return true if the map is valid, false otherwise
     */
    public static boolean validateMap(AbstractMap p_mapData) {
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
    public static boolean checkEmptyContinent(AbstractMap p_mapData) {
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
    public static boolean checkEmptyNeighbours(AbstractMap p_mapData) {
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
    public static boolean checkConnectedContinent(AbstractMap p_mapData) {
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

}