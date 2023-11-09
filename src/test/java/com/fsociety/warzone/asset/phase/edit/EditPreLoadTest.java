package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.command.CommandValidator;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.controller.GameplayController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of phase transition when a map file is loaded for
 * editing
 */
class EditPreLoadTest {

    /**
     * The Phase object used for testing
     */
    Phase d_phase;

    /**
     * Map file names for tests
     */
    String d_validMapFileName , d_invalidMapFileName;

    /**
     * The setup function to reset the phase
     */
    @BeforeEach
    void setUp() {
        d_validMapFileName = "1.map";
        d_invalidMapFileName = "duplicateNeighbours.map";
        d_phase = new EditPreLoad();
        GameEngine.setPhase(d_phase);
    }

    /**
     * Test to ensure that it remains in the same state if the map file is not loaded
     */
    @Test
    public void testEditMap_InvalidMap() {
        d_phase.editMap(d_invalidMapFileName);
        assertEquals(d_phase.getClass(), GameEngine.getPhase().getClass());
    }

    /**
     * Test to ensure that it doesn't remain in the same state if the map file is
     * loaded correctly
     */
    @Test
    public void testEditMap_success() {
        d_phase.editMap(d_validMapFileName);
        assertEquals(EditPostLoad.class, GameEngine.getPhase().getClass());
    }

    /**
     * Test to ensure that it doesn't remain in the same state if BACK command is given by the user
     */
    @Test
    public void testBack() {
        d_phase.back();
        assertEquals(Menu.class, GameEngine.getPhase().getClass());
    }
}