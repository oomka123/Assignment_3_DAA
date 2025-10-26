package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Json {

    public static List<GraphData> readGraphsFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<GraphData>>>(){}.getType();
            Map<String, List<GraphData>> data = gson.fromJson(reader, type);
            return data.get("graphs");
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeResultsToJson(List<OutputGraph> results, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> output = new HashMap<>();
            output.put("results", results);
            gson.toJson(output, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
