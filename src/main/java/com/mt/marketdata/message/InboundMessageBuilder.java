package com.mt.marketdata.message;

import java.net.DatagramPacket;

public final class InboundMessageBuilder {

    private DatagramPacket packet;


    public InboundMessageBuilder() {
    }

    public InboundMessageBuilder packet(DatagramPacket data) {
        packet = data;
        return this;
    }

    public InboundMessage build() {
        InboundMessage msg = new DataFeedMessage();
        int length = packet.getLength();
        byte [] data = new byte[length];
        // make a copy of raw socket packet so can release socket buffer asap.
        System.arraycopy(packet.getData(), 0, data, 0, length);
        boolean convertible = msg.fromSrc(data);
        return convertible ? msg : null;
    }

}
