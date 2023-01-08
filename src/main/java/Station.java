public class Station implements Comparable<Station> {

    private String lineNumber;
    private String stationName;

    private String lineName;
    private String dateStation;
    private String depthStation;
    private boolean hasConnection = false;

    // конструктор станций

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public Station(
            String stationName, String lineName,
            String dateStation, String depthStation,
            boolean hasConnection) {
        this.stationName = stationName;
        this.lineName = lineName;
        this.dateStation = dateStation;
        this.depthStation = depthStation;
        this.hasConnection = hasConnection;
    }

    public Station(String stationName, String lineNumber) {
        this.stationName = stationName;
        this.lineNumber = lineNumber;
    }

    public String getLineName() { return lineName; }

    public String getDateStation() {
        return dateStation;
    }

    public String getDepthStation() { return depthStation; }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getStationName() { return stationName; }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setDateStation(String dateStation) {
        this.dateStation = dateStation;
    }

    public void setDepthStation(String depthStation) {
        this.depthStation = depthStation;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString() {
        return stationName;
    }

    public int compareTo(Station station) {
        int lineComparison = lineNumber.compareToIgnoreCase(station.getLineNumber());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return stationName.compareToIgnoreCase(station.getStationName());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Station) obj) == 0;
    }
}
