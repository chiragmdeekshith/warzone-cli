package com.fsociety.warzone.util.command.validator;

import com.fsociety.warzone.util.command.constant.GameplayCommand;
import com.fsociety.warzone.util.command.constant.MapEditorCommand;
import com.fsociety.warzone.util.command.constant.Phase;
import com.fsociety.warzone.util.command.constant.StartupCommand;

import java.util.HashSet;
import java.util.Set;

public class CommandValidator {

    public static boolean isValidCommand(String p_rawCommand, Phase p_phase) {

        if(p_rawCommand.isEmpty()) {
            System.out.println("Command is empty. It cannot be empty.");
            return false;
        }

        String[] l_parsedCommand = p_rawCommand.split(" ");
        String l_commandType = l_parsedCommand[0];
        Set<String> l_validCommands = new HashSet<>();

        // Add phase specific commands to the set to ensure non-phase commands cannot be executed
        switch (p_phase) {
            case GAME_PLAY:
                for (GameplayCommand value : GameplayCommand.values()) {
                    l_validCommands.add(value.getCommand());
                }
                break;
            case MAP_EDITOR:
                for (MapEditorCommand value : MapEditorCommand.values()) {
                    l_validCommands.add(value.getCommand());
                }
                break;
            case START_UP:
                for (StartupCommand value : StartupCommand.values()) {
                    l_validCommands.add(value.getCommand());
                }
                break;
            default:
                System.out.println("You shouldn't be reaching this part of the code!");
                return false;
        }

        if(!l_validCommands.contains(l_commandType)) {
            System.out.println("This command is not valid in the " + p_phase + " phase.");
            return false;
        }

        // Validate commands in depth.
        return switch (p_phase) {
            case GAME_PLAY -> isCommandValidInGameplay(l_parsedCommand);
            case MAP_EDITOR -> isCommandValidInMapEditor(l_parsedCommand);
            case START_UP -> isCommandValidInStartup(l_parsedCommand);
        };

    }

    private static boolean isCommandValidInGameplay(String[] p_parsedCommand) {
        String l_commandType = p_parsedCommand[0];

        // Validate the BACK command
        if(l_commandType.equals(GameplayCommand.BACK.getCommand())) {
            return validateBackCommand(p_parsedCommand);
        }

        // Validate the DEPLOY command
        if(l_commandType.equals(GameplayCommand.DEPLOY.getCommand())) {
            if(p_parsedCommand.length != 3) {
                System.out.println("This command does not have required 3 arguments.");
                return false;
            }
            int countryId, armies;
            try {
                countryId = Integer.parseInt(p_parsedCommand[1]);
                armies = Integer.parseInt(p_parsedCommand[2]);
            } catch(NumberFormatException e) {
                System.out.println("This command is not valid because the second or third argument is not an integer.");
                return false;
            }
            if(countryId < 0 && armies < 0) {
                System.out.println("The countryId and armies fields have to be 0 or greater.");
                return false;
            }
            return true;
        }

        // Validate the SHOW_MAP command
        if(l_commandType.equals(GameplayCommand.SHOW_MAP.getCommand())) {
            return validateShowMapCommand(p_parsedCommand);
        }

        System.out.println("Unrecognised command in current phase");
        return false;
    }

    private static boolean isCommandValidInMapEditor(String[] p_parsedCommand) {
        String l_commandType = p_parsedCommand[0];

        // Validate the BACK command
        if(l_commandType.equals(MapEditorCommand.BACK.getCommand())) {
            return validateBackCommand(p_parsedCommand);
        }

        // Validate the EDIT_MAP command
        if(l_commandType.equals(MapEditorCommand.EDIT_MAP.getCommand())) {
            if(p_parsedCommand.length != 2){
                System.out.println("There are unexpected number of arguments passed.");
                return false;
            }
            String l_fileName = p_parsedCommand[1];
            if(!l_fileName.endsWith(".map")) {
                System.out.println("The file passed is not a '.map' file.");
                return false;
            }
            return true;
        }

        // Validate the EDIT_CONTINENT command
        if(l_commandType.equals(MapEditorCommand.EDIT_CONTINENT.getCommand())) {

            if(p_parsedCommand.length < 3 || p_parsedCommand.length > 4 ) {
                System.out.println("There are unexpected number of arguments passed.");
                return false;
            }

            String l_operation = p_parsedCommand[1];

            switch (l_operation) {
                case MapEditorCommand.ADD -> {
                    if(p_parsedCommand.length != 4) {
                        System.out.println("ADD needs two arguments - ID and Value");
                        return false;
                    }
                    int l_id, l_value;
                    try {
                        l_id = Integer.parseInt(p_parsedCommand[2]);
                        l_value = Integer.parseInt(p_parsedCommand[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid argument type passed - Integers required.");
                        return false;
                    }
                }
                case MapEditorCommand.REMOVE -> {
                    if(p_parsedCommand.length != 3) {
                        System.out.println("Remove needs one argument - ID");
                        return false;
                    }
                    int l_id;
                    try {
                        l_id = Integer.parseInt(p_parsedCommand[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid argument type passed - Integer required.");
                        return false;
                    }
                }
            }

            return true;
        }

        // Validate the EDIT_COUNTRY command
        if(l_commandType.equals(MapEditorCommand.EDIT_COUNTRY.getCommand())){

            if(p_parsedCommand.length != 3 && p_parsedCommand.length != 4){
                System.out.println("This command needs either 3 or for 4 arguments passed to it");
                return false;
            }

            String l_operation = p_parsedCommand[1];

            // Validate ADD country
            if(MapEditorCommand.ADD.equals(l_operation)) {
                if(p_parsedCommand.length != 4) {
                    System.out.println("Add country command requires two arguments");
                    return false;
                }
                int l_countryId, l_continentId;
                try {
                    l_countryId = Integer.parseInt(p_parsedCommand[2]);
                    l_continentId = Integer.parseInt(p_parsedCommand[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Integers are expected for countryId and continentId.");
                    return false;
                }
                if(l_countryId < 0 || l_continentId < 0) {
                    System.out.println("IDs cannot be less than 0.");
                    return false;
                }
                return true;
            }

            // Validate REMOVE country
            if(MapEditorCommand.REMOVE.equals(l_operation)) {
                if(p_parsedCommand.length != 3) {
                    System.out.println("Remove country command requires one argument");
                    return false;
                }
                int l_countryId;
                try {
                    l_countryId = Integer.parseInt(p_parsedCommand[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Integer is expected for countryId.");
                    return false;
                }
                if(l_countryId < 0) {
                    System.out.println("ID cannot be less than 0.");
                    return false;
                }
                return true;
            }

            System.out.println("Unrecognised flag: "+ l_operation);
            return false;
        }

        // Validate the EDIT_NEIGHBOUR command
        if(l_commandType.equals(MapEditorCommand.EDIT_NEIGHBOUR.getCommand())) {
            if(p_parsedCommand.length != 4) {
                System.out.println("This command needs one flag and two arguments");
                return false;
            }
            String l_operation = p_parsedCommand[1];
            if(!MapEditorCommand.ADD.equals(l_operation) && !MapEditorCommand.REMOVE.equals(l_operation)) {
                System.out.println("Unexpected flag " + l_operation);
            }
            int l_selfId, l_neighbourId;
            try {
                l_selfId = Integer.parseInt(p_parsedCommand[2]);
                l_neighbourId = Integer.parseInt(p_parsedCommand[3]);
            } catch (NumberFormatException e) {
                System.out.println("Integers expected as ID values");
                return false;
            }
            if(l_selfId < 0 || l_neighbourId < 0) {
                System.out.println("IDs must be 0 or greater.");
                return false;
            }
            return true;
        }

        // Validate the VALIDATE_MAP command
        if(l_commandType.equals(MapEditorCommand.VALIDATE_MAP.getCommand())) {
            if(p_parsedCommand.length != 1) {
                System.out.println("This command has too many arguments, so its invalid");
                return false;
            }
            return true;
        }

        // Validate the SAVE_MAP command
        if(l_commandType.equals(MapEditorCommand.SAVE_MAP.getCommand())) {
            if(p_parsedCommand.length != 2){
                System.out.println("Unexpected number of arguments passed. ");
                return false;
            }
            String l_fileName = p_parsedCommand[1];
            if(!l_fileName.endsWith(".map")) {
                System.out.println("The filename passed is not a '.map' file.");
                return false;
            }
            return true;
        }

        // Validate the SHOW_MAP command
        if(l_commandType.equals(MapEditorCommand.SHOW_MAP.getCommand())) {
            return validateShowMapCommand(p_parsedCommand);
        }

        System.out.println("Unrecognised command in current phase");
        return false;
    }

    private static boolean isCommandValidInStartup(String[] p_parsedCommand) {
        String l_commandType = p_parsedCommand[0];

        // Validate the BACK command
        if(l_commandType.equals(StartupCommand.BACK.getCommand())) {
            return validateBackCommand(p_parsedCommand);
        }

        // Validate the GAME_PLAYER command
        if(l_commandType.equals(StartupCommand.GAME_PLAYER.getCommand())) {

            if(p_parsedCommand.length < 3 || p_parsedCommand.length%2 != 1) {
                System.out.println("This command requires pairs of add/remove options and player names.");
                return false;
            }

            for (int i = 1; i < p_parsedCommand.length; i+=2) {
                String l_operation = p_parsedCommand[i];
                if(!StartupCommand.ADD.equals(l_operation) && !StartupCommand.REMOVE.equals(l_operation)) {
                    System.out.println("Unrecognised operation " + l_operation);
                    return false;
                }
            }

            for (int i = 2; i < p_parsedCommand.length; i+=2) {
                String l_playerName = p_parsedCommand[i];
                if (!l_playerName.matches("[A-Za-z0-9]+")) {
                    System.out.println("Player name must be alphanumeric");
                    return false;
                }
            }

            return true;
        }

        // Validate the ASSIGN_COUNTRIES command
        if(l_commandType.equals(StartupCommand.ASSIGN_COUNTRIES.getCommand())) {
            if(p_parsedCommand.length != 1) {
                System.out.println("This command has no arguments");
                return false;
            }
            return true;
        }

        // Validate the LOAD_MAP command
        if(l_commandType.equals(StartupCommand.LOAD_MAP.getCommand())) {
            if(p_parsedCommand.length != 2){
                System.out.println("Expected one argument - filename");
                return false;
            }
            String l_fileName = p_parsedCommand[1];
            if(!l_fileName.endsWith(".map")) {
                System.out.println("The file passed is not a '.map' file.");
                return false;
            }
            return true;
        }

        // Validate the SHOW_MAP command
        if(l_commandType.equals(StartupCommand.SHOW_MAP.getCommand())) {
            return validateShowMapCommand(p_parsedCommand);
        }

        System.out.println("Unrecognised Command in current phase");
        return false;
    }

    private static boolean validateShowMapCommand(String[] p_parsedCommand) {
        if(p_parsedCommand.length != 1) {
            System.out.println("This command has too many arguments, so its invalid");
            return false;
        }
        return true;
    }

    private static boolean validateBackCommand(String[] p_parsedCommand) {
        if(p_parsedCommand.length != 1) {
            System.out.println("This command has too many arguments, so its invalid");
            return false;
        }
        return true;
    }

}
