package com.emergencyroute.algorithm;

import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;

import java.util.List;

/**
 * Future result of a shortest-path calculation, including path and cost data.
 */
public class PathResult {
	private final boolean reachable;
	private final List<Node> nodes;
	private final List<Route> routes;
	private final double totalDistance;
	private final double totalTime;

	public PathResult(
			boolean reachable,
			List<Node> nodes,
			List<Route> routes,
			double totalDistance,
			double totalTime) {
		this.reachable = reachable;
		this.nodes = nodes;
		this.routes = routes;
		this.totalDistance = totalDistance;
		this.totalTime = totalTime;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public boolean isReachable() {
		return reachable;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public double getTotalTime() {
		return totalTime;
	}
}
