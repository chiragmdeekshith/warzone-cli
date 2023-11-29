package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.MapEditorController;
import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.util.map.ConquestMapTools;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.util.map.DominationMapTools;
import com.fsociety.warzone.util.map.MapAdapter;

/**
 * This Class implements the commands that are valid for map editing before a map has been loaded.
 */
public class EditPreLoad extends Edit {

    /**
     * The Adapted mapTools that is used to load and validate the map.
     */
    private DominationMapTools d_mapTools = new MapAdapter(new ConquestMapTools());

    /**
     * This method compiles and prints a help message of valid commands for the EditPreLoad phase when the 'help'
     * command is entered.
     */
    @Override
    public void help() {
        Command[] d_validCommands = {Command.EDIT_MAP, Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    /**
     * This method allows the user to load a map by entering the 'editmap' command.
     * @param p_fileName the name of the map file to load
     */
    @Override
    public void editMap(String p_fileName) {
        EditMap l_editMap = d_mapTools.loadAndValidateEditableMap(p_fileName);
        if(null == l_editMap) {
            Console.print("Failed to load the map from file! Please try another map file.");
            return;
        }
        MapEditorController.setEditMap(l_editMap);
        Console.print("Map '" + p_fileName + "' is ready.");

        GameEngine.setPhase(new EditPostLoad());
    }
}
