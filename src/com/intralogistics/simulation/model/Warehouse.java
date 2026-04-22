package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Aisle> aisles;

    public Warehouse(int numberOfAisles, int numberOfLevels, int slotsPerLevel) {
        this.aisles = new ArrayList<>();
        for (int i = 1; i <= numberOfAisles; i++) {
            aisles.add(new Aisle(i, numberOfLevels, slotsPerLevel));
        }
    }
    public boolean storeContainer(Container container) {
        for (Aisle aisle : aisles) {
            for (Level level : aisle.getLevels()) {
                for (StorageSlot slot : level.getSlots()) {
                    if (slot.addContainer(container)) {
                        System.out.println("Stored container " + container.getId()
                                + " in Aisle " + aisle.getNumber()
                                + ", Level " + level.getNumber());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Aisle> getAisles() {
        return aisles;
    }
}