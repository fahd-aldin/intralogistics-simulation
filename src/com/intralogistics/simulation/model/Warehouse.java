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

        return storeInSpecificLocation(container, location[0], location[1]);
    }

    private boolean storeByDistributionAndPriority(Container container) {
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

    private boolean storeInSpecificLocation(Container container, int aisleIndex, int levelIndex) {
        Aisle aisle = aisles.get(aisleIndex);
        Level level = aisle.getLevels().get(levelIndex);

        for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
            StorageSlot slot = level.getSlots().get(slotIndex);

            String position = slot.addContainer(container);
            if (position != null) {
                printStorageInfo(container, aisle, level, slotIndex, position, " (nahe am gleichen Auftrag)");
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

    public boolean retrieveContainerById(int containerId) {
        for (Aisle aisle : aisles) {
            for (Level level : aisle.getLevels()) {
                for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
                    StorageSlot slot = level.getSlots().get(slotIndex);

                    Container front = slot.getFront();
                    Container back = slot.getBack();

                    if (front != null && front.getId() == containerId) {
                        slot.setFront(null);

                        System.out.println("Container " + containerId +
                                " wurde ausgelagert aus Gasse " + aisle.getNumber() +
                                ", Ebene " + level.getNumber() +
                                ", Fach " + (slotIndex + 1) +
                                ", Position FRONT");

                        return true;
                    }

                    if (back != null && back.getId() == containerId) {

                        if (front != null) {
                            System.out.println("Container " + containerId +
                                    " ist im BACK, FRONT muss zuerst entfernt werden!");
                            return false;
                        }

                        slot.setBack(null);

                        System.out.println("Container " + containerId +
                                " wurde ausgelagert aus Gasse " + aisle.getNumber() +
                                ", Ebene " + level.getNumber() +
                                ", Fach " + (slotIndex + 1) +
                                ", Position BACK");

                        return true;
                    }
                }
            }
        }

        System.out.println("Container " + containerId + " nicht gefunden!");
        return false;
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

    public List<Aisle> getAisles() {
        return aisles;
    }
}