package me.danbrown.railflow.consumer.model;

public enum MessageTypes {
    TRAIN_STATUS("TS");

    private final String name;

    MessageTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
