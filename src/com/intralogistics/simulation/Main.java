package com.intralogistics.simulation;

import com.intralogistics.simulation.model.*;

public class Main {
    public static void main(String[] args) {

        int numberOfAisles = 4;
        int numberOfLevels = 10;
        int slotsPerLevel = 5;

        Warehouse warehouse = new Warehouse(
                numberOfAisles,
                numberOfLevels,
                slotsPerLevel
        );

        Container c1 = new Container(1, ContainerType.SOURCE, Priority.HIGH);
        Container c2 = new Container(2, ContainerType.SOURCE, Priority.MEDIUM);
        Container c3 = new Container(3, ContainerType.SOURCE, Priority.LOW);

        StorageSlot firstSlot = warehouse.getAisles().get(0)
                .getLevels().get(0)
                .getSlots().get(0);

        System.out.println(firstSlot.addContainer(c1));
        System.out.println(firstSlot.addContainer(c2));
        System.out.println(firstSlot.addContainer(c3));

        System.out.println(firstSlot);


        for (Aisle aisle : warehouse.getAisles()) {
            System.out.println("Aisle " + aisle.getNumber());

            System.out.println("  Inbound Lift: " + aisle.getInboundLift().getType());
            System.out.println("  Outbound Lift: " + aisle.getOutboundLift().getType());

            for (Level level : aisle.getLevels()) {
                System.out.println("    Level " + level.getNumber());

                System.out.println("      Shuttle on Level: " + level.getShuttle().getLevelNumber());

                System.out.println("      Slots: " + level.getSlots().size());
            }

            System.out.println();
        }
    }
}
