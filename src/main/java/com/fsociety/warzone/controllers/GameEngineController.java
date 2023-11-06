package com.fsociety.warzone.controllers;

import com.fsociety.warzone.models.command.ExecuteOrder;
import com.fsociety.warzone.models.command.IssueOrder;
import com.fsociety.warzone.models.command.AssignReinforcements;
import com.fsociety.warzone.models.Player;
import com.fsociety.warzone.models.map.EditMap;
import com.fsociety.warzone.models.map.PlayMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles everything related to playing a game of Warzone.
 */
public class GameEngineController {

    private static PlayMap d_playMap;
    private static MapEditorController d_mapEditorController;
    private static GamePlayController d_gamePlayController;
    /**
     * Parameterized constructor getting values of the map from the EditMap object
     */
    public GameEngineController(EditMap l_loadMap) {
        this.d_mapEditorController = new MapEditorController();
        this.d_gamePlayController = new GamePlayController();
    }

    /**
     * This method implements a game engine by running the start-up phase, and upon success, the main loop that
     * implements the gameplay. The return statement allows the user to return to the main menu if the 'back'
     * command is used within the startUp() or mainLoop() methods.
     */
    public static void playGame() {
        if(!d_gamePlayController.startUp()){
            return; // Return to main menu
        }
        mainLoop();
    }

    /**
     * This method implements the loop through the three main game phases: Assign Reinforcements, Issue Orders, and
     * Execute Orders. At each phase, each player is looped over and the corresponding method is called.
     */
    public static void mainLoop() {
        System.out.println("Game Start!");
        int l_turns = 0;
        while (true) {
            l_turns++;
            System.out.println("Turn " + l_turns);

            // Get updated continent owner for each continent
            d_playMap.getContinents().keySet().forEach(continentId -> {
                d_playMap.getContinents().get(continentId).computeAndSetContinentOwner();
            });
            // Assign Reinforcements Phase
            for (Player l_player : d_playMap.getPlayers()) {
                AssignReinforcements.assignReinforcements(l_player);
            }

            // Issue Orders Phase
            if (!IssueOrder.issueOrders(d_playMap.getPlayers())) {
                return; // Return to main menu
            }

            System.out.println("All players have deployed their reinforcements.");
            System.out.println("Executing orders...");

            // Execute Orders Phase
            ExecuteOrder.executeOrders(d_playMap.getPlayers());

            System.out.println("All orders executed. Turn " + l_turns + " over.");
        }

    }

}
