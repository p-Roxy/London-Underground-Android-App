package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrivalTest {
    private Arrival arrivalwithDirection;
    private Arrival arrivalwithoutDirection;
    private Arrival arrivalwithDirectionTimeWeird;
    private Arrival arrivalwithDirectionTimeWeird2;


    @Before
    public void setUp(){
        arrivalwithDirection = new Arrival(1800, "NY", "North - Nine and Three Quarters");
        arrivalwithDirectionTimeWeird = new Arrival(1877, "NY", "North - Nine and Three Quarters");
        arrivalwithDirectionTimeWeird2 = new Arrival(1879, "NY", "North - Nine and Three Quarters");
        arrivalwithoutDirection =  new Arrival(130, "NY", "   Nine and Three Quarters   ");
    }

    @Test
    public void testGetTravelDirn() {
        assertEquals("North", arrivalwithDirection.getTravelDirn());
    }

    @Test
    public void testGetTimeInMinsIrregularLow() {
        assertEquals(32, arrivalwithDirectionTimeWeird.getTimeToStationInMins());
    }

    @Test
    public void testGetTimeInMinsIrregularHigh() {
        assertEquals(32, arrivalwithDirectionTimeWeird.getTimeToStationInMins());
    }

    @Test
    public void testGetTimeInMins() {
        assertEquals(30, arrivalwithDirection.getTimeToStationInMins());
    }

    @Test
    public void testGetTravelWithoutDirn() {
        assertEquals("Unknown direction", arrivalwithoutDirection.getTravelDirn());
    }


    @Test
    public void testGetPlatformName() {
        assertEquals("Nine and Three Quarters", arrivalwithDirection.getPlatformName());
    }


    @Test
    public void testGetPlatformNameSpaces() {
        assertEquals("Nine and Three Quarters", arrivalwithoutDirection.getPlatformName());
    }

    @Test
    public void testCompareTo() {
        assertTrue(arrivalwithDirection.compareTo(arrivalwithoutDirection) > 0);
    }

    @Test
    public void testCompareToOtherWay() {
        assertTrue(arrivalwithoutDirection.compareTo(arrivalwithDirection) < 0);
    }
}
