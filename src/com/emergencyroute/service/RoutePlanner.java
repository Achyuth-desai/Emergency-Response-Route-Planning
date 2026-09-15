package com.emergencyroute.service;

import com.emergencyroute.algorithm.PathResult;
import com.emergencyroute.algorithm.ShortestPathAlgorithm;
import com.emergencyroute.model.Graph;
import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;

import java.util.ArrayList;
import java.util.List;

public class RoutePlanner {
    private final Graph graph;
    private final ShortestPathAlgorithm algorithm;

    public RoutePlanner(Graph graph, ShortestPathAlgorithm algorithm) {
        this.graph = graph;
        this.algorithm = algorithm;
    }

    public PathResult findShortestPath(String sourceNodeId, String destinationNodeId) {
        // Validate the provided source and destination nodes
        validateNodeExists(sourceNodeId);
        validateNodeExists(destinationNodeId);

        // Check if source and destination IDs are the same
        if (sourceNodeId.equals(destinationNodeId)) {
            return new PathResult(
                    true,
                    List.<Node>of(graph.getNodeById(sourceNodeId)),
                    List.<Route>of(),
                    0.0,
                    0.0);
        }

        // Use the provided algorithm to find the shortest path from source to destination.
        List<Route> routeToDestination = algorithm.findPath(graph, sourceNodeId, destinationNodeId);

        // If the list is empty, it means that the destination is not reachable.
        if (routeToDestination.isEmpty()) {
            return new PathResult(false, List.of(), List.of(), 0.0, 0.0);
        }

        // Find the nodes along the path and calculate total distance and time from source to destination
        List<Node> nodesInPath = new ArrayList<>();
        double totalDistance = 0.0;
        double totalTime = 0.0;
        
        nodesInPath.add(graph.getNodeById(sourceNodeId));
        for (Route route : routeToDestination) {
            nodesInPath.add(graph.getNodeById(route.getToNode()));
            totalDistance += route.getDistanceKm();
            totalTime += route.getDistanceKm() / route.getSpeedKmph(); 
        }

        return new PathResult(true, nodesInPath, routeToDestination, totalDistance, totalTime);
    }

    // Node validator method. Checks if the node provided is present in the graph.
    private void validateNodeExists(String nodeId) {
        if(graph.getNodeById(nodeId) == null) {
            throw new IllegalArgumentException("Node with ID " + nodeId + " does not exist in the graph.");
        }
    }

}
