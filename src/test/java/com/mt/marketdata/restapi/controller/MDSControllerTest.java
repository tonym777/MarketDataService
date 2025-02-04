package com.mt.marketdata.restapi.controller;

import com.mt.marketdata.restapi.service.InstrumentStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MDSControllerTest {

    @Mock
    InstrumentStore instrumentStore;
    @InjectMocks
    MDSController mdsController = new MDSController();

    @Test
    public void testGetAllInstruments() {
        mdsController.getAllInstruments();
        verify(instrumentStore, times(1)).getAll();

    }

}