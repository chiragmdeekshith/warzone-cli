package com.fsociety.warzone.util;

import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.view.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

/**
 * The class that is used to implement Adapter Pattern for map files.
 */
public class MapAdapter extends DominationMapTools {

    /**
     * The ConquestMapTools object that is getting adapted.
     */
    private static ConquestMapTools conquestMap;

    /**
     * The constructor for the MapAdapter class.
     * @param conquestMap
     */
    public MapAdapter(ConquestMapTools conquestMap) {
        super();
        this.conquestMap = conquestMap;
    }

    /**
     * Loading and Validating Edit Map that is overridden according to the Adapter Pattern.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the EditMap object ready
     */
    @Override
    public EditMap loadAndValidateEditableMap(String p_fileName) {
        if(Objects.equals(getMapType(p_fileName), "conquest")) {
            return conquestMap.loadAndValidateEditableMap(p_fileName);
        } if(Objects.equals(getMapType(p_fileName), "domination")) {
            return super.loadAndValidateEditableMap(p_fileName);
        }
        return null;
    }

    /**
     * Loading and Validating PLay Map that is overridden according to the Adapter Pattern.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the PlayMap object ready
     */
    @Override
    public PlayMap loadAndValidatePlayableMap(String p_fileName) {
        if(Objects.equals(getMapType(p_fileName), "conquest")) {
            return conquestMap.loadAndValidatePlayableMap(p_fileName);
        } if(Objects.equals(getMapType(p_fileName), "domination")) {
            return super.loadAndValidatePlayableMap(p_fileName);
        }
        return null;
    }

    /**
     * Save map method that is overriden according to the Adapter Pattern by input given by user.
     *
     * @param p_mapData - the map data to be saved,
     * @param p_fileNameForSave - the name of the file to be saved
     * @param p_fileType - the type of the file to be saved either conquest or domination
     * @return true if the file was saved successfully, false otherwise
     */
//    @Override
//    public boolean saveMapFile(EditMap p_mapData, String p_fileNameForSave, String p_fileType) {
//        if (p_fileType.equalsIgnoreCase("conquest")) {
//            return conquestMap.saveMapFile(p_mapData,p_fileNameForSave,p_fileType);
//        } else if(p_fileType.equalsIgnoreCase("domination")) {
//            return super.saveMapFile(p_mapData,p_fileNameForSave,p_fileType);
//        }
//        return false;
//    }

    /**
     * This method is used to get the map type.
     *
     * @param p_fileName - name of the file
     * @return the type of map
     */
    public static String getMapType(String p_fileName) {
        try {
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
            if (l_data.toString().toLowerCase().contains("[map]")) {
                return ("conquest");
            } else if (l_data.toString().startsWith(";")) {
                return ("domination");
            }
        } catch (Exception e) {
            Console.print("File does not exist!");
            return null;
        }
        //Should never reach here
        return null;
    }
}