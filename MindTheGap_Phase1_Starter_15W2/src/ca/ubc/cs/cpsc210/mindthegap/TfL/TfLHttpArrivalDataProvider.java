package ca.ubc.cs.cpsc210.mindthegap.TfL;

/*
 * Copyright 2015-2016 Department of Computer Science UBC
 */

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Wrapper for TfL Arrival Data Provider
 */
public class TfLHttpArrivalDataProvider extends AbstractHttpDataProvider {
    private static final String ARRIVALS_API_BASE = "https://api.tfl.gov.uk/";              // for TfL data
    //private static final String ARRIVALS_API_BASE = "http://kunghit.ugrad.cs.ubc.ca:6060";   // for simulated data (3pm to midnight)
    private Station stn;

    public TfLHttpArrivalDataProvider(Station stn) {
        super();
        this.stn = stn;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        Set<Line> lines = stn.getLines();
        String lineIds = "Line/";
        for (Line next: lines) {
            lineIds += next.getId().trim() + ",";
        }
        String arrivalsStopId = "/Arrivals?stopPointId=" + stn.getID() + "&app_id=&app_key=";
        String url = ARRIVALS_API_BASE + lineIds + arrivalsStopId;
        return new URL(url);
    }
}
