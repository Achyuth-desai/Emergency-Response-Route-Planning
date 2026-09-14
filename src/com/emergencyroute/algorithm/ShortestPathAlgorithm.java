package com.emergencyroute.algorithm;

import com.emergencyroute.model.Graph;
import com.emergencyroute.model.Route;

import java.util.List;

/** Contract for shortest-path algorithms. */
public interface ShortestPathAlgorithm {
    List<Route> findPath(Graph graph, String sourceNodeId, String destinationNodeId);
}
