package com.intralogistics.simulation.model;

public class Container {
    private final int id;
    private final ContainerType type;
    private final Priority priority;

    public Container(int id, ContainerType type, Priority priority) {
        this.id = id;
        this.type = type;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public ContainerType getType() {
        return type;
    }

    public Priority getPriority(){
        return priority;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", type=" + type +
                ", priority=" + priority +
                '}';
    }
}