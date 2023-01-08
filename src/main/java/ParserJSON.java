import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class ParserJSON {

    // парсинг с применением Json-simple
    public void parseSimple(String jsonFile) throws ParseException, IOException {
        // читаем JSON-файл в строку
        String toString = Files.readString(Paths.get(jsonFile));
        // Считываем JSON
        Object obj = new JSONParser().parse(toString);
        // Создаем объект (массив) JSONArray
        JSONArray jo = (JSONArray) obj;
        // Выводим на печать содержимое массива (список объектов)
        System.out.println(jo.toJSONString());
    }

    // парсинг с применением библиотеки GSON
    public void parseGSON(String jsonFile) {
        Gson gson = new Gson();
        // красивый вывод данных
//      Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // создаем новый тип
        Type listType = new TypeToken<List<NewStation>>() {
        }.getType();
        try {
            // читаем JSON-файл в строку
            String toString = Files.readString(Paths.get(jsonFile));
            // конвертируем JSON в Java Object
            List<NewStation> list = gson.fromJson(toString, listType);
            // Выводим на печать содержимое полученного массива (список объектов)
            System.out.println(gson.toJson(list));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // парсинг с применением библиотеки JAСKSON
    public void parseJAСKSON(String dataFile) throws Exception {
        // ObjectMapper отвечает за построение дерева из узлов JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // читаем JSON-файл в строку:
            String jsonFile = Files.readString(Paths.get(dataFile));
            // парсим JSON-данные в объект класса JsonNode
            JsonNode jsonData = objectMapper.readTree(jsonFile);
            // Выводим на печать содержимое массива (список объектов)
            System.out.println(jsonData);

          // записываем в отдельный файл полученные объекты станций
//        ObjectMapper mapperStations = new ObjectMapper();
//        File outputStations = new File("data/stations.json");
//        mapperStations.writeValue(outputStations, stations);
//        System.out.println("Список станций: " + stations);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

/*    Класс парсинга файлов формата JSON.

      Cоздайте класс(ы) для хранения данных из этих файлов.

      Напишите код, который будет принимать JSON-данные и выдавать список соответствующих им объектов.

      Проверьте работу данного класса:
      - передайте ему на вход данные любого из JSON-файлов, найденных на предыдущем шаге,
      и убедитесь, что он выводит данные корректно.
*/