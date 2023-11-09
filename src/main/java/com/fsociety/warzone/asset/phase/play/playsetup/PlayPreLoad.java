package com.fsociety.warzone.asset.phase.play.playsetup;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.map.PlayMap;
import com.fsociety.warzone.view.Console;
import com.fsociety.warzone.util.MapTools;

/**
 * This Class implements the commands that are valid during the Start-Up phase of gameplay before a map has been loaded.
 */
public class PlayPreLoad extends PlaySetup {

    /**
     * This method compiles and prints a help message of valid commands for the PlayPreLoad phase when the 'help'
     * command is entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.LOAD_MAP, Command.BACK, Command.EXIT};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands);
        Console.print(l_help);
    }

    /**
     * This method allows the user to load a map by entering the 'loadmap' command.
     * @param p_fileName the name of the map file to load
     */
    @Override
    public void loadMap(String p_fileName) {
        PlayMap l_playMap = MapTools.loadAndValidatePlayableMap(p_fileName);
        if(null == l_playMap) {
            Console.print("Failed to load the map! Please try another map file.");
            return;
        }
        GameplayController.setPlayMap(l_playMap);
        Console.print("Loaded map \"" + p_fileName + "\"");
        GameEngine.setPhase(new PlayPostLoad());
    }
}
