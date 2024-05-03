package com.example.th2.models;

import java.io.Serializable;

public class Work implements Serializable {
    private int id;
    private String name;
    private String description;
    private String dateDone;
    private String status;
    private boolean isCongTac;

    public Work() {
    }

    public Work(int id, String name, String description, String dateDone, String status, boolean isCongTac) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateDone = dateDone;
        this.status = status;
        this.isCongTac = isCongTac;
    }

    public Work(String name, String description, String dateDone, String status, boolean isCongTac) {
        this.name = name;
        this.description = description;
        this.dateDone = dateDone;
        this.status = status;
        this.isCongTac = isCongTac;
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

    public String getDateDone() {
        return dateDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCongTac() {
        return isCongTac;
    }

    public void setCongTac(boolean congTac) {
        isCongTac = congTac;
    }
}
