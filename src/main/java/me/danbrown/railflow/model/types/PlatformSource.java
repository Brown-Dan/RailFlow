package me.danbrown.railflow.model.types;

public enum PlatformSource {
    PLANNED,
    AUTOMATIC,
    MANUAL;

    public static PlatformSource fromInitial(String initial) {
        return switch (initial) {
            case "A" -> AUTOMATIC;
            case "M" -> MANUAL;
            default -> PLANNED;
        };
    }
}
