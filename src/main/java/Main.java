import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // URL адрес карты
        String METRO_URL = "https://skillbox-java.github.io/";

        ParserWeb parserWeb = new ParserWeb();
        CreateJSON createJSON = new CreateJSON();
        ParserJSON parsJSON = new ParserJSON();
        ParserCSV parsCSV = new ParserCSV();

        System.out.println("Задание 1. Парсинг веб-страницы и получение линий");
        parserWeb.getLine(parserWeb.getHTML(METRO_URL));
        System.out.println();

        System.out.println("Задание 2. Парсинг веб-страницы и получение станций");
        parserWeb.getStation(parserWeb.getHTML(METRO_URL));
        System.out.println();

        System.out.println("Задание 3. Создание JSON файла со списком линий и списком станций по линиям");
        createJSON.print();
        System.out.println("Создан файл: data/map.json");
        System.out.println();

        System.out.println("Задание 4. Поиск файлов формата .json и .csv в папке");
        ParsFolders parsFolders = new ParsFolders();
        String pathToFolder = "data"; // место поиска
        // поиск файлов с расширением .json
        String extension1 = ".json";
        parsFolders.findFiles(pathToFolder, extension1);
        System.out.println();
        // поиск файлов с расширением .csv
        String extension2 = ".csv";
        parsFolders.findFiles(pathToFolder, extension2);
        System.out.println();

        System.out.println("Задание 5. Класс парсинга файлов формата JSON. Выдает список соответствующих объектов");
        String jsonFile = "D:/SAVCHUK/SKILLBOX/java_basics/FilesAndNetwork/DataCollector/data/2/4/depths-1.json";
        System.out.println("Json-simple parse");
        parsJSON.parseSimple(jsonFile);
        System.out.println("GSON parse");
        parsJSON.parseGSON(jsonFile);
        System.out.println("JAСKSON parse");
        parsJSON.parseJAСKSON(jsonFile);
        System.out.println();

        System.out.println("Задание 6. Класс парсинга файлов формата CSV. Выдает список соответствующих объектов");
        String fileCSV = "D:/SAVCHUK/SKILLBOX/java_basics/FilesAndNetwork/DataCollector/data/4/6/dates-1.csv";
        parsCSV.parseCSV(fileCSV);
        System.out.println();

        System.out.println("Задание 7. Класс, создающий файл stations.json по шаблону");

        ParsData dataCollector = new ParsData();

        // передаём в метод путь к папке файлов и собираем глубины и даты
        dataCollector.fileReader(
                "D:/SAVCHUK/SKILLBOX/java_basics/FilesAndNetwork/DataCollector/src/main/resources/");

        // получаем список станций с глубинами и датами
        Map<String, Station> listStations = dataCollector.getListStations();

        // получаем список линий
        ParserOther parserOther = new ParserOther(METRO_URL);
        JSONArray linesArray = parserOther.parseLine();

        // получаем список станций
        parserOther.parseStation();
        List<Station> stations = parserOther.getContainerStations().getStations();

        // добавим названий линий в listStations
        setParameterLineName(stations, linesArray, listStations);

        // добавим переходы
        parserOther.parseConnection();
        TreeSet<Connections> connections = parserOther.getContainerStations().getConnections();
        JSONArray connectionsArray = parserOther.writeConnectionsInJSON(connections); // записываем переходы в JSON

        // добавим переходы в listStations
        setParameterHasConnection(connections, listStations);

        JSONObject stationObject = new JSONObject();
        JSONArray stationsArray = new JSONArray();

        for (Map.Entry<String, Station> entry : listStations.entrySet()) {
            // создаем объект новой станции и заполняем его
            JSONObject stationObj = new JSONObject();
            if (entry.getValue().getStationName() != null) {
                stationObj.put("name", entry.getValue().getStationName());
            }
            if (entry.getValue().getLineName() != null) {
                stationObj.put("line", entry.getValue().getLineName());
            }
            if (entry.getValue().getDateStation() != null) {
                stationObj.put("date", entry.getValue().getDateStation());
            }
            if (entry.getValue().getDepthStation() != null) {
                stationObj.put("depth", entry.getValue().getDepthStation());
            }
            stationObj.put("hasConnection", entry.getValue().isHasConnection());
            stationsArray.add(stationObj);
        }
        stationObject.put("stations", stationsArray); // кладем в объект ключ и значение

        // красивый вывод в консоль json объекта станций
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(stationObject));

        // записываем json объект в файл
        String path = "data/stations.json";
        createJSON.writeInJSONFile(stationObject, path);
        System.out.println("Создан файл: data/stations.json");
    }

    // добавление названий линий в listStations
    private static void setParameterLineName(
            List<Station> stations, JSONArray linesArray, Map<String, Station> listStations) {
        // берем из listStations ключ - название станции
        listStations.keySet().forEach(k -> {
            for (Station station : stations) { // ищем совпадение ключа с названием в списке станций
                if (station.getStationName().equals(k)) { // если находим совпадение, переходим к списку линий
                    linesArray.forEach(lineObject -> {
                        JSONObject lineJsonObject = (JSONObject) lineObject;
                        // если номер объекта линии равен номеру линии station
                        if (lineJsonObject.get("number").equals(station.getLineNumber())) {
                            // то меняем в listStations номер линии на соответствующее название
                            listStations.get(k).setLineName((String) lineJsonObject.get("name"));
                        }
                    });
                }
            }
        });
    }

    // добавление переходов в listStations
    private static void setParameterHasConnection(
            TreeSet<Connections> connections, Map<String, Station> listStations) {
        listStations.keySet().forEach(k -> {
            for (Connections connection : connections) {
                for (Station station : connection.getConnectionStations()) {
                    if (station.getStationName().equals(k)) {
                        listStations.get(k).setHasConnection(true);
                    }
                }
            }
        });
    }
}
