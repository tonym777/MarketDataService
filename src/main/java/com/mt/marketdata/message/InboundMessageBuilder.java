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
        InboundMessage msg =  new DataFeedMessage();
        boolean convertible = msg.fromSrc(packet);
        return convertible ? msg : null;
    }

}
