package com.emergencyroute.algorithm;

import com.emergencyroute.model.Route;

/** Calculates route weight as travel time in hours. */
public class TravelTimeWeightProvider implements WeightProvider {
    @Override
    public double calculateWeight(Route route) {
        return route.getDistanceKm() / route.getSpeedKmph();
    }
}