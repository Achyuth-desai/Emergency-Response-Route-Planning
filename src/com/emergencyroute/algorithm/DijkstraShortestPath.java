package com.emergencyroute.algorithm;

import com.emergencyroute.model.Graph;
import com.emergencyroute.model.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraShortestPath implements ShortestPathAlgorithm {
    // To store the cost of reaching each node, we can use a record to represent a
    // node and its associated cost.
    private record NodeCost(String nodeId, double cost) {
    };

    // WeightProvider is used to calculate the weight of each route based on its own attributes.
    private final WeightProvider weightProvider;

    public DijkstraShortestPath(WeightProvider weightProvider) {
        this.weightProvider = weightProvider;
    }

    @Override
    public List<Route> findPath(Graph graph, String sourceNodeId, String destinationNodeId) {
        Map<String, Double> costs = new HashMap<>();
        Map<String, Route> previousRoutes = new HashMap<>();

        PriorityQueue<NodeCost> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(NodeCost::cost));

        // Initialize the cost for each node to infinity, except for the source node which is set to 0
        for (String nodeId : graph.getNodes().keySet()) {
            costs.put(nodeId, Double.POSITIVE_INFINITY);
        }
        costs.put(sourceNodeId, 0.0);

        // Add the source node to the priority queue
        priorityQueue.add(new NodeCost(sourceNodeId, 0.0));

        // The main loop of Dijkstra's algorithm
        while (!priorityQueue.isEmpty()) {
            NodeCost currentNode = priorityQueue.poll();

            String currentNodeId = currentNode.nodeId();
            double currentCost = currentNode.cost();

            // If the current cost is greater than the recorded cost, we skip processing this node.
            if (currentCost > costs.get(currentNodeId)) {
                continue;
            }

            // If we reached the destination node, we can reconstruct the path and return it
            if (currentNodeId.equals(destinationNodeId)) {
                break;
            }

            // For each neighbor of the current node, we calculate the cost to reach it and update if it's lower than the previously recorded cost.
            for(Route route : graph.getRoutesFrom(currentNodeId)) {
                if(route.isActive()) {
                    String neighborNodeId = route.getToNode();
                    double routeCost = weightProvider.calculateWeight(route);

                    double newCost = currentCost + routeCost;

                    // If the new cost is lower than the previously recorded cost, we update the cost and add the neighbor to the priority queue.
                    if (newCost < costs.get(neighborNodeId)) {
                        costs.put(neighborNodeId, newCost);
                        previousRoutes.put(neighborNodeId, route);
                        priorityQueue.add(new NodeCost(neighborNodeId, newCost));
                    }
                }
            }
        }

        // Reconstruct the path from source to destination using the previousRoutes map
        return reconstructPath(previousRoutes, sourceNodeId, destinationNodeId);
    }

    private List<Route> reconstructPath(Map<String, Route> previousRoutes, String sourceNodeId, String destinationNodeId) {
            List<Route> path = new ArrayList<>();
            String currentNodeId = destinationNodeId;

            while (!currentNodeId.equals(sourceNodeId)) {
                Route route = previousRoutes.get(currentNodeId);
                if (route == null) {
                    // No path found
                    return Collections.emptyList();
                }
                path.add(route);
                currentNodeId = route.getFromNode();
            }

            // Reverse the path to get it from source to destination
            Collections.reverse(path);
            return path;
        }
}
