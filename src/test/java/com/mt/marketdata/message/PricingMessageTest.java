package com.mt.marketdata.message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;


class PricingMessageTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testPricingMessageDecode() {
        OutboundMessage msg = createTestMessage();
        byte [] data = msg.toByteArray();
        byte [] secId = new byte[4];
        byte [] price = new byte[8];

        System.arraycopy(data, 0,secId, 0, 4);
        System.arraycopy(data, 4,price, 0, 8);

        assert(1234 == ByteBuffer.wrap(secId).getInt());
        assert(1000000 == ByteBuffer.wrap(price).getLong());
    }

    OutboundMessage createTestMessage() {
        PricingMessage msg = new PricingMessage();
        msg.setData(1234, 1000000);

        return msg;
    }
}