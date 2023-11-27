package com.fsociety.warzone.util;

import com.fsociety.warzone.model.map.AbstractMap;
import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.view.Console;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;

/**
 * This class provides all the functionality to read from Domination type .map files and store them in Map Objects.
 * It can also save the map into a new file.
 */
public class DominationMapTools {

    /**
     * Reads the file from system and loads it into the EditMap. Verifies the format of the map file before loading it in and then
     * logically validates it again before returning it.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the EditMap object ready
     */
    public static EditMap loadAndValidateEditableMap(String p_fileName) {
        EditMap l_editMap;
        String l_mapType = "domination";
        try {
            // Read the file line by line
            l_editMap = new EditMap();
            String l_filePath = "src/main/resources/maps/" + p_fileName;
            FileReader l_fileReader;
            try {
                l_fileReader = new FileReader(l_filePath);
            } catch (FileNotFoundException e) {
                File l_newFile = new File(l_filePath);
                boolean l_success = l_newFile.createNewFile();
                if(!l_success) {
                    throw new RuntimeException("Failed to create file.");
                }
                l_editMap.setFileName(p_fileName);
                return l_editMap;
            }
            String l_line;
            StringBuilder l_data = new StringBuilder();
            BufferedReader l_mapReader = new BufferedReader(l_fileReader);
            l_line = l_mapReader.readLine();
            while(l_line !=null) {
                if(!l_line.equals("\n")) {
                    l_data.append(l_line).append("\n");
                    l_line = l_mapReader.readLine();
                }
            }
            if (validateFileFormat(l_editMap, l_data, p_fileName)) {
                if (!MapReader.loadDataFromFile(l_editMap, l_data,l_mapType)) {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (NumberFormatException e) {
            Console.print("Cannot parse integer.");
            return null;
        }
        catch (Exception e) {
            Console.print("File does not exist!");
            return null;
        }
        if(MapValidator.validateMap(l_editMap)) {
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
        String l_mapType = "domination";
        try {
            l_playMap = new PlayMap();
            String l_filePath = "src/main/resources/maps/" + p_fileName;
            FileReader l_mapFile;
            l_mapFile = new FileReader(l_filePath);
            String l_line;
            StringBuilder l_data = new StringBuilder();
            BufferedReader l_mapReader = new BufferedReader(l_mapFile);
            l_line = l_mapReader.readLine();
            while(l_line !=null) {
                if(!l_line.equals("\n")){
                    l_data.append(l_line).append("\n");
                    l_line = l_mapReader.readLine();
                }
            }
            if (validateFileFormat(l_playMap, l_data, p_fileName)) {
                if (!MapReader.loadDataFromFile(l_playMap, l_data, l_mapType)) {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (NumberFormatException e) {
            Console.print("Cannot parse integer.");
            return null;
        }
        catch (Exception e) {
            Console.print("File does not exist!");
            return null;
        }
        if(MapValidator.validateMap(l_playMap)) {
            l_playMap.initGameMapElements();
            return l_playMap;
        }
        return null;
    }

    /**
     *
     * This functions validates the map before saving into a physical file on the system.
     *
     * @param p_mapData - the EditMap object to save to the file
     * @param p_fileNameForSave - name of the new save file
     * @return true if the file was saved successfully, false otherwise
     */
    public static boolean saveMapFile(EditMap p_mapData, String p_fileNameForSave) {

        // Ensure the map is valid
        if(!MapValidator.validateMap(p_mapData)) {
            return false;
        }

        // Serialise the data
        StringBuilder l_data = new StringBuilder();
        l_data.append("\n;map: ").append(p_mapData.getFileName()).append("\n").append("\n[continents]\n");
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
            l_write = new PrintWriter("src/main/resources/maps/"+p_fileNameForSave);
            l_write.write(String.valueOf(l_data));
            l_write.close();
            return true;
        }
        catch (Exception e) {
            Console.print(e.getMessage());
            return false;
        }
    }

    /**
     *
     * This functions checks if the file is in the correct format.
     *
     * @param p_map - the AbstractMap object that we need to check format of
     * @param p_data - the StringBuilder object that contains the data from the file
     * @param p_fileName - name of the file
     * @return true if the file is in the correct format, false otherwise
     */
    public static boolean validateFileFormat(AbstractMap p_map, StringBuilder p_data, String p_fileName) {
        if (p_data.toString().startsWith(";")&&p_data.toString().toLowerCase().contains("[continents]") && p_data.toString().toLowerCase().contains("[countries]") && p_data.toString().toLowerCase().contains("[borders]")) {
            p_map.setFileName(p_fileName);
            return true;
        } else {
            Console.print("File is missing information or is in the wrong format.");
            return false;
        }
    }
}