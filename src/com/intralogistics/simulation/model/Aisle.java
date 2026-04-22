package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class Aisle {
    private final int number;
    private final Lift inboundLift;
    private final Lift outboundLift;
    private final List<Level> levels;

    public Aisle(int number, int numberOfLevels, int slotsPerLevel) {
        this.number = number;
        this.inboundLift = new Lift(LiftType.INBOUND);
        this.outboundLift = new Lift(LiftType.OUTBOUND);
        this.levels = new ArrayList<>();

        for (int i = 1; i <= numberOfLevels; i++) {
            levels.add(new Level(i, slotsPerLevel));
        }
    }

    public int getNumber() {
        return number;
    }

    public Lift getInboundLift() {
        return inboundLift;
    }

    public Lift getOutboundLift() {
        return outboundLift;
    }

    public List<Level> getLevels() {
        return levels;
    }
}