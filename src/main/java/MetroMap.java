import java.util.List;
import java.util.Map;

public class MetroMap {

    private List<LineMetro> lines;
    private Map<String, List<String>> stations;

    // конструктор метро

    public MetroMap(List<LineMetro> lines, Map<String, List<String>> stations) {
        this.lines = lines;
        this.stations = stations;
    }
}
