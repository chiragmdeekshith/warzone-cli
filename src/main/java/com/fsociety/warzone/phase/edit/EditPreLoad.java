package com.fsociety.warzone.phase.edit;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.controllers.MapEditorController;
import com.fsociety.warzone.models.map.EditMap;
import com.fsociety.warzone.utils.Console;
import com.fsociety.warzone.utils.MapTools;

public class EditPreLoad extends Edit {

    @Override
    public void help() {
        Command[] d_validCommands = {Command.SHOW_MAP, Command.EDIT_MAP, Command.BACK, Command.EXIT};
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

        GameRunner.setPhase(new EditPostLoad());
    }
}
