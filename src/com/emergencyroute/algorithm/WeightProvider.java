package com.emergencyroute.algorithm;

import com.emergencyroute.model.Route;

/** Defines how a route's cost is calculated for a shortest-path algorithm. */
public interface WeightProvider {
    double calculateWeight(Route route);
}
