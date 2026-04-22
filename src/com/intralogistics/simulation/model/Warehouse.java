package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Aisle> aisles;
    private int nextAisleIndex = 0;
    private int nextLevelIndex = 0;

    public Warehouse(int numberOfAisles, int numberOfLevels, int slotsPerLevel) {
        this.aisles = new ArrayList<>();
        for (int i = 1; i <= numberOfAisles; i++) {
            aisles.add(new Aisle(i, numberOfLevels, slotsPerLevel));
        }
    }
    public boolean storeContainer(Container container) {
        int numberOfAisles = aisles.size();
        int numberOfLevels = aisles.get(0).getLevels().size();

        for (int levelOffset = 0; levelOffset < numberOfLevels; levelOffset++) {
            int levelIndex = (nextLevelIndex + levelOffset) % numberOfLevels;

            for (int aisleOffset = 0; aisleOffset < numberOfAisles; aisleOffset++) {
                int aisleIndex = (nextAisleIndex + aisleOffset) % numberOfAisles;
                Aisle aisle = aisles.get(aisleIndex);
                Level level = aisle.getLevels().get(levelIndex);

                for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
                    StorageSlot slot = level.getSlots().get(slotIndex);

                    String position = slot.addContainer(container);
                    if (position != null) {
                        System.out.println("Container: " + container.getId()
                                + " wurde eingelagert in Gasse " + aisle.getNumber()
                                + ", Ebene " + level.getNumber()
                                + ", Fach " + (slotIndex + 1)
                                + ", Position " + position
                                + ", orderId " + container.getOrderId()

                        );


                        nextAisleIndex = (aisleIndex + 1) % numberOfAisles;

                        if (nextAisleIndex == 0) {
                            nextLevelIndex = (levelIndex + 1) % numberOfLevels;
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int[] findLocationByOrderId(Integer orderId) {
        if (orderId == null) {
            return null;
        }

        for (Aisle aisle : aisles) {
            for (Level level : aisle.getLevels()) {
                for (StorageSlot slot : level.getSlots()) {
                    Container front = slot.getFront();
                    Container back = slot.getBack();

                    if ((front != null && orderId.equals(front.getOrderId())) ||
                            (back != null && orderId.equals(back.getOrderId()))) {
                        return new int[]{aisle.getNumber() - 1, level.getNumber() - 1};
                    }
                }
            }
        }
        return null;
    }

    public List<Aisle> getAisles() {
        return aisles;
    }
}