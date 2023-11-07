package com.fsociety.warzone.phase.play.playsetup;

import com.fsociety.warzone.GameRunner;
import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.map.PlayMap;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.util.MapTools;

public class PlayPreLoad extends PlaySetup {

    @Override
    public void help() {
        Command[] l_validCommands = {Command.LOAD_MAP, Command.BACK, Command.EXIT};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(help);
    }

    @Override
    public void loadMap(String p_fileName) {
        PlayMap l_playMap = MapTools.loadAndValidatePlayableMap(p_fileName);
        if(null == l_playMap) {
            Console.print("Failed to load the map! Please try another map file.");
            return;
        }
        GameEngine.setPlayMap(l_playMap);
        Console.print("Loaded map \"" + p_fileName + "\"");
        GameRunner.setPhase(new PlayPostLoad());
    }
}
