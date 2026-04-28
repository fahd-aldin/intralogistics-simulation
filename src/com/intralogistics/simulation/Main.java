package com.intralogistics.simulation;

import com.intralogistics.simulation.model.*;
import com.intralogistics.simulation.service.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Intralogistics Warehouse Simulation ===");

        // Create warehouse and services
        Warehouse warehouse = new Warehouse(4, 10, 5);
        StorageService storageService = new StorageService(warehouse);
        RetrievalService retrievalService = new RetrievalService(warehouse, storageService);



        // Create realistic container data
        Container[] containers = {
                new Container(1, ContainerType.SOURCE, Priority.HIGH, 1001),
                new Container(2, ContainerType.SOURCE, Priority.MEDIUM, 1002),
                new Container(3, ContainerType.SOURCE, Priority.LOW, 1001),
                new Container(4, ContainerType.SOURCE, Priority.LOW),
                new Container(5, ContainerType.SOURCE, Priority.HIGH, 1003),
                new Container(6, ContainerType.SOURCE, Priority.MEDIUM, 1002),
                new Container(7, ContainerType.SOURCE, Priority.LOW),
                new Container(8, ContainerType.SOURCE, Priority.HIGH, 1001)
        };

        // Storage phase
        System.out.println("--- Storage Phase ---");
        for (Container c : containers) {
            storageService.storeContainer(c);
        }

        // Order lookup
        System.out.println("--- Order Lookup ---");
        int[] location = storageService.findLocationByOrderId(1001);
        if (location != null) {
            System.out.println("Order 1001 found in Gasse "
                    + (location[0] + 1) + ", Ebene "
                    + (location[1] + 1));
        } else {
            System.out.println("Order 1001 not found.");
        }

        // Retrieval tests
        // FRONT retrieval
        System.out.println("--- Retrieval FRONT ---");
        retrievalService.retrieveContainerById(1);

        // BACK retrieval (with relocation)
        System.out.println("--- Retrieval BACK (with relocation) ---");
        retrievalService.retrieveContainerById(3);

        // Non-existing container
        System.out.println("--- Retrieval NOT FOUND ---");
        retrievalService.retrieveContainerById(999);

        PickingArea pickingArea = new PickingArea();
        PickingWorkstation pickingWorkstation = pickingArea.getWorkstationById(1);

        pickingWorkstation.setTargetContainer(new Container(100, ContainerType.EMPTY, Priority.LOW));

        pickingWorkstation.addSourceContainer(new Container(200, ContainerType.SOURCE, Priority.LOW));
        pickingWorkstation.addSourceContainer(new Container(201, ContainerType.SOURCE, Priority.LOW));
        pickingWorkstation.addSourceContainer(new Container(202, ContainerType.SOURCE, Priority.LOW));
        pickingArea.printStatus();

        System.out.println("=== Simulation Finished ===");
    }

}

