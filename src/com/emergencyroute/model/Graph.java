package com.emergencyroute.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {
	private final Map<String, Node> nodes = new HashMap<>();
	private final Map<String, List<Route>> adjacencyList = new HashMap<>();

    public Graph(List<Node> nodes, List<Route> routes) {

        // Store nodes by their ID
        for (Node node : nodes) {
            this.nodes.put(node.getNodeId(), node);
        }

        // Build adjacency list
        for (Route route : routes) {

            // Original direction
            adjacencyList
                .computeIfAbsent(route.getFromNode(), key -> new ArrayList<>())
                .add(route);

            // Reverse direction for two-way routes
            if (!route.isOneWay()) {
                // Switch FromNode and ToNode to reverse the direction keeping rest of the attributes same
                Route reverseRoute = new Route(
                    route.getRoadId(),
                    route.getToNode(),
                    route.getFromNode(),
                    route.getDistanceKm(),
                    route.getHighwayType(),
                    route.getSpeedKmph(),
                    route.isOneWay(),
                    route.isActive()
                );

                // Add the route if no route is present in the adj list. If present, append.
                adjacencyList
                    .computeIfAbsent(route.getToNode(), key -> new ArrayList<>())
                    .add(reverseRoute);
            }
        }
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public Map<String, List<Route>> getAdjacencyList() {
        return adjacencyList;
    }

    public List<Route> getRoutesFrom(String node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Node getNodeById(String nodeId) {
        return nodes.get(nodeId);
    }
}
