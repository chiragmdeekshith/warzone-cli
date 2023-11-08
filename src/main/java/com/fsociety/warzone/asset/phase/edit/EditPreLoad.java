package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.MapEditorController;
import com.fsociety.warzone.model.map.EditMap;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.util.MapTools;

public class EditPreLoad extends Edit {

    @Override
    public void help() {
        Command[] d_validCommands = {Command.EDIT_MAP, Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(d_validCommands);
        Console.print(help);
    }

    @Override
    public void editMap(String p_fileName) {
        EditMap l_editMap = MapTools.loadAndValidateEditableMap(p_fileName);
        if(null == l_editMap) {
            Console.print("Failed to load the map from file! Please try another map file.");
            return;
        }
        MapEditorController.setEditMap(l_editMap);
        Console.print("Map '" + p_fileName + "' is ready.");

        GameEngine.setPhase(new EditPostLoad());
    }
}
