package com.fsociety.warzone.util.command.constant;

public enum MapEditorCommand {
    BACK("back"),
    EDIT_MAP("editmap"),
    EDIT_CONTINENT("editcontinent"),
    EDIT_COUNTRY("editcountry"),
    EDIT_NEIGHBOUR("editneighbor"), // Typo is intentional
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
