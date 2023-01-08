import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

public class ParserWeb {

    public static JSONArray lines;
    public static ArrayList<LineMetro> lineList;
    public static ArrayList<Station> stationsList;
    public static HashMap<String, List<String>> stationMap;
    private Document document;

    // 1. получение HTML-кода страницы
    public Document getHTML(String url) {
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    // Задание 1. Парсинг страницы HTML и получение из неё - номер и имя линии московского метро
    public JSONArray getLine(Document HTML) {
        lines = new JSONArray(); // создаем массив
        lineList = new ArrayList<>();
        Elements elements = HTML.getElementsByClass("js-metro-line");
        // для каждого element создадим новый объект линии используя конструктор Line и засунем в ArrayList
        elements.forEach(line -> lineList.add(
                new LineMetro(line.attr("data-line"), line.text())));

        // вывод на печать линий
        for (LineMetro line : lineList) {
            System.out.println(line.getNumber() + " " + line.getName());
        }

        for (LineMetro line : lineList) {
            // создаем новый объект
            JSONObject line1 = new JSONObject();
            line1.put("number", line.getNumber());
            line1.put("name", line.getName());
            // полученный объект добавляем в массив
            lines.add(line1);
        }
        return lines;
    }

    // Задание 2. Парсинг страницы HTML и получение из неё - имя станции и номер линии московского метро
    public HashMap<String, List<String>> getStation(Document HTML) {
        stationsList = new ArrayList<>();
        Elements elements = HTML.getElementsByClass("js-metro-stations");
        // для каждого element создадим новый объект станции используя конструктор Station и засунем в ArrayList
        elements.forEach(station -> stationsList.add(
                new Station(station.text(), station.attr("data-line"))));

        // вывод на печать станций
        for (Station station : stationsList) {
            System.out.println("Линия " + station.getLineNumber() + ". " + "Станции: " + station.getStationName());
        }

        // создаем массив станций
        stationMap = stationsList.stream()
                .collect(Collectors.groupingBy(  // группируем станции по линиям
                        Station::getLineNumber,
                        LinkedHashMap::new,
                        Collectors.mapping(     // в группу (объект List) выделим названия станций
                                Station::getStationName,
                                Collectors.toList())));
        return stationMap;
    }

    public JSONArray getLines() {
        return lines;
    }
}

//    Класс парсинга веб-страницы.

//    В нём реализуйте каждую операцию в отдельных методах:
//    1 получение HTML-кода страницы «Список станций Московского метрополитена» с помощью библиотеки jsoup;
//    2 парсинг полученной страницы и получение из неё следующих данных
//    (создайте для каждого типа данных отдельные классы):
//    - линии московского метро (имя и номер линии, цвет не нужен);
//    - станции московского метро (имя станции и номер линии).

//    Проверьте работу данного класса: напишите код, который будет его использовать и выводить полученные данные.