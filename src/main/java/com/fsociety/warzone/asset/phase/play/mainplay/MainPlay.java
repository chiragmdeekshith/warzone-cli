package com.fsociety.warzone.asset.phase.play.mainplay;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.asset.phase.play.Play;
import com.fsociety.warzone.model.GameSaveData;
import com.fsociety.warzone.util.GameSaveUtil;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands common to the Reinforcement and Attack phases.
 */
public abstract class MainPlay extends Play {

    /**
     * This method calls the map to be printed during gameplay using the 'showmap' command.
     */
    @Override
    public void showMap() {
        GameplayController.getPlayMap().showMap();
    }

    /**
     * This method allows the player to print their cards by using the 'showcards' command.
     */
    @Override
    public void showCards() {
        IssueOrder.getCurrentPlayer().getHandOfCards().showCards();
    }

    /**
     * This method prints the players active in the game using the 'showplayers' command.
     */
    @Override
    public void showPlayers() {
        GameplayController.printPlayers();
    }

    /**
     * This method prints out the invalid command message when the 'savegame' command is used outside of the
     * MainPlay phase.
     */
    @Override
    public void saveGame(String p_fileName) {
        GameSaveData l_gameSaveData = new GameSaveData();

        l_gameSaveData.setGameWon(GameplayController.getGameWon());
        l_gameSaveData.setPlayMap(GameplayController.getPlayMap());
        l_gameSaveData.setCurrentTournament(GameplayController.getCurrentTournament());
        l_gameSaveData.setPlayerNameMap(GameplayController.getPlayerNameMap());
        l_gameSaveData.setTruces(GameplayController.getTruces());
        l_gameSaveData.setPlayerIdMap(GameplayController.getPlayerIdMap());
        l_gameSaveData.setPlayers(GameplayController.getPlayers());
        l_gameSaveData.setWinner(GameplayController.getWinner());
        l_gameSaveData.setTurns(GameplayController.getTurns());
        l_gameSaveData.setNeutralPlayer(GameplayController.getNeutralPlayer());
        l_gameSaveData.setCurrentPhase(GameEngine.getPhase());

        if(GameSaveUtil.saveGameToFile(l_gameSaveData, p_fileName)) {
            Console.print("Game saved to " +p_fileName+ " successfully!");
        } else {
            Console.print("Game could not be saved. Error writing to file!");
        }
    }

    /**
     * This method allows the user to return to the main menu during gameplay using the 'back' command.
     * By setting the GameplayController's static variable flag
     */
    @Override
    public void back() {
        GameplayController.setBackCommandIssued();
    }

}
