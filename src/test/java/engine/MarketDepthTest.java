package engine;

import com.mt.marketdata.engine.MarketDepth;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.mt.marketdata.engine.MarketDepth.Side.Ask;
import static com.mt.marketdata.engine.MarketDepth.Side.Bid;
import static org.junit.Assert.*;

public class MarketDepthTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDepthUpdate() {
        MarketDepth book = makeDepth1();
        assertEquals(10, book.numBidsLevel());
        assertEquals(10, book.numAsksLevel());
        book.setLevel(9, 0,0, Bid);
        book.setLevel(8, 0,0, Bid);
        book.setLevel(9, 0,0, Ask);
        assertEquals(8, book.numBidsLevel());
        assertEquals(9, book.numAsksLevel());
        book.setLevel(8, 0.22,10, Bid);
        assertEquals(9, book.numBidsLevel());
        assertEquals(9, book.numAsksLevel());
    }


    public MarketDepth makeDepth1() {
        MarketDepth depth = new MarketDepth();
        depth.setLevel(0, 5.0, 100, Bid);
        depth.setLevel(1, 4.0, 200, Bid);
        depth.setLevel(2, 3.0, 100, Bid);
        depth.setLevel(3, 2.0, 200, Bid);
        depth.setLevel(4, 1.0, 300, Bid);
        depth.setLevel(5, 0.9, 100, Bid);
        depth.setLevel(6, 0.8, 200, Bid);
        depth.setLevel(7, 0.7, 100, Bid);
        depth.setLevel(8, 0.5, 200, Bid);
        depth.setLevel(9, 0.3, 3000, Bid);
        depth.setLevel(0, 6.0, 100, Ask);
        depth.setLevel(1, 7.0, 200, Ask);
        depth.setLevel(2, 8.0, 100, Ask);
        depth.setLevel(3, 9.0, 200, Ask);
        depth.setLevel(4, 10.0, 300, Ask);
        depth.setLevel(5, 11.0, 100, Ask);
        depth.setLevel(6, 12.0, 200, Ask);
        depth.setLevel(7, 12.3, 100000, Ask);
        depth.setLevel(8, 12.8, 200, Ask);
        depth.setLevel(9, 12.9, 300, Ask);
        return depth;
    }


}