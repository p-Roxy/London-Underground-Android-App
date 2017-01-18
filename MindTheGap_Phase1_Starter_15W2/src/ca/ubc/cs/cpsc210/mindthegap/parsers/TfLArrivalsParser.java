package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.model.exception.ArrivalException;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLArrivalsDataMissingException;

import javax.json.*;
import java.io.StringReader;
import java.util.Set;
import java.util.jar.JarException;

import static ca.ubc.cs.cpsc210.mindthegap.parsers.TfLLineParser.parseLine;

/**
 * A parser for the data returned by the TfL station arrivals query
 */
public class TfLArrivalsParser extends TfLAbstractParser {

    /**
     * Parse arrivals from JSON response produced by TfL query.  Parsed arrivals are
     * added to given station assuming that arrival is on a line that passes
     * through the station and that corresponding JSON object has all of:
     * timeToStation, platformName, lineID and one of destinationName or towards.  If
     * any of the aforementioned elements is missing, or if arrival is on a line
     * that does not pass through the station, the arrival is not added to the station.
     *
     * @param stn             station to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by TfL
     * @throws JsonException  when JSON response does not have expected format
     * @throws TfLArrivalsDataMissingException  when all arrivals are missing at least one of the following:
     *      <ul>
     *          <li>timeToStation</li>
     *          <li>platformName</li>
     *          <li>lineId</li>
     *          <li>destinationName and towards</li>
     *      </ul>
     *  or if all arrivals are on a line that does not run through given station.
     */
    public static void parseArrivals(Station stn, String jsonResponse)
            throws JsonException, TfLArrivalsDataMissingException {

        JsonReader reader = Json.createReader(new StringReader(jsonResponse));

        JsonArray arrivals = reader.readArray();

        for (int count = 0; count < arrivals.size(); count++) {
            Arrival arrival = makeArrival(count, arrivals);
            String lineID = lineID(count, arrivals);
            Line line = checkLine(stn, lineID);
            addArrival(stn, line, arrival);
        }
    }

    private static void addArrival(Station station, Line line, Arrival arrival) throws TfLArrivalsDataMissingException {
        try {
            station.addArrival(line, arrival);
        } catch (ArrivalException e) {
            throw new TfLArrivalsDataMissingException();
        }
    }

    private static Line checkLine(Station station, String lineID){
        for (Line next: station.getLines()) {
            if (next.getId().equals(lineID)) {
                return next;
            }
        }
        return null;
    }

    private static String lineID(int count, JsonArray arrivals) throws TfLArrivalsDataMissingException {
        try {
            return arrivals.getJsonObject(count).getString("lineId");
        } catch (NullPointerException e) {
            throw new TfLArrivalsDataMissingException();
        } catch (Exception e) {
            throw new JsonException("");
        }
    }

    private static Arrival makeArrival(int count, JsonArray arrivals) throws TfLArrivalsDataMissingException {
        try {
            int timeToStation = arrivals.getJsonObject(count).getInt("timeToStation");
            String destination = makeDestination(count, arrivals);
            String platformName = arrivals.getJsonObject(count).getString("platformName");
            return new Arrival(timeToStation, destination, platformName);
        } catch (NullPointerException e) {
            throw new TfLArrivalsDataMissingException();
        } catch (Exception e) {
            throw new JsonException("Wrong Format");
        }
    }

    private static String makeDestination (int count, JsonArray arrivals) {

        try {
            return parseName(arrivals.getJsonObject(count).getString("destinationName"));
        }catch (NullPointerException e) {
            return arrivals.getJsonObject(count).getString("towards");
        }catch (Exception e) {
            throw new JsonException("");
        }
    }
}
