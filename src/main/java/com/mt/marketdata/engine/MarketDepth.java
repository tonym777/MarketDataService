package com.mt.marketdata.engine;


import java.util.Arrays;
import java.util.Objects;

public class MarketDepth implements OrderBook{

    public static final long SCALED_FACTOR = 1_000_000_000L;

    public static double DELTA = 1.0 / SCALED_FACTOR;

    public static class DepthLevel {
        private double price;
        private int size;
    }

    public enum Side {
        Bid,
        Ask
    }
    private static final int OB_LEVEL = 10;
    private final DepthLevel [] bids = new DepthLevel[OB_LEVEL];
    private final DepthLevel [] asks = new DepthLevel[OB_LEVEL];
    private int bidsLevel = 0;
    private int asksLevel = 0;

    public MarketDepth() {
        for (int i = 0; i < OB_LEVEL; i++) {
            bids[i] = new DepthLevel();
            asks[i] = new DepthLevel();
        }
    }

    public void setLevel(int level, double price, int size, Side side) {
        if (level >= OB_LEVEL) {
            return;
        }
        if (side == Side.Bid) {
            bids[level].price = price;
            bids[level].size = size;
            // assumption : if level size become zero, this side's level can be removed and move one level up;
            bidsLevel = size > 0 ? Math.max(bidsLevel, level+1) : level;
        }
        else {
            asks[level].price = price;
            asks[level].size = size;
            asksLevel = size > 0 ? Math.max(asksLevel, level+1) : level;
        }
    }

    public int numBidsLevel() {
        return this.bidsLevel;
    }

    public int numAsksLevel() {
        return this.asksLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.bids), Arrays.hashCode(this.asks));
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (another == null || getClass() != another.getClass()) {
            return false;
        }

        MarketDepth anotherDepth = (MarketDepth)another;

        if (bidsLevel != anotherDepth.bidsLevel || asksLevel != anotherDepth.asksLevel) {
            return false;
        }

        for (int i = 0; i < OB_LEVEL; i++) {
            //if (Double.compare(bids[i].price, anotherDepth.bids[i].price) != 0
            //    || Double.compare(asks[i].price, anotherDepth.asks[i].price) != 0
            if (Math.abs(bids[i].price-anotherDepth.bids[i].price) < DELTA
                    || Math.abs(asks[i].price-anotherDepth.asks[i].price) < DELTA
                    || bids[i].size != anotherDepth.bids[i].size
                    || asks[i].size != anotherDepth.asks[i].size ) {
                return false;
            }
        }

        return true;
    }


    @Override
    public int numLevels() {
        return Math.max(this.bidsLevel, this.asksLevel);
    }

    @Override
    public double bidPrice(int level) {
        if (level > this.bidsLevel) {
            return 0;
        }
        return bids[level].price;
    }

    @Override
    public int bidSize(int level) {
        if (level > this.bidsLevel) {
            return 0;
        }
        return bids[level].size;
    }

    @Override
    public double askPrice(int level) {
        if (level > this.asksLevel) {
            return 0;
        }
        return asks[level].price;
    }

    @Override
    public int askSize(int level) {
        if (level > this.asksLevel) {
            return 0;
        }
        return asks[level].size;
    }
}
