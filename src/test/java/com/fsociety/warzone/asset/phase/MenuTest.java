package com.fsociety.warzone.asset.phase;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.edit.EditPostLoad;
import com.fsociety.warzone.asset.phase.edit.EditPreLoad;
import com.fsociety.warzone.asset.phase.play.playsetup.PlayPreLoad;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    /**
     * The Phase object used for testing
     */
    Phase d_phase;

    /**
     * Map file names for tests
     */
    @BeforeEach
    void setUp() {
        d_phase = new Menu();
        GameEngine.setPhase(d_phase);
    }

    /**
     * Test to ensure that it doesn't remain in the same state
     */
    @Test
    public void testPlayGame() {
        d_phase.playGame();
        assertEquals(PlayPreLoad.class, GameEngine.getPhase().getClass());
    }

    /**
     * Test to ensure that it doesn't remain in the same state
     */
    @Test
    public void testMapEditor() {
        d_phase.mapEditor();
        assertEquals(EditPreLoad.class, GameEngine.getPhase().getClass());
    }

}