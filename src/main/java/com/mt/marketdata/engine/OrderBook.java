package com.mt.marketdata.engine;

public interface OrderBook {
    /**
     * get num of total levels on both bids and asks
     * @return number of total levels
     */
    public int numLevels();

    /**
     * get bid price at level
     * @param level
     * @return price or zero if no level found
     */
    public double bidPrice(int level);

    /**
     * get bid size at level
     * @param level
     * @return size or zero if no level found
     */
    public int bidSize(int level);

    /**
     * get ask price at level
     * @param level
     * @return price or zero if no level found
     */
    public double askPrice(int level);

    /**
     * get ask size at level
     * @param level
     * @return size or zero if no level found
     */
    public int askSize(int level);
}
