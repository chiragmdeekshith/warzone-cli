package com.fsociety.warzone.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    void generateId() {
        Assertions.assertEquals(1, IdGenerator.generateId());
    }
}