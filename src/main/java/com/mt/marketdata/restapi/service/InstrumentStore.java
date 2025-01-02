package com.mt.marketdata.restapi.service;

import com.mt.marketdata.annotation.LatencyMeasurement;
import com.mt.marketdata.restapi.model.Instrument;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class InstrumentStore {

    private final static List<Instrument> instrList = new LinkedList<>();

    @LatencyMeasurement
    public List<Instrument> getAll() {
        return instrList;
    }
}
