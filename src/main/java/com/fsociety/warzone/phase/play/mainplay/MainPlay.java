package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.phase.play.Play;

public abstract class MainPlay extends Play {
    @Override
    public void showMap() {
        GameEngine.getPlayMap().showMap();
    }

}
