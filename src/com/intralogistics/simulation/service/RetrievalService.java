package com.intralogistics.simulation.service;

import com.intralogistics.simulation.model.*;

import java.util.List;

public class RetrievalService {
    private final Warehouse warehouse;
    private final StorageService storageService;
    public RetrievalService(Warehouse warehouse, StorageService storageService){
        this.warehouse = warehouse;
        this.storageService = storageService;

    }
    public boolean retrieveContainerById(int containerId) {
        List<Aisle> aisles = warehouse.getAisles();
        for (int aisleIndex = 0; aisleIndex < aisles.size(); aisleIndex++) {
            Aisle aisle = aisles.get(aisleIndex);

            for (int levelIndex = 0; levelIndex < aisle.getLevels().size(); levelIndex++) {
                Level level = aisle.getLevels().get(levelIndex);

                for (int slotIndex = 0; slotIndex < level.getSlots().size(); slotIndex++) {
                    StorageSlot slot = level.getSlots().get(slotIndex);

                    Container front = slot.getFront();
                    Container back = slot.getBack();

                    if (front != null && front.getId() == containerId) {
                        slot.removeFront();

                        System.out.println("Container " + containerId +
                                " wurde ausgelagert aus Gasse " + aisle.getNumber() +
                                ", Ebene " + level.getNumber() +
                                ", Fach " + (slotIndex + 1) +
                                ", Position FRONT");

                        return true;
                    }

                    if (back != null && back.getId() == containerId) {
                        Container temp = null;

                        if (front != null) {
                            temp = slot.removeFront();

                            System.out.println("Front container " + temp.getId()
                                    + " wurde temporär entfernt ...");
                        }

                        slot.removeBack();

                        System.out.println("Container " + containerId +
                                " wurde ausgelagert aus Gasse " + aisle.getNumber() +
                                ", Ebene " + level.getNumber() +
                                ", Fach " + (slotIndex + 1) +
                                ", Position BACK");

                        if (temp != null) {
                            System.out.println("Reinlagern von temporärem Container " + temp.getId());

                            boolean reinsertedSameLocation =
                                   storageService.storeAtLocation(temp, aisleIndex, levelIndex, " (wieder am ursprünglichen Ort)");


                            if (!reinsertedSameLocation) {
                                System.out.println("Kein Platz am ursprünglichen Ort. Suche neuen Lagerplatz...");
                                storageService.storeContainer(temp);
                            }
                        }

                        return true;
                    }
                }
            }
        }

        System.out.println("Container " + containerId + " nicht gefunden!");
        return false;
    }




}
