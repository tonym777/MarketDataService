package com.mt.marketdata.transport;

import com.mt.marketdata.engine.EventProcessor;
import com.mt.marketdata.message.InboundMessage;
import com.mt.marketdata.message.InboundMessageBuilder;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public final class Subscriber implements AbstractSocket, Runnable {

    private static final Logger logger = Logger.getLogger(Subscriber.class.getName());
    private static final int BUF_LEN = 1024;
    private final List<EventProcessor> processors = new LinkedList<>();
    private final String address;
    private final int port;
    private final String nic;
    private MulticastSocket multicastSocket;
    private SocketAddress multicastGroup;
    private NetworkInterface nif;

    public Subscriber(final String address, final int port, final String nic) {
        this.address = address;
        this.port = port;
        this.nic = nic;
    }

    public void addListener(EventProcessor processor) {
        processors.add(processor);
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public boolean connect() {
        if (multicastSocket == null) {
            try {
                multicastSocket = new MulticastSocket(port);
                multicastGroup = new InetSocketAddress(InetAddress.getByName(address), port);
                nif = NetworkInterface.getByName(nic);
                multicastSocket.joinGroup(multicastGroup, nif);
            } catch (IOException e) {
                logger.severe("create multicast socket error" + e.getMessage());
                if (multicastSocket != null) {
                    multicastSocket.close();
                    multicastSocket = null;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void disconnect() {
        if (multicastSocket != null) {
            try {
                if (multicastGroup != null && nif != null) {
                    multicastSocket.leaveGroup(multicastGroup, nif);
                }
            } catch (IOException e) {
                logger.severe("close multicast socket error: " + e.getMessage());
            }
            multicastSocket.close();
            multicastSocket = null;
        }
    }

    @Override
    public void run() {

        if (!connect()) {
            return;
        }

        while (true) {
            try {
                byte[] buf = new byte[BUF_LEN];
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                multicastSocket.receive(msgPacket);
                InboundMessage msg = new InboundMessageBuilder().packet(msgPacket).build();
                if (msg != null) {
                    processors.forEach(e -> e.onEvent(msg));
                }
            } catch (IOException e) {
                logger.severe("receive multicast packet error" + e.getMessage());
                disconnect();
                break;
            }
        }
    }
}
