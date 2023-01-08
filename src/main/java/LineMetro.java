import org.json.simple.JSONObject;

import java.util.List;

public class LineMetro implements Comparable<LineMetro> {

    public static List<LineMetro> lines;
    private List<Station> stations;

    private final String number;
    private final String name;
    private final JSONObject jsonLine;

    // конструктор линий
    public LineMetro(String number, String name) {
        this.number = number;
        this.name = name;
        jsonLine = new JSONObject();
        jsonLine.put("number", number);
        jsonLine.put("name", name);
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public List<Station> getStations()
    {
        return stations;
    }

    public JSONObject getJsonLine() { return jsonLine; }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(LineMetro o) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((LineMetro) obj) == 0;
    }
}
