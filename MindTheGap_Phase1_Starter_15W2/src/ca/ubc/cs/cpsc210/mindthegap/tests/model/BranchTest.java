package ca.ubc.cs.cpsc210.mindthegap.tests.model;

import ca.ubc.cs.cpsc210.mindthegap.model.Branch;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BranchTest {
    private String line;
    private Branch branch;
    private Branch branch2;
    private Branch branch3;
    private List<LatLon> latLonList;
    private List<LatLon> latLonList2;
    private LatLon latLon1;
    private LatLon latLon2;
    private LatLon latLon3;
    private LatLon latLon4;


    @Before
    public void setUp(){
        latLonList = new ArrayList<>();
        latLonList2 = new ArrayList<>();
        line = "[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757]]]";
        branch = new Branch(line);
    }

    @Test
    public void testConstructor() {
        makeLatLon1();
        assertEquals(latLonList, branch.getPoints());
    }

    @Test
    public void testEquals() {
        makeBranchTwo();
        assertTrue(branch.equals(branch2));
        assertTrue(branch2.equals(branch));
    }

    @Test
    public void testAddBranch() {
        makeBranchTwo();
        makeBranchThree();

    }

    @Test
    public void testNotEquals() {
        makeBranchThree();
        assertFalse(branch.equals(branch3));
        assertFalse(branch3.equals(branch));
    }

    @Test
    public void testNullEquals() {
        assertFalse(branch.equals(null));
    }

    private void makeLatLon1() {
        latLon1 = new LatLon(51.6037, 0.093493);
        latLon2 = new LatLon(51.5956, 0.091015);
        latLon3 = new LatLon(51.5857, 0.088596);
        latLon4 = new LatLon(51.5757, 0.090015);
        latLonList.add(latLon1);
        latLonList.add(latLon2);
        latLonList.add(latLon3);
        latLonList.add(latLon4);
    }

    private void makeBranchTwo() {
        branch2 = new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757]]]");
    }

    private void makeBranchThree() {
        branch3 = new Branch("[[[0.005515,-51.5566],[-0.00345,51.5418],[0.033633,-51.5251],[-0.0555,51.5272]]]");
    }
}
