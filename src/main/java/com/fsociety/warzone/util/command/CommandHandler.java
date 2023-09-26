package com.fsociety.warzone.util.command;

import com.fsociety.warzone.model.orders.Deploy;
import com.fsociety.warzone.util.command.constant.GameplayCommand;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;


import java.util.*;

/**
 * This class handles all input commands.
 */
public class CommandHandler {
    private final String rawCommand;
    private String[] parsedCommand;

    private String commandName;

    private Phase phase;

    public CommandHandler(String rawCommand, Phase phase) {
        this.phase = phase;
        this.rawCommand = rawCommand;
        this.splitRawCommand();
        this.commandName = this.parsedCommand[0];
        if(!this.isCommandValid()) {
            throw new RuntimeException("Command not valid");
        }
    }

    private void splitRawCommand() {
        this.parsedCommand = this.rawCommand.split(" ");
    }

    private boolean isCommandValid() {
        if(this.parsedCommand.length == 0) {
            return false;
        }

        Set<String> validCommands = new HashSet<>();

        switch (this.phase) {
            case GAME_PLAY:
                for (GameplayCommand value : GameplayCommand.values()) {
                    validCommands.add(value.getCommand());
                }
                break;
            case MAP_EDITOR:
                for (MapEditorCommand value : MapEditorCommand.values()) {
                    validCommands.add(value.getCommand());
                }
                break;
            case START_UP:
                for (StartupCommand value : StartupCommand.values()) {
                    validCommands.add(value.getCommand());
                }
                break;
            default:
                return false;
        }


        if(!validCommands.contains(this.commandName)) {
            return false;
        }

        switch (this.phase) {
            case GAME_PLAY:
                return this.isCommandValidInGameplay();
            case MAP_EDITOR:
                break;
            case START_UP:
                break;
            default:
                return false;
        }

        return false;
    }

    private boolean isCommandValidInGameplay() {
        if(this.commandName.equals(GameplayCommand.BACK.getCommand())) {
            return this.parsedCommand.length == 1;
        }

        if(this.commandName.equals(GameplayCommand.DEPLOY.getCommand())) {
            return this.isDeployCommandValid(this.parsedCommand);
        }

        if(this.commandName.equals(GameplayCommand.SHOW_MAP.getCommand())) {
            return true;
        }
        return false;
    }

    private boolean isDeployCommandValid(String[] parsedCommand) {
        if(parsedCommand == null) {
            return false;
        }
        if(parsedCommand.length != 3) {
            return false;
        }

        int countryId , armies;
        try {
            countryId = Integer.parseInt(parsedCommand[1]);
            armies = Integer.parseInt(parsedCommand[2]);
        } catch(Exception e) {
            return false;
        }

        return countryId >= 0 && armies >= 0;
    }

    public Object getCommandObject() {
        if(this.commandName.equals(GameplayCommand.DEPLOY.getCommand())) {
            return new Deploy(Integer.parseInt(parsedCommand[1]), Integer.parseInt(parsedCommand[2]));
        }
        if(this.commandName.equals(GameplayCommand.BACK.getCommand())) {
            //TODO: handle back command somehow
            return null;
        }
        return null;
    }


    // Getters and Setters
}
