package model;

public enum Favorite {
    FAVORITE("FAVORITE"), NON("NON");
    private String value;

    Favorite(String value) {
        this.value = value;
    }
}
