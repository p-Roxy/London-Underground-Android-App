package ca.ubc.cs.cpsc210.mindthegap.model;

import java.util.*;

/**
 * Represents a line on the underground with a name, id, list of stations and list of branches.
 *
 * Invariants:
 * - no duplicates in list of stations
 * - stations must be maintained in the order in which they were added to the line
 */
public class Line implements Iterable<Station> {
    private List<Station> stations;
    private LineResourceData lineRD;
    private String id;
    private String name;
    private Set<Branch> branches;

    /**
     * Constructs a line with given resource data, id and name.
     * List of stations and list of branches are empty.
     *
     * @param lmd     the line meta-data
     * @param id      the line id
     * @param name    the name of the line
     */
    public Line(LineResourceData lmd, String id, String name) {
        lineRD = lmd;
        this.id = id;
        this.name = name;
        stations = new ArrayList<>();
        branches = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    /**
     * Get colour specified by line resource data
     *
     * @return  colour in which to plot this line
     */
    public int getColour() {
        return lineRD.getColour();
    }

    /**
     * Add station to line, if it's not already there.
     *
     * @param stn  the station to add to this line
     */
    public void addStation(Station stn) {
        if (!hasStation(stn) && stn != null) {
            stations.add(stn);
            stn.addLine(this);
        }
    }

    /**
     * Remove station from line
     *
     * @param stn  the station to remove from this line
     */
    public void removeStation(Station stn) {
        if (hasStation(stn)) {
            stations.remove(stn);
            stn.removeLine(this);
        }
    }

    /**
     * Remove all stations from this line
     */
    public void clearStations() {
        for (Station next: stations) {
            next.removeLine(this);
        }
        stations.clear();
    }

    /**
     *
     * @return unmodifiable view of list of stations on this line
     */
    public List<Station> getStations() {
        return Collections.unmodifiableList(stations);
    }

    /**
     * Determines if this line has a given station
     *
     * @param stn  the station
     * @return  true if line has the given station
     */
    public boolean hasStation(Station stn) {
        return stations.contains(stn);
    }

    /**
     * Add a branch to this line
     *
     * @param b  the branch to add to line
     */
    public void addBranch(Branch b) {
        if (b != null) {
            branches.add(b);
        }
    }

    /**
     *
     * @return unmodifiable view of set of all branches in this line
     */
    public Set<Branch> getBranches() {
        return Collections.unmodifiableSet(branches);
    }



    /**
     * Two lines are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;

        Line stations = (Line) o;

        return getId().equals(stations.getId());
    }

    /**
     * Two lines are equal if their ids are equal
     */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public Iterator<Station> iterator() {
        return stations.iterator();
    }
}

