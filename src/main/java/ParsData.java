import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ParsData {

    // !!! КЛАСС СБОРА ГЛУБИН И ДАТ ИЗ JSON И CSV ФАЙЛОВ !!!

    Map<String, Station> listStations = new HashMap<>();
    String DATA_FILE = "";

    public Map<String, Station> getListStations() {
        return listStations;
    }

    // проверяем файлы и собираем соответствующие данные
    public Map<String, Station> fileReader(String path) throws ParseException, IOException {
        File doc = new File(path);
        if (doc.isFile()) { // если передали файл
            DATA_FILE = doc.getAbsolutePath();
            if (doc.getName().endsWith(".json")) { // получаем данные если json
                getDatesFromJson(doc);
            }
            if (doc.getName().endsWith(".csv")) { // получаем данные если csv
                getDatesFromCsv(doc);
            }
        } else {  // если передали не файл, а папку, рекурсивно обходим папку с файлами
            File[] files = doc.listFiles();
            for (File file : files) {
                fileReader(file.getAbsolutePath());
            }
        }
        return listStations;
    }

    // собираем данные из json файлов
    public void getDatesFromJson(File doc) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(doc);
        JSONArray jsonData = (JSONArray) parser.parse(reader);
        for (Object item : jsonData) {
            JSONObject stationJsonObject = (JSONObject) item;
            String stationName = (String) stationJsonObject.get("station_name"); // ключ

            // проверим содержание ключа (названия станции) в мапе
            if (!listStations.containsKey(stationName)) {
                // если не содержит, добавим название станции через конструктор
                listStations.put(stationName, new Station(stationName));
            }

            // проверяем начинается ли название файла с dates (если даты будут в json файле)
            if (doc.getName().startsWith("dates")) {
                String date = (String) stationJsonObject.get("dates"); // получаем дату
                listStations.get(stationName).setDateStation(date); // дописываем дату через конструктор

                // если название файла начинается с depths
            } else if (doc.getName().startsWith("depths")) {
                String depth = String.valueOf(stationJsonObject.get("depth")); // получаем глубину
                // дописываем глубину соответствующей станции (ключу)
                listStations.get(stationName).setDepthStation(depth);
            }
        }
    }

    // собираем данные из csv файлов
    private void getDatesFromCsv(File doc) throws FileNotFoundException {
        String filePath = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath)); // передаем путь к файлу в считыватель
        try {
            String splitBy = ","; // разделитель — запятая
            String line = ""; // символ кавычки - двойная кавычка
            while ((line = reader.readLine()) != null) { // читаем все строки
                // разбиваем объект на массив строк разделителем "," с ограничением найденных подстрок 2
                String[] lines = line.split(splitBy, 2);
                for (int i = 0; i < lines.length; i++) {
                    if (i % 2 == 0) {
                        String stationName = lines[i]; // ключ название станции

                        // проверим содержание ключа (названия станции) в мапе
                        if (!listStations.containsKey(stationName)) {
                            // если не содержит, добавим название станции через конструктор
                            listStations.put(stationName, new Station(stationName));
                        }

                        // проверяем начинается ли название файла с dates
                        if (doc.getName().startsWith("dates")) {
                            // дописываем дату через конструктор
                            listStations.get(stationName).setDateStation(lines[i + 1]);

                            // если название файла начинается с depths (если глубины будут в csv файле)
                        } else if (doc.getName().startsWith("depth")) {
                            listStations.get(stationName).setDepthStation(lines[i + 1]); // допишем глубину
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
