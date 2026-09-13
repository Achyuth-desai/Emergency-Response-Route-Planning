package com.emergencyroute.model;

/** An immutable road connection between two node IDs. */
public class Route {
    private final String roadId;
    private final String fromNode;
    private final String toNode;
    private final double distanceKm;
    private final String highwayType;
    private final double speedKmph;
    private final boolean oneWay;
    private final boolean active;

    public Route(
            String roadId,
            String fromNode,
            String toNode,
            double distanceKm,
            String highwayType,
            double speedKmph,
            boolean oneWay,
            boolean active) {
        this.roadId = roadId;
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.distanceKm = distanceKm;
        this.highwayType = highwayType;
        this.speedKmph = speedKmph;
        this.oneWay = oneWay;
        this.active = active;
    }

    public String getRoadId() {
        return roadId;
    }

    public String getFromNode() {
        return fromNode;
    }

    public String getToNode() {
        return toNode;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String getHighwayType() {
        return highwayType;
    }

    public double getSpeedKmph() {
        return speedKmph;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public boolean isActive() {
        return active;
    }
}
