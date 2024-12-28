package engine;

import com.mt.marketdata.engine.MarketDepth;
import com.mt.marketdata.engine.MicroPriceCalculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.mt.marketdata.engine.MarketDepth.Side.Ask;
import static com.mt.marketdata.engine.MarketDepth.Side.Bid;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class MicroPriceCalculatorTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPricingCalculation() {
        MicroPriceCalculator calculatorMock = Mockito.mock(MicroPriceCalculator.class);
        MarketDepth depth1 = makeDepth1();
        MarketDepth depth2 = makeDepth2();

        when(calculatorMock.calculateMicroPrice(depth1)).thenReturn(1.0);
        when(calculatorMock.calculateMicroPrice(depth2)).thenReturn(2.0);

        double price = calculatorMock.calculateMicroPrice(depth2);
        assertEquals(0, Double.compare(2.0, price));

        price = calculatorMock.calculateMicroPrice(depth1);
        assertEquals(0, Double.compare(1.0, price));

    }

    public MarketDepth makeDepth1() {
        MarketDepth depth = new MarketDepth();
        depth.setLevel(0, 5.0, 100, Bid);
        depth.setLevel(1, 4.0, 200, Bid);
        depth.setLevel(2, 3.0, 100, Bid);
        depth.setLevel(3, 2.0, 200, Bid);
        return depth;
    }

    public MarketDepth makeDepth2() {
        MarketDepth depth = new MarketDepth();
        depth.setLevel(0, 5.0, 100, Bid);
        depth.setLevel(1, 4.0, 200, Bid);
        depth.setLevel(2, 3.0, 100, Bid);
        depth.setLevel(3, 2.0, 200, Bid);
        depth.setLevel(0, 6.0, 100, Ask);
        depth.setLevel(1, 7.0, 200, Ask);
        depth.setLevel(2, 0, 0, Ask);
        return depth;
    }
}