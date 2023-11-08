package com.fsociety.warzone.asset.phase.play.mainplay;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.asset.phase.play.Play;

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

}
