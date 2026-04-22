package com.intralogistics.simulation;

import com.intralogistics.simulation.model.*;


    public class Main {
        public static void main(String[] args) {
            Warehouse warehouse = new Warehouse(4, 10, 5);

            Container c1 = new Container(1, ContainerType.SOURCE, Priority.HIGH);
            Container c2 = new Container(2, ContainerType.SOURCE, Priority.MEDIUM);
            Container c3 = new Container(3, ContainerType.SOURCE, Priority.LOW);

            warehouse.storeContainer(c1);
            warehouse.storeContainer(c2);
            warehouse.storeContainer(c3);
        }
    }
