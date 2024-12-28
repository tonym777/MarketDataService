package com.mt.marketdata.engine;

import com.mt.marketdata.message.InboundMessage;

public interface EventProcessor {
    /**
     * event processor to handle InboundMessage type
     * @param msg
     */
    public void onEvent(InboundMessage msg);

}
