package com.fsociety.warzone.util.command.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum MapEditorCommand {
    BACK("back"),
    EDIT_MAP("editmap"),
    EDIT_CONTINENT("editcontinent"),
    EDIT_COUNTRY("editcountry"),
    EDIT_NEIGHBOUR("editneighbour"),
    SAVE_MAP("savemap"),
    VALIDATE_MAP("validatemap"),
    SHOW_MAP("showmap");

    // Arguments for commands
    public static final String ADD = "-add";
    public static final String REMOVE = "-remove";

    private final String d_command;

    MapEditorCommand(String p_command){
        this.d_command = p_command;
    }

    public String getCommand() {
        return d_command;
    }


}