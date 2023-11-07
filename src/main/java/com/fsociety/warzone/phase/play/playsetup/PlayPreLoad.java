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
        Command[] l_validCommands = {Command.LOAD_MAP};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }

    @Override
    public void loadMap(String p_fileName) {
        PlayMap l_playMap = MapTools.loadAndValidatePlayableMap(p_fileName);
        if(null == l_playMap) {
            return;
        }
        GameEngine.setPlayMap(l_playMap);
        GameRunner.setPhase(new PlayPostLoad());
    }
}
