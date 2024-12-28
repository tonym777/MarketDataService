package com.mt.marketdata.message;

import java.nio.ByteBuffer;

public class PricingMessage implements OutboundMessage{
    private static final int SECURITY_ID_LEN = 4;
    private static final int SCALED_PRICE_LEN = 8;
    private final ByteBuffer buffer = ByteBuffer.allocate(SECURITY_ID_LEN+SCALED_PRICE_LEN);


    public void setData(int secId, long price) {
        buffer.putInt(0, secId);
        buffer.putLong(SECURITY_ID_LEN, price);
    }

    @Override
    public byte[] toByteArray() {
        // or make a copy of buffer if sending part is expensive
        return buffer.array();
    }
}
