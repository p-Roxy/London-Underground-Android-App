package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.ArrivalBoard;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ArrivalBoardTest {
    private Arrival arrival1;
    private Arrival arrival2;
    private Arrival arrival3;
    private List<Arrival> arrivals;
    private Line line;
    private String travelDirection;
    private ArrivalBoard arrivalBoard;


    @Before
    public void setUp(){
        arrival1 = new Arrival(1800, "NY", "North - Nine and Three Quarters");
        arrival2 =  new Arrival(130, "NY", "   Nine and Three Quarters   ");
        line = new Line(LineResourceData.CENTRAL, "28p", "Canada Line");
        travelDirection = "North";
        arrivalBoard = new ArrivalBoard(line, travelDirection);
        arrivalBoard.addArrival(arrival1);
        arrivalBoard.addArrival(arrival2);
    }

    @Test
    public void testgetNumArrivals() {
        assertEquals(2, arrivalBoard.getNumArrivals());
        assertEquals(arrival2, arrivalBoard.iterator().next());
    }

    @Test
    public void testAddArrivalClear() {
        arrivalBoard.addArrival(new Arrival(300, "NY", "Boundary Line"));
        assertEquals(3, arrivalBoard.getNumArrivals());
        assertEquals(arrival2, arrivalBoard.iterator().next());

        arrivalBoard.clearArrivals();
        assertEquals(0, arrivalBoard.getNumArrivals());
    }

    @Test
    public void testEqualsNoArrivals() {
        ArrivalBoard arrivalBoard1 = new ArrivalBoard(line, travelDirection);
        assertTrue(arrivalBoard.equals(arrivalBoard1));
        assertTrue(arrivalBoard1.equals(arrivalBoard));
    }

    @Test
    public void testEqualsArrivals() {
        ArrivalBoard arrivalBoard1 = new ArrivalBoard(line, travelDirection);
        arrivalBoard1.addArrival(arrival1);
        arrivalBoard1.addArrival(arrival2);
        assertTrue(arrivalBoard.equals(arrivalBoard1));
        assertTrue(arrivalBoard1.equals(arrivalBoard));
    }


    @Test
    public void testNotEquals() {
        Line line2 = new Line(LineResourceData.CENTRAL, "IUP", "JohnnyTown");
        String travelDirection2 = "North";
        ArrivalBoard arrivalBoard1 = new ArrivalBoard(line2, travelDirection2);
        assertTrue(!arrivalBoard.equals(arrivalBoard1));
        assertTrue(!arrivalBoard1.equals(arrivalBoard));
    }

}
