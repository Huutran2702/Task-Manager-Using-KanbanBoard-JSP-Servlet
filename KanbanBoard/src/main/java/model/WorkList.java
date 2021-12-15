package model;

public class WorkList {
    private int id;
    private String name;
    private int wspID;


    public WorkList() {
    }

    public WorkList(String name) {
        this.name = name;
    }

    public WorkList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public WorkList(int id, String name, int wspID) {
        this.id = id;
        this.name = name;
        this.wspID = wspID;
    }

    public WorkList(String name, int wspID) {
        this.name = name;
        this.wspID = wspID;
    }

    public int getWspID() {
        return wspID;
    }

    public void setWspID(int wspID) {
        this.wspID = wspID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
