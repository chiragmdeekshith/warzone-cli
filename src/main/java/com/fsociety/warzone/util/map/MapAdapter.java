package com.fsociety.warzone.util.map;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.view.Console;

import java.io.BufferedReader;
import java.io.FileReader;

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
     * @param conquestMap - conquest map object
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
        String l_mapType = getMapType(p_fileName);
        if(Command.MAP_OPTION_CONQUEST.equals(l_mapType)) {
            return conquestMap.loadAndValidateEditableMap(p_fileName);
        } if(Command.MAP_OPTION_DOMINATION.equals(l_mapType)) {
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
        String l_mapType = getMapType(p_fileName);
        if(Command.MAP_OPTION_CONQUEST.equals(l_mapType)) {
            return conquestMap.loadAndValidatePlayableMap(p_fileName);
        } if(Command.MAP_OPTION_DOMINATION.equals(l_mapType)) {
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
    @Override
    public boolean saveMapFile(EditMap p_mapData, String p_fileNameForSave, String p_fileType) {
        if (Command.MAP_OPTION_CONQUEST.equals(p_fileType)) {
            return conquestMap.saveMapFile(p_mapData,p_fileNameForSave,p_fileType);
        } else if(Command.MAP_OPTION_DOMINATION.equals(p_fileType)) {
            return super.saveMapFile(p_mapData,p_fileNameForSave,p_fileType);
        }
        return false;
    }

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
                return Command.MAP_OPTION_CONQUEST;
            } else if (l_data.toString().startsWith(";")) {
                return Command.MAP_OPTION_DOMINATION;
            }
        } catch (Exception e) {
            Console.print("File does not exist!");
            return null;
        }
        //Should never reach here
        return null;
    }
}