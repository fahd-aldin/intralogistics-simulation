package com.intralogistics.simulation;

import com.intralogistics.simulation.model.*;


    public class Main {
        public static void main(String[] args) {
            Warehouse warehouse = new Warehouse(2, 2, 2);
            /*
            Container c1 = new Container(1, ContainerType.SOURCE, Priority.HIGH);
            Container c2 = new Container(2, ContainerType.SOURCE, Priority.MEDIUM);
            Container c3 = new Container(3, ContainerType.SOURCE, Priority.LOW);


             */

            for( int i  = 1; i <= 20; i++){
                Container c = new Container(i, ContainerType.SOURCE, Priority.LOW);
                boolean stored = warehouse.storeContainer(c);

                if (!stored) {
                    System.out.println("Container " + c.getId() + " konnte nicht eingelagert werden: Lager voll.");
                }
            }




        }
    }
