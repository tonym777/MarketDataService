package com.mt.marketdata.restapi.model;

public class Instrument {

    private long instrId;

    private String name;

    public Instrument() {
    }

    public void setId(long id) {
        this.instrId = id;
    }

    public long getId() {
        return instrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Instrument [Id =" + instrId + " Name=" + name + "]";
    }
}
