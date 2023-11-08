package com.fsociety.warzone.asset.phase.play.mainplay;

import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.asset.phase.play.Play;

public abstract class MainPlay extends Play {
    @Override
    public void showMap() {
        GameplayController.getPlayMap().showMap();
    }

    @Override
    public void showCards() {
        IssueOrder.getCurrentPlayer().getHandOfCards().showCards();
    }

    @Override
    public void showPlayers() {
        GameplayController.printPlayers();
    }

}
