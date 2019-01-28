package tk.logiik.vivanfc.viva.values;

import java.util.Map;
import java.util.TreeMap;

public class Line {

    private String name;
    private Map<Integer, Station> stations;

    Line(String name) {
        this.name = name;
        this.stations = new TreeMap<>();
    }


    // name
    public String getName() {
        return name;
    }

    // stations
    void addStation(int stationId, String stationName) {
        Station station = new Station(stationName);
        stations.put(stationId, station);
    }

    public Station getStation(int stationId) {
        return stations.get(stationId);
    }

}
