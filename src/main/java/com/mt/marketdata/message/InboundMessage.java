package com.mt.marketdata.message;

import java.net.DatagramPacket;

public interface InboundMessage {

    /**
     * fill in inbound message payload base on received UDP packet
     * @param data
     * @return true - if process ok or false if not successful
     */
    public boolean fromSrc(byte [] data);
}
