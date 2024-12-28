package com.mt.marketdata.transport;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public final class Publisher implements AbstractSocket {

    private static final Logger logger = Logger.getLogger(Publisher.class.getName());
    private final String address;
    private final int port;
    private Socket socket;
    private DataOutputStream outputStream;


    public Publisher(final String address, final int port) {
        this.address = address;
        this.port = port;
        connect();
    }


    @Override
    public boolean connect() {
        try {
            socket = new Socket(address, port);
            socket.setTcpNoDelay(true);
            socket.setKeepAlive(true);
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            logger.severe("publisher connection error: " + e.getMessage() + " with address = " + address + " port = " + port);
            return false;
        }
        return true;
    }

    @Override
    public void disconnect() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.severe("close socket error: " + e.getMessage());
            }
            socket = null;
        }
    }

    public void send(byte[] buffer) {
        try {
            if (!socket.isConnected()) {
                connect();
            }
            outputStream.write(buffer);
        } catch (IOException e) {
            logger.severe("publish packet error: " + e.getMessage());
            disconnect();
        }
    }
}
