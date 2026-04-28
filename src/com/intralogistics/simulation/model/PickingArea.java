package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class PickingArea {

    private static final int NUMBER_OF_WORKSTATIONS = 4;

    private final List<PickingWorkstation> workstations;

    public PickingArea() {
        this.workstations = new ArrayList<>();

        for (int i = 1; i <= NUMBER_OF_WORKSTATIONS; i++) {
            workstations.add(new PickingWorkstation(i));
        }
    }

    public List<PickingWorkstation> getWorkstations() {
        return workstations;
    }

    public PickingWorkstation getWorkstationById(int id) {
        for (PickingWorkstation workstation : workstations) {
            if (workstation.getId() == id) {
                return workstation;
            }
        }
        return null;
    }

    public void printStatus() {
        System.out.println("=== Kommissionierbereich ===");

        for (PickingWorkstation workstation : workstations) {
            System.out.println("Arbeitsplatz " + workstation.getId()
                    + "  Zielbehälter: " + workstation.getTargetContainer()
                    + "  Quellbehälter: " + workstation.getSourceContainers().size()
                    );
        }
    }
}