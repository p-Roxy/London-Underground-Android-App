package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLLineDataMissingException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import javax.json.*;
import java.io.StringReader;

/**
 * A parser for the data returned by TFL line route query
 */
public class TfLLineParser extends TfLAbstractParser {

    /**
     * Parse line from JSON response produced by TfL.
     *
     * @param lmd              line meta-data
     * @return                 line parsed from TfL data
     * @throws JsonException   when JSON data does not have expected format
     * @throws TfLLineDataMissingException when
     * <ul>
     *  <li> JSON data is missing lineName, lineId or stopPointSequences elements </li>
     *  <li> or, for a given sequence: </li>
     *    <ul>
     *      <li> the stopPoint array is missing, or </li>
     *      <li> all station elements are missing one of name, lat, lon or stationId elements </li>
     *    </ul>
     * </ul>
     */
    public static Line parseLine(LineResourceData lmd, String jsonResponse)
            throws JsonException, TfLLineDataMissingException {
        JsonReader reader = Json.createReader(new StringReader(jsonResponse));
        JsonObject rootJSON = reader.readObject();

        try {
            Line line = makeLine(lmd, rootJSON);
            JsonArray stations = rootJSON.getJsonArray("stations");
            for (int count = 0; count < stations.size(); count++) {
                Station station = makeStation(count, stations);
                line.addStation(station);
            }
            StationManager.getInstance().addStationsOnLine(line);
            return line;
        } catch (NullPointerException | TfLLineDataMissingException e){
            throw new TfLLineDataMissingException();
        } catch (Exception e) {
            throw new JsonException("");
        }
    }

    private static Line makeLine(LineResourceData lmd, JsonObject line) throws TfLLineDataMissingException{
        Line lineObject;
        String lineId = line.getString("lineId");
        String lineName = line.getString("lineName");
        lineObject = new Line(lmd, lineId, lineName);
        makeBranch(line.getJsonArray("lineStrings"), lineObject);

        return lineObject;
    }

    private static void makeBranch(JsonArray lines, Line line) throws TfLLineDataMissingException{
        for (int count = 0; count < lines.size(); count++) {
            Branch branch1;
            String branchString = lines.getString(count);
            branch1 = new Branch(branchString);
            line.addBranch(branch1);
        }

    }

    private static Station makeStation(int count, JsonArray station) throws TfLLineDataMissingException{
        String stationId = station.getJsonObject(count).getString("stationId");
        if (StationManager.getInstance().getStationWithId(stationId) != null) {
            return StationManager.getInstance().getStationWithId(stationId);
        } else {
            int stationLat = station.getJsonObject(count).getInt("lat");
            int stationLon = station.getJsonObject(count).getInt("lon");
            String stationName = parseName(station.getJsonObject(count).getString("name"));
            LatLon latLon = new LatLon(stationLat, stationLon);
            return new Station(stationId, stationName, latLon);
        }
    }
}

