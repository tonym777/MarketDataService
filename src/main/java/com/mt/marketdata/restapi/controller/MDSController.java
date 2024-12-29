package com.mt.marketdata.restapi.controller;

import com.mt.marketdata.restapi.model.Instrument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MDSController {

    @GetMapping("/instrumentService")
    public ResponseEntity<List<Instrument>> getAllInstruments(@RequestParam(required = false) long id) {
        try {
            List<Instrument> instruments = new ArrayList<Instrument>();
            return new ResponseEntity<>(instruments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
