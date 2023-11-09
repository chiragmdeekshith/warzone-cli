package com.fsociety.warzone.controller;

import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.util.MapTools;

/**
 * This class handles everything related to editing maps by acting as an interface between the user and the Map Tools
 * class.
 */
public class MapEditorController {
    private static EditMap d_editMap;

    /**
     * This map resets the map being worked on to null.
     */
    public static void resetMapEditor() {
        d_editMap = null;
    }

    /**
     * This method allows the user to validate the map they are currently working on.
     * @return true if the map is properly validated, false otherwise
     */
    public static boolean validateMap() {
        return MapTools.validateMap(d_editMap);
    }

    /**
     * This method allows the use to save the map being worked on to a file.
     * @param p_fileName the file name to be saved to
     * @return true if the file was saved successfully, false otherwise
     */
    public static boolean saveMap(String p_fileName) {
        return MapTools.saveMapFile(d_editMap, p_fileName);
    }

    // Getters and setters

    /**
     * Get the map being edited.
     * @return the map
     */
    public static EditMap getEditMap() {
        return d_editMap;
    }

    /**
     * Set the map being edited.
     * @param p_editMap the map to be edited
     */
    public static void setEditMap(EditMap p_editMap) {
        d_editMap = p_editMap;
    }
}
