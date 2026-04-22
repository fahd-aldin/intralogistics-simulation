package com.intralogistics.simulation.model;

public class Container {
    private final int id;
    private final ContainerType type;
    private final Priority priority;
    private final Integer orderId;
    public Container(int id, ContainerType type, Priority priority, Integer orderId) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.orderId = orderId;
    }
    public Container(int id, ContainerType type, Priority priority) {
        this(id, type, priority, null);
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
    public Integer getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Behälter{id=" + id +
                ", type=" + type +
                ", priority=" + priority +
                ", orderId=" + orderId +
                "}";
    }
}