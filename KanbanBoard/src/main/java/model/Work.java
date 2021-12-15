package model;

import java.util.Date;

public class Work {
    private int id;
    private String name;
    private String description;
    private Date start;
    private Date finish;
    private int wlID;
    public Work() {
    }

    public Work(String name) {
        this.name = name;
    }

    public Work(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Work(int id, String name, int wlID) {
        this.id = id;
        this.name = name;
        this.wlID = wlID;
    }

    public Work(String name, String description, Date start, Date finish, int wlID) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.finish = finish;
        this.wlID = wlID;
    }

    public Work(int id, String name, String description, Date start, Date finish, int wlID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.finish = finish;
        this.wlID = wlID;
    }

    public int getWlID() {
        return wlID;
    }

    public void setWlID(int wlID) {
        this.wlID = wlID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getStart() {
        return (java.sql.Date) start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public java.sql.Date getFinish() {
        return (java.sql.Date) finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }
}
