package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.model.exception.ArrivalException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StationTest {
    private Set<Line> lines;
    private String id;
    private String name;
    private Line line;
    private Station stn;
    private Line line2;
    private Line line3;
    private LatLon locn;
    private Arrival arrivalwithDirection;
    private Arrival arrivalwithoutDirection;
    private Arrival arrival2;
    private List<ArrivalBoard> arrivalBoards;
    private String travelDirection;

    @Before
    public void setUp(){
        line = new Line(LineResourceData.CENTRAL, "NY", "WaterWay");
        travelDirection = "inbound";
        arrivalBoards = new ArrayList<>();
        line2 = new Line(LineResourceData.CENTRAL, "NY1", "Underground");
        line3 = new Line(LineResourceData.CENTRAL, "NY", "Underground");
        lines = new HashSet<>();
        locn = new LatLon(0.093493, 51.6037);
        arrivalwithDirection = new Arrival(1800, "NYC", "North - Nine and Three Quarters");
        arrivalwithoutDirection = new Arrival(256, "NY", "North - Nine and Three Quarters");
        arrival2 = new Arrival(720, "BX", "Three Quarters");
        stn = new Station("NYN", "New York Water", locn);
    }

    @Test
    public void testConstructor() {
        assertEquals("NYN" , stn.getID());
        assertEquals("New York Water", stn.getName());
        assertEquals(new LatLon(0.093493, 51.6037) , stn.getLocn());
    }

    @Test
    public void testAddLine() {
        stn.addLine(line);
        assertEquals(1, stn.getLines().size());
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(stn));
        assertTrue(stn.hasLine(line));
    }


    @Test
    public void testAddNullLine() {
        stn.addLine(null);
        assertEquals(0, line.getBranches().size());
    }

    @Test (expected = ArrivalException.class)
    public void testAddIncorrectLineArrival() throws ArrivalException{
        stn.addArrival(line, null);
    }

    @Test
    public void testAddArrival() throws ArrivalException {
        try {
            stn.addLine(line);
            stn.addArrival(line, arrivalwithDirection);
        } catch (ArrivalException e) {
            fail("exception unexpected");
        }
        assertEquals(1, stn.getNumArrivalBoards());
    }

    @Test
    public void testClearArrivalBoards() throws ArrivalException {
        try {
            stn.addLine(line);
            stn.addArrival(line, arrivalwithDirection);
            stn.addArrival(line, arrivalwithoutDirection);
            stn.addArrival(line, arrival2);

            assertEquals(2, stn.getNumArrivalBoards());

        } catch (ArrivalException e) {
            fail("exception unexpected");
        }
    }

    @Test
    public void testAddSameStation() {
        stn.addLine(line);
        stn.addLine(line);
        assertEquals(1, line.getStations().size());
        assertEquals(1, stn.getLines().size());
        assertTrue(line.hasStation(stn));
        assertTrue(stn.hasLine(line));
    }

    @Test
    public void testAddSecondStation() {
        stn.addLine(line);
        stn.addLine(line2);
        assertEquals(1, line.getStations().size());
        assertEquals(1, line2.getStations().size());
        assertEquals(2, stn.getLines().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line2.hasStation(stn));
        assertTrue(stn.hasLine(line));
        assertTrue(stn.hasLine(line2));
    }


    @Test
    public void testRemoveMultipleStation() {
        stn.addLine(line);
        stn.addLine(line2);
        assertEquals(1, line.getStations().size());
        assertEquals(1, line2.getStations().size());
        assertEquals(2, stn.getLines().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line2.hasStation(stn));
        assertTrue(stn.hasLine(line));
        assertTrue(stn.hasLine(line2));

        stn.removeLine(line);
        stn.removeLine(line2);
        assertEquals(0, stn.getLines().size());
        assertEquals(0, line.getStations().size());
        assertEquals(0, line2.getStations().size());
    }

    @Test
    public void testRemoveSameIDStation() {
        stn.addLine(line);
        stn.addLine(line3);
        assertEquals(1, line.getStations().size());
        assertEquals(0, line3.getStations().size());
        assertEquals(1, stn.getLines().size());
        assertTrue(line.hasStation(stn));
        assertTrue(stn.hasLine(line));

        stn.removeLine(line);
        assertEquals(0, stn.getLines().size());
        assertEquals(0, line.getStations().size());
    }

}
