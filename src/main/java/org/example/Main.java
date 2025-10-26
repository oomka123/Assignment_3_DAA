package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.Json.readGraphsFromJson;
import static org.example.Json.writeResultsToJson;
import static org.example.MSTComparison.compareMSTs;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select graph difficulty level:");
        System.out.println("1 - Easy");
        System.out.println("2 - Medium");
        System.out.println("3 - Hard");
        System.out.print("Enter your choice (1/2/3): ");

        String choice = scanner.nextLine();
        String inputFile;

        switch (choice) {
            case "1":
                inputFile = "easy_input.json";
                break;
            case "2":
                inputFile = "medium_input.json";
                break;
            case "3":
                inputFile = "hard_input.json";
                break;
            default:
                System.out.println("Invalid choice. Using default input.json file.");
                inputFile = "input.json";
        }

        List<GraphData> graphs = readGraphsFromJson(inputFile);
        List<OutputGraph> results = new ArrayList<>();

        for (GraphData graph : graphs) {
            results.add(compareMSTs(graph));
        }

        writeResultsToJson(results, "output.json");
        System.out.println("Comparison completed. Results saved to output.json");
    }
}