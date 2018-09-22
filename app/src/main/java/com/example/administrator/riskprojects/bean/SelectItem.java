package com.example.administrator.riskprojects.bean;

public class SelectItem {
    public String name;

    @Override
    public String toString() {
        return name;
    }
    public String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
