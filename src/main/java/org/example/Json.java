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

/**
 * Utility class for reading input graph data from a JSON file and writing
 * MST comparison results back to a JSON file using the Gson library.
 */
public class Json {

    /**
     * Reads graph data from a JSON file.
     *
     * @param filePath the path to the JSON file containing graph data
     * @return a list of GraphData objects parsed from the JSON file
     */
    public static List<GraphData> readGraphsFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // Create Gson instance for JSON deserialization
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<GraphData>>>(){}.getType();

            // Deserialize JSON into a map and extract the list of graphs
            Map<String, List<GraphData>> data = gson.fromJson(reader, type);
            return data.get("graphs");
        } catch (Exception e) {

            // Handle exceptions by printing stack trace and returning empty list
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Writes MST comparison results to a JSON file.
     *
     * @param results  the list of OutputGraph results to write
     * @param filePath the path to the output JSON file
     */
    public static void writeResultsToJson(List<OutputGraph> results, String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {

            // Create Gson instance with pretty printing for JSON serialization
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> output = new HashMap<>();
            output.put("results", results);

            // Serialize the results map to JSON and write to file
            gson.toJson(output, writer);
        } catch (Exception e) {

            // Handle exceptions by printing stack trace
            e.printStackTrace();
        }
    }

}
