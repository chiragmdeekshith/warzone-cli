package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.controllers.GameEngineController;
import com.fsociety.warzone.models.command.IssueOrder;
import com.fsociety.warzone.phase.play.Play;

public abstract class MainPlay extends Play {
    @Override
    public void showMap() {
        GameEngineController.getPlayMap().showMap();
    }

    @Override
    public void showCards() {
        IssueOrder.getCurrentPlayer().getHandOfCards().showCards();
    }

    @Override
    public void showPlayers() {
        GameEngineController.printPlayers();
    }

}
