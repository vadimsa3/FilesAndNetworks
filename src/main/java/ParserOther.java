import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ParserOther {

    // !!! ВАРИАНТ ПАРСИНГА ПОД ФОРМИРОВАНИЕ НОВОЙ СТАНЦИИ !!!

    private Document document;
    private ContainerStations containerStations;
    private List<LineMetro> lines;
    private ParsData collector;

    public ParserOther(String url) throws Exception {
        document = Jsoup.connect(url).maxBodySize(0).get();
        lines = new ArrayList<>();
        containerStations = new ContainerStations();
    }

    public List<LineMetro> getLines() {
        return lines;
    }

    public ContainerStations getContainerStations() {
        return containerStations;
    }

    // парсим линии из страницы HTML
    public JSONArray parseLine() {
        Elements linesList = document.getElementsByAttributeValueStarting(
                "class", "js-metro-line");
        JSONArray linesObjectArray = new JSONArray();
        for(Element element : linesList) {
            LineMetro line = new LineMetro(element.attr("data-line"), element.ownText());
            linesObjectArray.add(line.getJsonLine());
            lines.add(line);
        }
        return linesObjectArray;
    }

    // парсим станции из страницы HTML
     public JSONObject parseStation() {
        Elements dataList = document.getElementsByClass("js-metro-stations");
        JSONObject stationsObject = new JSONObject();
        for(Element element : dataList) {
            JSONArray stationsArray = new JSONArray();
            Elements stationsList = element.getElementsByClass("name");
            for(Element stationElement : stationsList) {
                stationsArray.add(stationElement.text());
                stationsObject.put(element.attr("data-line"), stationsArray);

                containerStations.addStation(new Station(stationElement.text(), element.attr("data-line")));
            }
        }
         return stationsObject;
     }

    // парсим станции - переходы из страницы HTML
    public void parseConnection() {
        Elements dataList = document.getElementsByClass("js-metro-stations");
        for(Element element : dataList) {
            Elements connectionsList = element.select("p:has(span[title])");
            for(Element connectionElement : connectionsList) {
                String station = connectionElement.text();
                int indexSpace = station.lastIndexOf(";");
                String stationName = station.substring(indexSpace + 1).trim();

                Connections stationsConnection = new Connections();
                stationsConnection.addStation(new Station(stationName, element.attr("data-line")));

                Elements connectionsSpanList = connectionElement.select("span[title]");
                for(Element conSpanElement : connectionsSpanList) {
                    String line = conSpanElement.attr("class");
                    int indexDash = line.lastIndexOf("-");
                    String numberLine = line.substring(indexDash + 1);

                    String text = conSpanElement.attr("title");
                    int indexQuote = text.indexOf("«");
                    int lastIndexQuote = text.lastIndexOf("»");
                    String station1 = text.substring(indexQuote + 1, lastIndexQuote);

                    stationsConnection.addStation(new Station(station1, numberLine));
                }
                containerStations.addConnection(stationsConnection);
            }
        }
    }
    // записываем переходы в JSON
    public JSONArray writeConnectionsInJSON(TreeSet<Connections> connections) {
        JSONArray connectionsArray = new JSONArray();
        for(Connections stationsCon : connections) {
            JSONArray connectionObjectArray = new JSONArray();
            for(Station station : stationsCon.getConnectionStations()) {
                JSONObject connectionObject = new JSONObject();
                connectionObject.put("line", station.getLineNumber());
                connectionObject.put("station", station.getStationName());
                connectionObjectArray.add(connectionObject);
            }
            connectionsArray.add(connectionObjectArray);
        }
        return connectionsArray;
    }
}
