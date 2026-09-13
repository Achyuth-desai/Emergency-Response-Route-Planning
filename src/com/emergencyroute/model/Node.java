package com.emergencyroute.model;

/** An immutable location in the route network. */
public class Node {
    private final String nodeId;
    private final double latitude;
    private final double longitude;

    public Node(String nodeId, double latitude, double longitude) {
        this.nodeId = nodeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNodeId() {
        return nodeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
