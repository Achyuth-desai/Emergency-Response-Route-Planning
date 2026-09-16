package com.emergencyroute.validation;

import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Validates node and route data before it is used to build a graph. */
public class DataValidator {
    // Validates the nodes
    public void validateNodes(List<Node> nodes) {
        requireList(nodes, "Nodes"); // Null check

        Set<String> nodeIds = new HashSet<>();
        for (Node node : nodes) {
            if (node == null) { // Node object should not be null
                throw new IllegalArgumentException("Nodes must not contain null values.");
            }
            requireText(node.getNodeId(), "Node ID");
            if (!nodeIds.add(node.getNodeId())) { // Node value should be unique. No duplicate entries allowed
                throw new IllegalArgumentException("Duplicate node ID: " + node.getNodeId());
            } //Legal latitude and longitude values
            if (!Double.isFinite(node.getLatitude()) || node.getLatitude() < -90 || node.getLatitude() > 90) {
                throw new IllegalArgumentException("Node latitude must be finite and between -90 and 90.");
            }
            if (!Double.isFinite(node.getLongitude()) || node.getLongitude() < -180 || node.getLongitude() > 180) {
                throw new IllegalArgumentException("Node longitude must be finite and between -180 and 180.");
            }
        }
    }

    // Validates the routes
    public void validateRoutes(List<Route> routes, List<Node> nodes) {
        requireList(routes, "Routes");
        requireList(nodes, "Nodes");

        Set<String> nodeIds = new HashSet<>();
        for (Node node : nodes) {
            if (node != null) {
                nodeIds.add(node.getNodeId());
            }
        }

        Set<String> roadIds = new HashSet<>();
        for (Route route : routes) {
            if (route == null) {// Route object should not be null
                throw new IllegalArgumentException("Routes must not contain null values.");
            }
            requireText(route.getRoadId(), "Road ID");
            requireText(route.getFromNode(), "Route source node ID");
            requireText(route.getToNode(), "Route destination node ID");
            if (!roadIds.add(route.getRoadId())) {// Route ID should be unique. No duplicate values allowed.
                throw new IllegalArgumentException("Duplicate road ID: " + route.getRoadId());
            } // Routes must connect valid nodes
            if (!nodeIds.contains(route.getFromNode()) || !nodeIds.contains(route.getToNode())) {
                throw new IllegalArgumentException("Route endpoints must reference existing nodes: " + route.getRoadId());
            } // Distance and speed must be finite and greater than zero 
            if (!Double.isFinite(route.getDistanceKm()) || route.getDistanceKm() <= 0) {
                throw new IllegalArgumentException("Route distance must be finite and greater than zero: " + route.getRoadId());
            }
            if (!Double.isFinite(route.getSpeedKmph()) || route.getSpeedKmph() <= 0) {
                throw new IllegalArgumentException("Route speed must be finite and greater than zero: " + route.getRoadId());
            }
            requireText(route.getHighwayType(), "Highway type");
        }
    }

    // Null check helper methods
    private void requireList(List<?> values, String name) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException(name + " must not be null or empty.");
        }
    }

    private void requireText(String value, String name) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " must not be null or blank.");
        }
    }
}