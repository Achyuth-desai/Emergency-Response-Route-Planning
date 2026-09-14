package com.emergencyroute;

import java.util.List;

import com.emergencyroute.algorithm.DijkstraShortestPath;
import com.emergencyroute.algorithm.PathResult;
import com.emergencyroute.algorithm.ShortestPathAlgorithm;
import com.emergencyroute.algorithm.TravelTimeWeightProvider;
import com.emergencyroute.algorithm.WeightProvider;
import com.emergencyroute.data.SampleData;
import com.emergencyroute.io.RoutePrinter;
import com.emergencyroute.model.Graph;
import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;
import com.emergencyroute.service.RoutePlanner;

/** Application entry point. */
public class App {
    public static void main(String[] args) {

        // Load Data
        List<Node> nodes = SampleData.getNodes();
        List<Route> routes = SampleData.getRoutes();

        // Build the Graph
        Graph graph = new Graph(nodes, routes);

        WeightProvider weightProvider = new TravelTimeWeightProvider();
        ShortestPathAlgorithm algorithm = new DijkstraShortestPath(weightProvider);

        RoutePlanner routePlanner = new RoutePlanner(graph, algorithm);

        // Example request
        String sourceNodeId = "N01";
        String destinationNodeId = "N04";

        // Find the shortest path
        PathResult result = routePlanner.findShortestPath(sourceNodeId, destinationNodeId);

        // Output
        RoutePrinter routePrinter = new RoutePrinter();
        routePrinter.printPathResult(result);
    }
}
