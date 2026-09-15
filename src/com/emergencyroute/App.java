package com.emergencyroute;

import java.util.List;

import com.emergencyroute.algorithm.DijkstraShortestPath;
import com.emergencyroute.algorithm.PathResult;
import com.emergencyroute.algorithm.ShortestPathAlgorithm;
import com.emergencyroute.algorithm.TravelTimeWeightProvider;
import com.emergencyroute.algorithm.WeightProvider;
import com.emergencyroute.data.CsvData;
import com.emergencyroute.io.RoutePrinter;
import com.emergencyroute.model.Graph;
import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;
import com.emergencyroute.validation.DataValidator;
import com.emergencyroute.service.RoutePlanner;

/** Application entry point. */
public class App {
    public static void main(String[] args) throws java.io.IOException {

        // Load Data
        CsvData csvData = new CsvData("src/com/emergencyroute/data/nodes.csv", 
                                        "src/com/emergencyroute/data/roads_valid.csv",
                                        "src/com/emergencyroute/data/routing_queries.csv");
        List<Node> nodes = csvData.getNodes();
        List<Route> routes = csvData.getRoutes();
        List<List<String>> routingQueries = csvData.getRoutingQueries();
        
        // Hardcoded Sample Data (for testing purposes)
        // List<Node> nodes = SampleData.getNodes();
        // List<Route> routes = SampleData.getRoutes();

        // Validate the Data
        DataValidator validator = new DataValidator();

        validator.validateNodes(nodes);
        validator.validateRoutes(routes, nodes);

        // Build the Graph
        Graph graph = new Graph(nodes, routes);

        WeightProvider weightProvider = new TravelTimeWeightProvider();
        ShortestPathAlgorithm algorithm = new DijkstraShortestPath(weightProvider);

        RoutePlanner routePlanner = new RoutePlanner(graph, algorithm);

        for (List<String> query : routingQueries) {
            String sourceNodeId = query.get(1);
            String destinationNodeId = query.get(2);

            System.out.println("\nQuery " + query.get(0) + " | Source: " + sourceNodeId
                    + " | Destination: " + destinationNodeId);

            // Find the shortest path
            PathResult result;
            try {
                result = routePlanner.findShortestPath(sourceNodeId, destinationNodeId);
            } catch (IllegalArgumentException exception) {
                System.out.println("Query " + query.get(0) + " could not be processed: " + exception.getMessage());
                continue;
            }

            // Check if the destination is reachable
            if (!result.isReachable()) {
                System.out.println("Destination " + destinationNodeId + " is not reachable from source "
                        + sourceNodeId);
                continue;
            }

            // Output
            RoutePrinter routePrinter = new RoutePrinter();

            routePrinter.printPathResult(result);
        }
    }
}
