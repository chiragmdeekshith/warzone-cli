package com.fsociety.warzone.asset.phase.edit;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Menu;
import com.fsociety.warzone.asset.phase.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditPostLoadTest {

    /**
     * The Phase object used for testing
     */
    Phase d_phase;

    /**
     * The setup function to reset the phase
     */
    @BeforeEach
    void setUp() {
        d_phase = new EditPostLoad();
        GameEngine.setPhase(d_phase);
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