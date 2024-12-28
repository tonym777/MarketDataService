package com.mt.marketdata.message;


public final class OutboundMessageBuilder {

    private int securityId;
    private long price;

    public OutboundMessageBuilder() {
    }

    public OutboundMessageBuilder securityId(int data) {
        securityId = data;
        return this;
    }

    public OutboundMessageBuilder price(long data) {
        price = data;
        return this;
    }

    public OutboundMessage build() {
        PricingMessage msg =  new PricingMessage();
        msg.setData(securityId, price);
        return msg;
    }

}
