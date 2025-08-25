package com.mt.marketdata.message;

import com.mt.pricing.MarketDepth;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.nio.ByteBuffer;

class DataFeedMessageTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testDeedFeedMessageDecode() {
        InboundMessage dataFeedMsg = createTestMessage();
        MarketDepth depth = new MarketDepth();
        ((DataFeedMessage)dataFeedMsg).convert(depth);

        assert(depth.numLevels() == 2);

        assert(Double.compare(0.000999999, depth.bidPrice(0)) == 0);
        assert(Double.compare(0.000999998, depth.bidPrice(1)) == 0);
        assert(Double.compare(0.001, depth.askPrice(0)) == 0);

        assert(depth.bidSize(0) == 1000);
        assert(depth.bidSize(1) == 3000);
        assert(depth.askSize(0) == 5000);
    }

    InboundMessage createTestMessage() {
        InboundMessage msg = new DataFeedMessage();
        int length = 52;
        int secId = 1234;
        short numUpdates = 3;
        ByteBuffer buffer = ByteBuffer.allocateDirect(length);
        buffer.putInt(length);
        buffer.putInt(secId);
        buffer.putShort(numUpdates);
        // fill the bid side (2 entries/levels)
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.putLong(999999);
        buffer.putInt(1000);
        buffer.put((byte)1);
        buffer.put((byte)0);
        buffer.putLong(999998);
        buffer.putInt(3000);
        // fill the ask size
        buffer.put((byte)0);
        buffer.put((byte)1);
        buffer.putLong(1000000);
        buffer.putInt(5000);

        buffer.rewind();

        byte[] data;
        if (buffer.hasArray()) {
            data = buffer.array();
        } else {
            data = new byte[buffer.remaining()];
            buffer.get(data);
        }

        msg.fromSrc(data);
        return msg;
    }
}