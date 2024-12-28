package com.mt.marketdata.message;

import com.mt.marketdata.engine.MarketDepth;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import static com.mt.marketdata.engine.MarketDepth.Side;
import static com.mt.marketdata.engine.MarketDepth.Side.Ask;
import static com.mt.marketdata.engine.MarketDepth.Side.Bid;

public class DataFeedMessage implements InboundMessage {

    protected static final Logger logger = Logger.getLogger(DataFeedMessage.class.getName());
    protected static final int MSG_LENGTH_LEN = 4;
    protected static final int SECURITY_ID_LEN = 4;
    protected static final int NUMBER_OF_UPDATES_LEN = 2;

    protected static final int LEVEL_LEN = 1;
    protected static final int SIDE_LEN = 1;
    protected static final int SCALED_PRICE_LEN = 8;
    protected static final int QTY_LEN = 4;

    private int msgLength;
    private int securityId;
    private int numberOfUpdates;
    private ByteBuffer buffer;
    private int pos = 0;


    public int getMsgLength() {
        return msgLength;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void convert(MarketDepth depth) {
        if (buffer == null) {
            return;
        }
        for (int i = 0; i < numberOfUpdates; i++) {
            int level = buffer.get(pos);
            pos += LEVEL_LEN;
            Side side = buffer.get(pos) == 0 ? Bid : Ask;
            pos += SIDE_LEN;
            double price = 1.0*buffer.getLong(pos)/MarketDepth.SCALED_FACTOR;
            pos += SCALED_PRICE_LEN;
            int size = buffer.getInt(pos);
            pos += QTY_LEN;
            depth.setLevel(level, price, size, side);
        }
    }

    @Override
    public boolean fromSrc(DatagramPacket packet) {
        buffer = ByteBuffer.wrap(packet.getData());

        msgLength = buffer.getInt(pos);
        if (msgLength != packet.getLength()) {
            logger.severe("malformed message received. Expected:" + msgLength + " Received:" + packet.getLength());
            return false;
        }
        pos += MSG_LENGTH_LEN;
        securityId = buffer.getInt(pos);
        pos += SECURITY_ID_LEN;
        numberOfUpdates = buffer.getShort(pos);
        pos += NUMBER_OF_UPDATES_LEN;

        return true;
    }
}
