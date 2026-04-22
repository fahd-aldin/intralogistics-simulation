package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Aisle> aisles;
    private int nextAisleIndex = 0;

    public Warehouse(int numberOfAisles, int numberOfLevels, int slotsPerLevel) {
        this.aisles = new ArrayList<>();
        for (int i = 1; i <= numberOfAisles; i++) {
            aisles.add(new Aisle(i, numberOfLevels, slotsPerLevel));
        }
    }
    public boolean storeContainer(Container container) {
        int numberOfAisles = aisles.size();

        for (int i = 0; i < numberOfAisles; i++) {
            int aisleIndex = (nextAisleIndex + i) % numberOfAisles; // ??
            Aisle aisle = aisles.get(aisleIndex);

            for (Level level : aisle.getLevels()) {
                for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
                    StorageSlot slot = level.getSlots().get(slotIndex);

                    String position = slot.addContainer(container);
                    if (position != null) {
                        System.out.println("Container: " + container.getId() + " wurde eingelagert in Gasse "
                                + aisle.getNumber()
                                + ", Ebene " + level.getNumber()
                                + ", Fach " + (slotIndex + 1)
                                + ", Position " + position);

                        nextAisleIndex = (aisleIndex + 1) % numberOfAisles;
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