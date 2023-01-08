import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class CreateJSON {

    // !!! КЛАСС СОЗДАНИЯ ФАЙЛА METRO !!!

    private static MetroMap metroMap;

    public void print() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        metroMap = new MetroMap(ParserWeb.lineList, ParserWeb.stationMap);
        try (FileWriter file = new FileWriter("data/map.json")) {
            file.write(gson.toJson(metroMap));
        }
    }

    public void writeInJSONFile(JSONObject object, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter file = new FileWriter(path)) {
            file.write(gson.toJson(object));
        }
    }
}
