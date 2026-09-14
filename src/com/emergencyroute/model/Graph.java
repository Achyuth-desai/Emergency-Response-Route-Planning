package com.emergencyroute.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Future boundary for route-network access.
 *
 * <p>Its internal representation and graph-query operations will be defined
 * when routing functionality is implemented.</p>
 */
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
