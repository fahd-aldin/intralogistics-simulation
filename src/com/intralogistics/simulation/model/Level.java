package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int number;
    private final List<StorageSlot> slots;
    private final Shuttle shuttle;

    public Level(int number, int numberOfSlots) {
        this.number = number;
        this.slots = new ArrayList<>();
        for (int i = 0; i < numberOfSlots; i++) {
            slots.add(new StorageSlot());
        }
        this.shuttle = new Shuttle(number);
    }

    public int getNumber() {
        return number;
    }

    public List<StorageSlot> getSlots() {
        return slots;
    }

    public Shuttle getShuttle() {
        return shuttle;
    }
}