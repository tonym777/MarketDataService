package com.mt.marketdata.restapi.controller;

import com.mt.marketdata.restapi.model.Instrument;
import com.mt.marketdata.restapi.service.InstrumentStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MDSController {

    @Autowired
    InstrumentStore instrStore;

    @GetMapping("/instrumentService")
    public ResponseEntity<List<Instrument>> getAllInstruments() {
        try {
            List<Instrument> instruments = instrStore.getAll();
            return new ResponseEntity<>(instruments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
