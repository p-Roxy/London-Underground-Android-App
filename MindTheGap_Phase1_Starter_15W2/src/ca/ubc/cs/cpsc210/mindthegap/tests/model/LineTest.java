package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Branch;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class LineTest {
    private List<Station> stations;
    private LineResourceData lineRD;
    private String id;
    private String name;
    private Set<Branch> branches;
    private Branch branch2;
    private Branch branch3;
    private Line line;
    private Station stn;
    private Station stn2;
    private Station stn3;

    @Before
    public void setUp(){
        line = new Line(LineResourceData.CENTRAL, "NY", "WaterWay");
        stations = new ArrayList<>();
        branches = new HashSet<>();
        stn = new Station("NYN", "New York Water", new LatLon(0.093493, 51.6037));
        stn2 = new Station("NYS", "New York Water", new LatLon(0.093493, 51.6037));
        stn3 = new Station("NYN", "New York OverPass", new LatLon(0.091015, 51.5956));
    }

    @Test
    public void testConstructor() {
        assertEquals(LineResourceData.CENTRAL.getColour(), line.getColour());
        assertEquals("NY", line.getId());
        assertEquals("WaterWay", line.getName());
    }

    @Test
    public void testAddStation() {
        line.addStation(stn);
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(stn));
    }

    @Test
    public void testAddNullBranch() {
        line.addBranch(null);
        assertEquals(0, line.getBranches().size());
    }

    @Test
    public void testAddBranch() {
        makeBranchThree();
        makeBranchTwo();
        line.addBranch(branch2);
        line.addBranch(branch3);
        Set<Branch> branchTest = new HashSet<>();
        branchTest.add(branch2);
        branchTest.add(branch3);
        assertEquals(2, line.getBranches().size());
        assertEquals(branchTest, line.getBranches());
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testAddDIfferentBranch() throws UnsupportedOperationException{
        makeBranchThree();
        makeBranchTwo();
        line.addBranch(branch3);
        Set<Branch> branchTest = new HashSet<>();
        branchTest.add(branch3);
        branchTest.add(branch2);
        line.getBranches().add(branch2);
    }

    @Test
    public void testNullStation() {
        line.addStation(null);
        assertEquals(0, line.getStations().size());
    }

    @Test
    public void testAddSameStation() {
        line.addStation(stn);
        line.addStation(stn);
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(stn));
    }

    @Test
    public void testAddSecondStation() {
        line.addStation(stn);
        line.addStation(stn2);
        assertEquals(2, line.getStations().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line.hasStation(stn2));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testGetStation() throws UnsupportedOperationException{
        line.addStation(stn2);
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(stn2));
        line.getStations().add(stn);
    }

    @Test
    public void testAddSameIdStation() {
        line.addStation(stn);
        line.addStation(stn3);
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line.hasStation(stn3));
        assertTrue(line.getStations().contains(stn));
        assertTrue(line.getStations().contains(stn3));
    }

    @Test
    public void testRemoveSameIDStation() {
        line.addStation(stn);
        line.addStation(stn3);
        assertEquals(1, line.getStations().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line.hasStation(stn3));
        assertEquals(stn, line.getStations().get(0));
        assertEquals(stn3, line.getStations().get(0));
    }


    @Test
    public void testRemoveMultipleStation() {
        line.addStation(stn);
        line.addStation(stn2);
        assertEquals(2, line.getStations().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line.hasStation(stn2));
        assertEquals(stn, line.getStations().get(0));
        assertEquals(stn2, line.getStations().get(1));

        line.removeStation(stn);
        line.removeStation(stn2);
        assertEquals(0, line.getStations().size());
    }
    @Test
    public void testClearMultipleStation() {
        line.addStation(stn);
        line.addStation(stn2);
        assertEquals(2, line.getStations().size());
        assertTrue(line.hasStation(stn));
        assertTrue(line.hasStation(stn2));
        assertEquals(stn, line.getStations().get(0));
        assertEquals(stn2, line.getStations().get(1));
        line.clearStations();
        assertEquals(0, line.getStations().size());
        assertTrue(!stn.hasLine(line));
    }
    private void makeBranchTwo() {
        branch2 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757]]]");
    }
    private void makeBranchThree() {
        branch3 = new Branch("[[[0.005515,-51.5566],[-0.00345,51.5418],[0.033633,-51.5251],[-0.0555,51.5272]]]");
    }
}
