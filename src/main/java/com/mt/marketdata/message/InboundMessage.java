package com.mt.marketdata.message;


public interface InboundMessage {

    /**
     * fill in inbound message payload base on received UDP packet
     * @param data
     * @return true - if decode ok or false if not successful
     */
    boolean fromSrc(byte [] data);
}
