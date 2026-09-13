package com.emergencyroute.algorithm;

import com.emergencyroute.model.Graph;

/** Contract for shortest-path algorithms. */
public interface ShortestPathAlgorithm {
    PathResult findPath(Graph graph, String sourceNodeId, String destinationNodeId);
}
