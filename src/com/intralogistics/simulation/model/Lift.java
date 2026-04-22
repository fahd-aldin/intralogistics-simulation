package com.intralogistics.simulation.model;

public class Lift {
    private final LiftType type;

    public Lift(LiftType type) {
        this.type = type;
    }

    public LiftType getType() {
        return type;
    }
}