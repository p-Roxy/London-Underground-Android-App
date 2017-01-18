package ca.ubc.cs.cpsc210.mindthegap.tests.parsers;

import ca.ubc.cs.cpsc210.mindthegap.TfL.DataProvider;
import ca.ubc.cs.cpsc210.mindthegap.TfL.FileDataProvider;
import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.parsers.TfLArrivalsParser;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLArrivalsDataMissingException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests to check basic parsing for TfL Arrivals data
 */
public class TfLArrivalsParserBasicTest extends AbstractTfLArrivalsParserTest {
    private String arrivalsJsonData;

    @Before
    public void setUp() throws Exception {
        DataProvider dataProvider = new FileDataProvider("./res/raw/arrivals_oxc.json");
        arrivalsJsonData = dataProvider.dataSourceToString();
        stn = new Station("id", "Oxford Circus", new LatLon(51.5, -0.1));
        stn.addLine(new Line(LineResourceData.CENTRAL, "central", "Central"));
        stn.addLine(new Line(LineResourceData.VICTORIA, "victoria", "Victoria"));
    }

    @Test
    public void testArrivalsBasicParsing() {
        try {
            (new TfLArrivalsParser()).parseArrivals(stn, arrivalsJsonData);
        } catch (TfLArrivalsDataMissingException e) {
            fail("Parser threw TfLArrivalsDataMissingException");
        } catch (Exception e) {
            fail("Parser threw unexpected exception");
        }
    }

    @Test
    public void testArrivalsMultiple() {
        try {
            (new TfLArrivalsParser()).parseArrivals(stn, arrivalsJsonData);
            assertEquals(4, stn.getNumArrivalBoards());
        } catch (TfLArrivalsDataMissingException e) {
            fail("Parser threw TfLArrivalsDataMissingException");
        } catch (Exception e) {
            fail("Parser threw unexpected exception");
        }
    }

    @Test
    public void testDestinationName() {
        try {
            DataProvider dataProvider = new FileDataProvider("./res/raw/arrivals_oxc_missing_destinationName.json");
            arrivalsJsonData = dataProvider.dataSourceToString();
            (new TfLArrivalsParser()).parseArrivals(stn, arrivalsJsonData);
            assertEquals(1, stn.getNumArrivalBoards());
            assertEquals("Ealing Broadway", stn.iterator().next().iterator().next().getDestination());
        } catch (TfLArrivalsDataMissingException e) {
            fail("Parser threw TfLArrivalsDataMissingException");
        } catch (Exception e) {
            fail("Parser threw unexpected exception");
        }
    }

    @Test (expected = TfLArrivalsDataMissingException.class)
    public void testTFLException() throws TfLArrivalsDataMissingException, IOException {
        DataProvider dataProvider = new FileDataProvider("./res/raw/arrivals_oxc_missing.json");
        arrivalsJsonData = dataProvider.dataSourceToString();
        new TfLArrivalsParser().parseArrivals(stn, arrivalsJsonData);
    }
}