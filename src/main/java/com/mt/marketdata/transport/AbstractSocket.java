package com.mt.marketdata.transport;

public interface AbstractSocket {
    /**
     * connect to socket
     * @return true/false if operation success/fail
     */
    public boolean connect();

    /**
     * disconnect socket
     */
    public void disconnect();
}
