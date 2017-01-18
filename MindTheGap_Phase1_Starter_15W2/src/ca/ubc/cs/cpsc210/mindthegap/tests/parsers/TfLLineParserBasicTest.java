package ca.ubc.cs.cpsc210.mindthegap.tests.parsers;

import ca.ubc.cs.cpsc210.mindthegap.TfL.DataProvider;
import ca.ubc.cs.cpsc210.mindthegap.TfL.FileDataProvider;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.model.StationManager;
import ca.ubc.cs.cpsc210.mindthegap.parsers.TfLLineParser;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLLineDataMissingException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Unit test for TfLLineParser
 */
public class TfLLineParserBasicTest {
    private String lineData;
    private String lineData2;
    private String lineData3;

    @Before
    public void setUp() throws Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/central_inbound.json");
        lineData = dataProvider.dataSourceToString();
    }

    @Test
    public void testBasicLineParsing() {
        try {
            (new TfLLineParser()).parseLine(null, lineData);
        } catch (TfLLineDataMissingException e) {
            fail("TfLLineDataMissingException not expected");
        }
    }

    @Test (expected = TfLLineDataMissingException.class)
    public void testMissingFields() throws Exception {
        makeNewLineData();
        (new TfLLineParser()).parseLine(null, lineData2);
    }

    @Test (expected = TfLLineDataMissingException.class)
    public void testMissingStations() throws Exception {
        makeNewLineData3();
        (new TfLLineParser()).parseLine(null, lineData2);
    }

    @Test (expected = JsonException.class)
    public void testFormatError() throws Exception {
        makeNewLineData2();
        (new TfLLineParser()).parseLine(null, lineData3);
    }


    @Test
    public void testLineContainsStations() throws Exception {
        Line line = (new TfLLineParser()).parseLine(LineResourceData.CENTRAL, lineData);
        assertEquals(49, line.getStations().size());
        assertEquals(new Station("940GZZLUBKE", "Barkingside", new LatLon(51.585695, 0.08859599999999999)), line.getStations().get(0));
    }

    @Test
    public void testTwoLinesConstructAndStations() throws Exception {
        StationManager.getInstance().clearStations();
        makeNewLineData4();
        Line line = new TfLLineParser().parseLine(null, lineData);
        assertEquals(49, line.getStations().size());
        assertEquals(49, StationManager.getInstance().getNumStations());
        Line line1 = new TfLLineParser().parseLine(null, lineData2);
        assertEquals(60, line1.getStations().size());
        assertTrue(StationManager.getInstance().getNumStations() < (60 + 49));

    }

    @Test
    public void testLine2() throws Exception {
        makeNewLineData4();
        Line line = new TfLLineParser().parseLine(null, lineData2);
        assertEquals(60, line.getStations().size());
    }

    private void makeNewLineData() throws Exception{
        DataProvider dataProvider = new FileDataProvider("./res/raw/central_inbound_missing_name.json");
        lineData2 = dataProvider.dataSourceToString();
    }

    private void makeNewLineData2() throws Exception{
        DataProvider dataProvider = new FileDataProvider("./res/raw/central_inbound_format.json");
        lineData3 = dataProvider.dataSourceToString();
    }

    private void makeNewLineData3() throws Exception{
        DataProvider dataProvider = new FileDataProvider("./res/raw/central_inbound_missing_stations.json");
        lineData2 = dataProvider.dataSourceToString();
    }

    private void makeNewLineData4() throws Exception{
        DataProvider dataProvider = new FileDataProvider("./res/raw/district_inbound.json");
        lineData2 = dataProvider.dataSourceToString();
    }

}