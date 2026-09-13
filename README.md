# Emergency Response Route Planning

This project currently contains only the initial Java structure for an emergency-response shortest-route planner. It does not yet load route data, construct a graph, calculate routes, or provide command-line behavior.

## Package structure

```text
src/
└── com/emergencyroute/
    ├── App.java
    ├── model/
    │   ├── Node.java
    │   ├── Route.java
    │   └── Graph.java
    ├── algorithm/
    │   ├── ShortestPathAlgorithm.java
    │   ├── DijkstraShortestPath.java
    │   ├── WeightProvider.java
    │   └── PathResult.java
    └── service/
        └── RoutePlanner.java
```

## Responsibilities

- `App` is the minimal application entry point.
- `model` contains route-network domain types. `Node` and `Route` are immutable; `Graph` is the future boundary for graph access without exposing its eventual storage representation.
- `algorithm` contains shortest-path contracts. `DijkstraShortestPath` is intentionally unimplemented, and `WeightProvider` keeps route-cost policy independent from the routing algorithm.
- `service` contains the future application boundary that will coordinate routing requests.

## Current model fields

- `Node`: `nodeId`, `latitude`, `longitude`
- `Route`: `roadId`, `fromNode`, `toNode`, `distanceKm`, `highwayType`, `speedKmph`, `oneWay`, `active`

Node and road identifiers are direct `String` fields. New domain attributes belong directly on `Node` or `Route`; this project does not use ID wrapper types or generic attribute sets.

`oneWay` and `active` are retained as route data. Their legal-traversal enforcement will be designed with graph construction and Dijkstra implementation later.

`PathResult` is currently an empty placeholder for a future computed path and its relevant cost information.
