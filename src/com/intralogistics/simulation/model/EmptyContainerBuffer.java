package com.intralogistics.simulation.model;

import java.util.LinkedList;
import java.util.Queue;

public class EmptyContainerBuffer {

    // FIFO Queue: (First In, First Out) erster Behälter rein → erster raus
    private final Queue<Container> emptyContainers;

    public EmptyContainerBuffer() {
        this.emptyContainers = new LinkedList<>();
    }


    public void addEmptyContainer(Container container) {
        if (container.getType() == ContainerType.EMPTY) {
            emptyContainers.add(container);
        } else {
            System.out.println("Nur leere Behälter dürfen in den Puffer!");
        }
    }

    // poll() statt remove(): um Exception  beim leer zu vermeiden (gibt null zurück)
    public Container getEmptyContainer() {
        if (emptyContainers.isEmpty()) {
            System.out.println("Keine leeren Behälter verfügbar!");
            return null;
        }
        return emptyContainers.poll();
    }

    public int getSize() {
        return emptyContainers.size();
    }
}