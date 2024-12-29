package com.mt.marketdata.restapi.model;

public class Instrument {

    private long instrId;

    public Instrument() {
    }

    public void setId(long id) {
        this.instrId = id;
    }

    public long getId() {
        return instrId;
    }

    @Override
    public String toString() {
        return "Instrument [Id =" + instrId;
    }
}
