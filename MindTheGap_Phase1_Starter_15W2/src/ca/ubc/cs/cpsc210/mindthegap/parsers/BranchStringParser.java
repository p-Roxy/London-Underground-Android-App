package ca.ubc.cs.cpsc210.mindthegap.parsers;


import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Parser for branch strings in TfL line data
 */
public class BranchStringParser {
    /**
     * Parse a branch string obtained from TFL
     *
     * @param branch  branch string
     * @return       list of lat/lon points parsed from branch string
     */
    public static List<LatLon> parseBranch(String branch) {
        List<LatLon> latLonList = new ArrayList<>();
        if (branch.equals("")) {
            return latLonList;
        }
        Pattern pattern = Pattern.compile("[^.,\\-\\d],*[^.,\\-\\d]*");
        String[] latLons = pattern.split(branch, 0);
        for (String latLon1 : latLons) {
            if (Pattern.matches("((\\-)?(\\d+).(\\d+)),((\\-)?(\\d+).(\\d+))", latLon1)) {
                Pattern p = Pattern.compile(",");
                String[] latLon = p.split(latLon1);
                latLonList.add(new LatLon(Double.parseDouble(latLon[1]), Double.parseDouble(latLon[0])));
            }
        }
        return latLonList;
    }
}
