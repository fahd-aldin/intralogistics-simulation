package com.intralogistics.simulation.model;
public class StorageSlot {
    private Container front;
    private Container back;

    public boolean isFull() {
        return front != null && back != null;
    }

    public boolean isEmpty() {
        return front == null && back == null;
    }

    public String  addContainer(Container container) {
        if (front == null) {
            front = container;
            return "FRONT";
        }
        if (back == null) {
            back = container;
            return "BACK";
        }
        return null;
    }

    public Container removeFront() {
        if (front == null) return null;
        Container temp = front;
        front = null;
        return temp;
    }

    public Container removeBack() {
        if (back == null) return null;
        Container temp = back;
        back = null;
        return temp;
    }

    public Container getFront() {
        return front;
    }

    public Container getBack() {
        return back;
    }

    @Override
    public String toString() {
        return "Front=" + front + ", Back=" + back;
    }
}