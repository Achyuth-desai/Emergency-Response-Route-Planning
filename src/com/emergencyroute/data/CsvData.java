package com.emergencyroute.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;

/** Loads the route network from nodes.csv and routes.csv files. */
public class CsvData {
    private final List<Node> nodes;
    private final List<Route> routes;

    public CsvData(Path nodesFile, Path routesFile) throws IOException {
        nodes = loadNodes(nodesFile);
        routes = loadRoutes(routesFile);
    }

    public CsvData(String nodesFile, String routesFile) throws IOException {
        this(Path.of(nodesFile), Path.of(routesFile));
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    private List<Node> loadNodes(Path file) throws IOException {
        List<Node> result = new ArrayList<>();
        readRows(file, row -> {
            if (row.size() != 3) {
                throw new IllegalArgumentException("Expected 3 columns in nodes.csv");
            }
            result.add(new Node(row.get(0), parseDouble(row.get(1), "latitude"),
                    parseDouble(row.get(2), "longitude")));
        });
        return result;
    }

    private List<Route> loadRoutes(Path file) throws IOException {
        List<Route> result = new ArrayList<>();
        readRows(file, row -> {
            if (row.size() != 8) {
                throw new IllegalArgumentException("Expected 8 columns in routes.csv");
            }
            result.add(new Route(row.get(0), row.get(1), row.get(2), parseDouble(row.get(3), "distanceKm"),
                    row.get(4), parseDouble(row.get(5), "speedKmph"), parseBoolean(row.get(6)),
                    parseBoolean(row.get(7))));
        });
        return result;
    }

    private void readRows(Path file, RowReader rowReader) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            int lineNumber = 0;
            boolean firstRow = true;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                List<String> row = parseCsvRow(line);
                if (firstRow && isHeader(row)) {
                    firstRow = false;
                    continue;
                }
                firstRow = false;
                try {
                    rowReader.read(row);
                } catch (RuntimeException exception) {
                    throw new IllegalArgumentException("Invalid CSV data in " + file + " at line " + lineNumber,
                            exception);
                }
            }
        }
    }

    private List<String> parseCsvRow(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean quoted = false;
        for (int index = 0; index < line.length(); index++) {
            char character = line.charAt(index);
            if (character == '"') {
                if (quoted && index + 1 < line.length() && line.charAt(index + 1) == '"') {
                    value.append('"');
                    index++;
                } else {
                    quoted = !quoted;
                }
            } else if (character == ',' && !quoted) {
                values.add(value.toString().trim());
                value.setLength(0);
            } else {
                value.append(character);
            }
        }
        if (quoted) {
            throw new IllegalArgumentException("Unclosed quoted CSV value");
        }
        values.add(value.toString().trim());
        return values;
    }

    private boolean isHeader(List<String> row) {
        return !row.isEmpty() && (row.get(0).equalsIgnoreCase("nodeId") || row.get(0).equalsIgnoreCase("roadId"));
    }

    private double parseDouble(String value, String field) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid " + field + ": " + value, exception);
        }
    }

    private boolean parseBoolean(String value) {
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Invalid boolean value: " + value);
        }
        return Boolean.parseBoolean(value);
    }

    @FunctionalInterface
    private interface RowReader {
        void read(List<String> row);
    }
}