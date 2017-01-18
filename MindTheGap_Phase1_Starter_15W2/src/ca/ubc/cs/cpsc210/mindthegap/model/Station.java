package ca.ubc.cs.cpsc210.mindthegap.model;

import ca.ubc.cs.cpsc210.mindthegap.model.exception.ArrivalException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import java.util.*;

/**
 * Represents a station on the underground with an id, name, location (lat/lon)
 * set of lines that stop at this station and a list of arrival boards.
 */
public class Station implements Iterable<ArrivalBoard> {
    private List<ArrivalBoard> arrivalBoards;
    private String id;
    private String name;
    private LatLon latLon;
    private Set<Line> lines;

    /**
     * Constructs a station with given id, name and location.
     * Set of lines and list of arrival boards are empty.
     *
     * @param id    the id of this station (cannot by null)
     * @param name  name of this station
     * @param locn  location of this station
     */
    public Station(String id, String name, LatLon locn) {
        this.id = id;
        this.name = name;
        this.latLon = locn;
        arrivalBoards = new ArrayList<>();
        lines = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public LatLon getLocn() {
        return latLon;
    }

    public String getID() {
        return id;
    }

    /**
     *
     * @return  returns an unmodifiable view of the set of lines through this station
     */
    public Set<Line> getLines() {
        return Collections.unmodifiableSet(lines);
    }

    public int getNumArrivalBoards() {
        return arrivalBoards.size();
    }

    /**
     * Add line to set of lines with stops at this station.
     *
     * @param line  the line to add
     */
    public void addLine(Line line) {
        if (!hasLine(line) && line != null) {
            lines.add(line);
            line.addStation(this);
        }
    }

    /**
     * Remove line from set of lines with stops at this station
     *
     * @param line the line to remove
     */
    public void removeLine(Line line) {
        if (hasLine(line)) {
            lines.remove(line);
            line.removeStation(this);
        }
    }

    /**
     * Determine if this station is on a given line
     * @param line  the line
     * @return  true if this station is on given line
     */
    public boolean hasLine(Line line) {
        return lines.contains(line);
    }

    /**
     * Add train arrival travelling on a particular line in a particular direction to this station.
     * Throws ArrivalException if line does not run through this station.  Otherwise,
     * arrival is added to corresponding arrival board based on the line on which it is
     * operating and the direction of travel (as indicated by platform prefix).  If the arrival
     * board for given line and travel direction does not exist, it is created and added to
     * arrival boards for this station.
     *
     * @param line    line on which train is travelling
     * @param arrival the train arrival to add to station
     * @throws ArrivalException when given line does not run through this station
     */
    public void addArrival(Line line, Arrival arrival) throws ArrivalException {
        if (!hasLine(line)) {
            throw new ArrivalException();
        }
        Boolean arrivalAdded = false;
        for (ArrivalBoard next : arrivalBoards) {
            if (arrival.getTravelDirn().equals(next.getTravelDirn()) && next.getLine().equals(line)) {
                next.addArrival(arrival);
                arrivalAdded = true;
            }
        }
        if (!arrivalAdded){
            makeNewArrivalBoard(line, arrival);
        }
    }

    private void makeNewArrivalBoard(Line line, Arrival arrival) {
        ArrivalBoard arrivalBoard = new ArrivalBoard(line, arrival.getTravelDirn());
        arrivalBoard.addArrival(arrival);
        arrivalBoards.add(arrivalBoard);
    }

    /**
     * Remove all arrival boards from this station
     */
    public void clearArrivalBoards() {
        arrivalBoards.clear();
    }

    /**
     * Two stations are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Station)) return false;

        Station that = (Station) o;

        return id.equals(that.id);

    }


    /**
     * Two stations are equal if their ids are equal
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public Iterator<ArrivalBoard> iterator() {
        return arrivalBoards.iterator();
    }
}