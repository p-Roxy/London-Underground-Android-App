package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.model.StationManager;
import ca.ubc.cs.cpsc210.mindthegap.model.exception.StationException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for StationManager
 */
public class StationManagerTest {
    private StationManager stnManager;
    private Station stn;
    private Station stn2;
    private Station stn3;
    private Line line;

    @Before
    public void setUp() {
        stnManager = StationManager.getInstance();
        stnManager.clearSelectedStation();
        stnManager.clearStations();
    }

    @Test
    public void testStationManagerConstructor() {
        assertEquals(stnManager.getSelected(), null);
    }

    @Test
    public void testfindNearestToNull() throws StationException {
        assertEquals(stnManager.getSelected(), null);
        line = new Line(LineResourceData.CENTRAL, "NY", "WaterWay");

        stn = new Station("NYN", "New York Water", new LatLon(0.093493, 51.6037));
        stn2 = new Station("NYS", "New York Water", new LatLon(0.093493, 51.6037));
        stn3 = new Station("NY2", "New York OverPass", new LatLon(0.091015, 51.5956));
        line.addStation(stn);
        line.addStation(stn2);
        line.addStation(stn3);

        stnManager.addStationsOnLine(line);

        assertEquals(stn2, stnManager.getStationWithId("NYS"));
        assertEquals(3, stnManager.getNumStations());

        stnManager.setSelected(stn3);

        assertEquals(stn, stnManager.findNearestTo(new LatLon(0.0934500, 51.6037)));

        assertEquals(stn3, stnManager.findNearestTo(new LatLon(0.090000, 51.6037)));

        assertEquals(null, stnManager.findNearestTo(new LatLon(0.0934500, 79.00000)));


    }

    @Test
    public void testfindNearestTo() {
        assertEquals(stnManager.getSelected(), null);
    }

// NOTE: you will need to add further test methods
}