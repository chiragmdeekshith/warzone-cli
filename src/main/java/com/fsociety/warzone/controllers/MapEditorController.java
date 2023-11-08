package com.fsociety.warzone.controllers;

import com.fsociety.warzone.models.map.EditMap;
import com.fsociety.warzone.utils.MapTools;

/**
 * This class handles everything related to Editing maps. This class can be thought of as a Map Engine.
 */
public class MapEditorController {
    private static EditMap d_editMap;

    public static void resetMapEditor() {
        d_editMap = null;
    }

    public static boolean validateMap() {
        return MapTools.validateMap(d_editMap);
    }

    public static boolean saveMap(String p_fileName) {
        return MapTools.saveMapFile(d_editMap, p_fileName);
    }

    public static EditMap getEditMap() {
        return d_editMap;
    }

    public static void setEditMap(EditMap p_editMap) {
        d_editMap = p_editMap;
    }
}
