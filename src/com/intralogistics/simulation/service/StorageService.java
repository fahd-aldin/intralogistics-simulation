package com.intralogistics.simulation.service;

import com.intralogistics.simulation.model.*;

import java.util.List;

public class StorageService {
    private final Warehouse warehouse;
    private int nextAisleIndex = 0;


    public StorageService(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public boolean storeContainer(Container container) {
        if (storeNearSameOrder(container)) {
            return true;
        }

        return storeByDistributionAndPriority(container);
    }

    private boolean storeNearSameOrder(Container container) {
        if (container.getOrderId() == null) {
            return false;
        }

        int[] location = findLocationByOrderId(container.getOrderId());
        if (location == null) {
            return false;
        }

        return storeAtLocation(container, location[0], location[1], " (nahe am gleichen Auftrag)");
    }

    private boolean storeByDistributionAndPriority(Container container) {
        List<Aisle> aisles = warehouse.getAisles();

        int numberOfAisles = aisles.size();
        int numberOfLevels = aisles.get(0).getLevels().size();

        int[] levelOrder = getLevelSearchOrder(container.getPriority(), numberOfLevels);

        for (int levelOffset = 0; levelOffset < numberOfLevels; levelOffset++) {
            int levelIndex = levelOrder[levelOffset];

            for (int aisleOffset = 0; aisleOffset < numberOfAisles; aisleOffset++) {
                int aisleIndex = (nextAisleIndex + aisleOffset) % numberOfAisles;
                Aisle aisle = aisles.get(aisleIndex);
                Level level = aisle.getLevels().get(levelIndex);

                for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
                    StorageSlot slot = level.getSlots().get(slotIndex);

                    String position = slot.addContainer(container);
                    if (position != null) {
                        printStorageInfo(container, aisle, level, slotIndex, position, "");

                        nextAisleIndex = (aisleIndex + 1) % numberOfAisles;

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean storeAtLocation(Container container, int aisleIndex, int levelIndex, String note) {
        List<Aisle> aisles = warehouse.getAisles();

        Aisle aisle = aisles.get(aisleIndex);
        Level level = aisle.getLevels().get(levelIndex);

        for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
            StorageSlot slot = level.getSlots().get(slotIndex);

            String position = slot.addContainer(container);
            if (position != null) {
                printStorageInfo(container, aisle, level, slotIndex, position, note);
                return true;
            }
        }
        return false;
    }
    private int[] getLevelSearchOrder(Priority priority, int numberOfLevels) {
        int[] order = new int[numberOfLevels];

        if (priority == Priority.HIGH) {
            for (int i = 0; i < numberOfLevels; i++) {
                order[i] = i;
            }
        } else if (priority == Priority.LOW) {
            for (int i = 0; i < numberOfLevels; i++) {
                order[i] = numberOfLevels - 1 - i;
            }
        } else {
            int index = 0;
            int middle = numberOfLevels / 2;

            for (int i = middle; i < numberOfLevels; i++) {
                order[index++] = i;
            }
            for (int i = 0; i < middle; i++) {
                order[index++] = i;
            }
        }

        return order;
    }
    public int[] findLocationByOrderId(Integer orderId) {
        if (orderId == null) {
            return null;
        }

        List<Aisle> aisles = warehouse.getAisles();

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
    private void printStorageInfo(Container container,
                                  Aisle aisle,
                                  Level level,
                                  int slotIndex,
                                  String position,
                                  String note) {
        System.out.println("Container: " + container.getId()
                + " wurde eingelagert in Gasse " + aisle.getNumber()
                + ", Ebene " + level.getNumber()
                + ", Fach " + (slotIndex + 1)
                + ", Position " + position
                + ", orderId " + container.getOrderId()
                + ", Priority " + container.getPriority()
                + note);
    }

}
