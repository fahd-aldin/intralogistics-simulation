package com.intralogistics.simulation;

import com.intralogistics.simulation.model.*;


    public class Main {
        public static void main(String[] args) {
            Warehouse warehouse = new Warehouse(2, 4, 2);


            Container c1 = new Container(1, ContainerType.SOURCE, Priority.HIGH, 1001);
            Container c2 = new Container(2, ContainerType.SOURCE, Priority.MEDIUM, 1002);
            Container c3 = new Container(3, ContainerType.SOURCE, Priority.LOW, 1001);
            Container c4 = new Container(4, ContainerType.SOURCE, Priority.LOW);

            warehouse.storeContainer(c1);
            warehouse.storeContainer(c2);
            warehouse.storeContainer(c3);
            warehouse.storeContainer(c4);

            int[] location = warehouse.findLocationByOrderId(1001);
            if (location != null) {
                System.out.println("Auftrag 1001 gefunden in Gasse "
                        + (location[0] + 1) + ", Ebene " + (location[1] + 1));
            }


            warehouse.retrieveContainerById(2);
            warehouse.retrieveContainerById(3);


            /*for( int i  = 1; i <= 20; i++){
                Container c = new Container(i, ContainerType.SOURCE, Priority.LOW, i + 100);
                boolean stored = warehouse.storeContainer(c);

                if (!stored) {
                    System.out.println("Container " + c.getId() + " konnte nicht eingelagert werden: Lager voll.");
                }
            }

             */

        }
    }
