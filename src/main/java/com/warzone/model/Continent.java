package com.warzone.model;

import com.warzone.util.IdGenerator;

public class Continent {
    private final Long id;
    private final String name;

    public Continent(final String name) {
        this.id = IdGenerator.generateId();
        this.name = name;
    }

    //
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
