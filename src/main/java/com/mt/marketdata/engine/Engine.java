package com.mt.marketdata.engine;

import com.mt.marketdata.message.DataFeedMessage;
import com.mt.marketdata.message.InboundMessage;
import com.mt.marketdata.message.OutboundMessage;
import com.mt.marketdata.message.OutboundMessageBuilder;
import com.mt.marketdata.transport.Publisher;
import com.mt.marketdata.transport.Subscriber;

import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Engine implements EventProcessor, Runnable {

    private static final MicroPriceCalculator calculator = new MicroPriceCalculator();
    private final Subscriber subscriber;
    private final Publisher publisher;
    private final Map<Integer, MarketDepth> marketDepths = new ConcurrentHashMap<>();
    private final Queue<InboundMessage> inboundQueue = new ConcurrentLinkedQueue<>();


    public Engine(Properties props) {
        String mcastAddress = props.getProperty("DATA_FEED_MCAST_GROUP");
        int port = Integer.parseInt(props.getProperty("DATA_FEED_UDP_PORT"));
        String nic = props.getProperty("NIC");
        String tcpIp = props.getProperty("REMOTE_TCP_IP");
        int tcpPort = Integer.parseInt(props.getProperty("REMOTE_TCP_PORT"));

        publisher = new Publisher(tcpIp, tcpPort);
        subscriber = new Subscriber(mcastAddress, port,nic);
        subscriber.addListener(this);
    }

    @Override
    public void onEvent(InboundMessage msg) {
        inboundQueue.offer(msg);
    }

    @Override
    public void run() {
        subscriber.start();

        while (true)  {
            if (!inboundQueue.isEmpty()) {
                DataFeedMessage msg = (DataFeedMessage)inboundQueue.poll();
                int id = msg.getSecurityId();

                if (!marketDepths.containsKey(id)) {
                    marketDepths.put(id, new MarketDepth());
                }

                MarketDepth depth = marketDepths.get(id);
                msg.convert(depth);
                double price = calculator.calculateMicroPrice(depth);
                OutboundMessage outBoundMsg = new OutboundMessageBuilder().
                            price((long)(price*MarketDepth.SCALED_FACTOR)).
                            securityId(id).
                            build();
                publisher.send(outBoundMsg.toByteArray());
            }
        }

    }

}
