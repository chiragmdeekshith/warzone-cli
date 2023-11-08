package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.models.map.PlayMap;
import com.fsociety.warzone.utils.Console;
import com.fsociety.warzone.utils.MapTools;

public class PlayPreLoad extends PlaySetup {

    @Override
    public void help() {
        Command[] l_validCommands = {Command.LOAD_MAP, Command.BACK, Command.EXIT};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }

    @Override
    public void loadMap(String p_fileName) {
        PlayMap l_playMap = MapTools.loadAndValidatePlayableMap(p_fileName);
        if(null == l_playMap) {
            Console.print("Failed to load the map! Please try another map file.");
            return;
        }
        GameEngineController.setPlayMap(l_playMap);
        Console.print("Loaded map \"" + p_fileName + "\"");
        GameRunner.setPhase(new PlayPostLoad());
    }
}
