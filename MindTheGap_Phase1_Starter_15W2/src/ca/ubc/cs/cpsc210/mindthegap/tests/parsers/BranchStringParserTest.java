package ca.ubc.cs.cpsc210.mindthegap.tests.parsers;

import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static ca.ubc.cs.cpsc210.mindthegap.parsers.BranchStringParser.parseBranch;


import static org.junit.Assert.assertEquals;



public class BranchStringParserTest {
    private List<LatLon> latLonList;



    @Before
    public void setUp(){
        latLonList = new ArrayList<>();
    }

    @Test
    public void testEmptyString() {
        String emptyString = "";
        assertEquals(latLonList, parseBranch(emptyString));
    }

    @Test
    public void testPositiveString() {
        String string = "[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857],[0.090015,51.5757]]]";
        latLonList.add(new LatLon(51.6037, 0.093493));
        latLonList.add(new LatLon(51.5956, 0.091015));
        latLonList.add(new LatLon(51.5857, 0.088596));
        latLonList.add(new LatLon(51.5757, 0.090015));
        assertEquals(latLonList, parseBranch(string));
        assertEquals(4, parseBranch(string).size());
        assertEquals(new LatLon(51.6037, 0.093493), parseBranch(string).get(0));
        assertEquals(new LatLon(51.5956, 0.091015), parseBranch(string).get(1));
        assertEquals(new LatLon(51.5857, 0.088596), parseBranch(string).get(2));
        assertEquals(new LatLon(51.5757, 0.090015), parseBranch(string).get(3));
    }

    @Test
    public void testSingleString() {
        String string = "[[[0.093493,51.6037]]]";
        latLonList.add(new LatLon(51.6037, 0.093493));
        assertEquals(latLonList, parseBranch(string));
        assertEquals(1, parseBranch(string).size());
        assertEquals(new LatLon(51.6037, 0.093493), parseBranch(string).get(0));
    }

    @Test
    public void testSingleStringNoBrackets() {
        String string = "0.093493,51.6037";
        latLonList.add(new LatLon(51.6037, 0.093493));
        assertEquals(latLonList, parseBranch(string));
        assertEquals(1, parseBranch(string).size());
        assertEquals(new LatLon(51.6037, 0.093493), parseBranch(string).get(0));
    }

    @Test
    public void testNegativeLatString() {
        String string = "[[[-0.005515,51.5566],[-0.00345,51.5418],[-0.033633,51.5251],[-0.0555,51.5272]]]";
        latLonList.add(new LatLon(51.5566, -0.005515));
        latLonList.add(new LatLon(51.5418, -0.00345));
        latLonList.add(new LatLon(51.5251, -0.033633));
        latLonList.add(new LatLon(51.5272, -0.0555));
        assertEquals(latLonList, parseBranch(string));
    }

    @Test
    public void testNegativeLonString() {
        String string = "[[[0.005515,-51.5566],[-0.00345,51.5418],[0.033633,-51.5251],[-0.0555,51.5272]]]";
        latLonList.add(new LatLon(-51.5566, 0.005515));
        latLonList.add(new LatLon(51.5418, -0.00345));
        latLonList.add(new LatLon(-51.5251, 0.033633));
        latLonList.add(new LatLon(51.5272, -0.0555));
        assertEquals(latLonList, parseBranch(string));
    }

}
