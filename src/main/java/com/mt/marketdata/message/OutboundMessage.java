package com.mt.marketdata.message;

public interface OutboundMessage {

    /**
     * generate byte array from message poyload
     * @return byte array
     */
    byte [] toByteArray();

}
