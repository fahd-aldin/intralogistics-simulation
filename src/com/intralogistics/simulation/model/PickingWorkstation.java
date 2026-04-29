package com.intralogistics.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class PickingWorkstation {

    private final int id;

    private Container targetContainer; // Zielbehälter
    private final List<Container> sourceContainers; // sollen  3 Quellbehälter sein

    private static final int MAX_SOURCE_CONTAINERS = 3;

    public PickingWorkstation(int id) {
        this.id = id;
        this.sourceContainers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Container getTargetContainer() {
        return targetContainer;
    }

    public void setTargetContainer(Container targetContainer) {
        this.targetContainer = targetContainer;
    }

    public List<Container> getSourceContainers() {
        return sourceContainers;
    }

    public boolean addSourceContainer(Container container) {
        if (sourceContainers.size() < MAX_SOURCE_CONTAINERS) {
            sourceContainers.add(container);
            return true;
        }
        return false;
    }

    public void removeSourceContainer(Container container) {
        sourceContainers.remove(container);
    }

    public boolean isReadyForPicking() {
        return targetContainer != null && !sourceContainers.isEmpty();
    }
    public boolean assignTargetFromBuffer(EmptyContainerBuffer buffer) {
        if (targetContainer != null) {
            System.out.println("Arbeitsplatz " + id + " hat bereits einen Zielbehälter.");
            return false;
        }

        Container emptyContainer = buffer.getEmptyContainer();

        if (emptyContainer == null) {
            System.out.println("Kein leerer Behälter verfügbar für Arbeitsplatz " + id);
            return false;
        }

        this.targetContainer = emptyContainer;

        System.out.println("Zielbehälter " + emptyContainer.getId()
                + " wurde Arbeitsplatz " + id + " zugewiesen.");

        return true;
    }
}