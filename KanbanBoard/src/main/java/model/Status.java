package model;

public enum Status {
    LOCK("LOCK"), ACTIVE("ACTIVE");
    private String value;

    private Status(String value) {
        this.value = value;
    }
}
