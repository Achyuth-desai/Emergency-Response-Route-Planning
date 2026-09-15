# Emergency Response Route Planning

A command-line Java application that computes the fastest route between two points in a road network — built with emergency dispatch in mind, where routes need to skip blocked roads and optimize for travel *time*, not distance.

It loads a road network (locations + road segments) from CSV files, validates the data, builds a graph, and runs Dijkstra's algorithm to answer a batch of "get from A to B" queries.

## Features

- **CSV-driven road network** — locations/nodes (`nodes.csv`), road segments (`roads_valid.csv`), and a batch of routing requests (`routing_queries.csv`) are all loaded from disk; no recompilation needed to test a new network.
- **Data validation before routing** — every node and route is checked (duplicate IDs, non-existent endpoints, self-loops, non-positive distance/speed, null checks) so bad data fails fast with a clear message instead of producing a wrong route.
- **Directed graph with automatic two-way expansion** — a road is stored once in the CSV; if it isn't marked one-way, the graph adds the reverse edge automatically.
- **Blocked-road awareness** — each route has an `active` flag. Inactive (e.g. closed/blocked) roads are skipped when searching for a path, without needing to delete them from the dataset.
- **Time-optimized shortest path** — uses Dijkstra's algorithm, weighting each road by `distance ÷ speed` (i.e. travel time), so the "shortest" route is the fastest one, not necessarily the shortest in km.
- **Batch query processing** — processes every query in `routing_queries.csv` in one run, and keeps going if one query fails (unknown node) or is unreachable, rather than stopping the whole batch.
- **Readable console output** — for each query, prints the road-by-road path, the sequence of locations, the roads taken, and the total distance and time.

## Approach

1. **Load** — `CsvData` reads `nodes.csv`, `roads_valid.csv`, and `routing_queries.csv` into plain Java lists using a small built-in CSV parser (handles quoted values, skips header rows).
2. **Validate** — `DataValidator` checks the raw nodes and routes for structural correctness (uniqueness, valid endpoints, positive distance/speed) before anything is built from them.
3. **Build the graph** — `Graph` stores nodes by ID and builds an adjacency list of Routes. Two-way roads get a mirrored reverse-direction edge added automatically.
4. **Plan the route** — for each query, `RoutePlanner` asks a `ShortestPathAlgorithm` (currently `DijkstraShortestPath`) for the lowest-cost path, where cost per edge comes from a pluggable `WeightProvider` (currently `TravelTimeWeightProvider`: `distanceKm / speedKmph`). Only `active` routes are considered.
5. **Report** — `RoutePlanner` reconstructs the path into an ordered list of nodes/routes and totals the distance and time; `RoutePrinter` formats it for the console.

## Design Decisions

- **Strategy pattern for the algorithm and the cost function** (`ShortestPathAlgorithm`, `WeightProvider` interfaces) — the routing algorithm (Dijkstra today) and how an edge's "cost" is computed (travel time today) are both swappable without changing `Graph` or `RoutePlanner`. Someone could add an A* implementation or a distance-only/road-type-aware weight provider later without touching the rest of the codebase.
- **Time as the default cost metric** — distance alone doesn't reflect real travel time on different road types/speeds, and for emergency response, minutes matter more than kilometers.
- **One-way vs. two-way handling** — Directed edges are identified at graph-build time. The route is added to both nodes/locations in their adjacency lists for bi-directional routes.
- **`active` flag instead of deleting roads** — models a road being temporarily closed as data, so a network can be re-routed around a blockage just by flipping a flag, no dataset surgery required.
- **Validation is a separate step from parsing and graph-building** — `CsvData` only parses, `DataValidator` only validates, `Graph` only assembles. Bad input produces a clear, specific error before Dijkstra ever runs, instead of a confusing downstream failure.
- **Immutable domain objects** — `Node` and `Route` have no setters, so once the network is loaded it can't be mutated accidentally mid-computation.
- **No external dependencies** — a small hand-rolled CSV parser is used instead of pulling in a library, so the whole project builds with nothing but a JDK and `javac`.
- **Per-query error isolation** — `App` wraps each query in its own try/catch so one malformed or unreachable query doesn't abort the rest of the batch.

## Project Structure

```
Emergency Response Route Planning/
├── README.md
├── .vscode/
│   └── settings.json                      # VS Code Java project config (source: src, output: bin)
└── src/com/emergencyroute/
    ├── App.java                           # Entry point: loads data, validates, builds graph, runs queries
    │
    ├── model/
    │   ├── Node.java                      # Immutable location (id, latitude, longitude)
    │   ├── Route.java                     # Immutable road segment (id, from, to, distance, type, speed, oneWay, active)
    │   └── Graph.java                     # Adjacency-list graph; auto-expands two-way roads into two directed edges
    │
    ├── algorithm/
    │   ├── ShortestPathAlgorithm.java     # Interface: findPath(graph, source, destination)
    │   ├── DijkstraShortestPath.java      # Dijkstra implementation using a priority queue
    │   ├── WeightProvider.java            # Interface: calculateWeight(route)
    │   ├── TravelTimeWeightProvider.java  # Weight = distanceKm / speedKmph (hours)
    │   └── PathResult.java                # Result dataset: reachable?, node path, route path, total distance, total time
    │
    ├── data/
    │   ├── CsvData.java                   # Loads/parses nodes, roads, and routing queries from CSV
    │   ├── SampleData.java                # Hardcoded network used for quick manual testing (see App.java)
    │   ├── nodes.csv                      # Locations (id, latitude, longitude)
    │   ├── roads.csv                      # Roads (id, from, to, distance, type, speed, one-way, active)
    │   ├── roads_valid.csv                # Corrected version of roads.csv (for testing validation) - this is what App.java actually loads
    │   └── routing_queries.csv            # Sample batch of source/destination queries
    │
    ├── validation/
    │   └── DataValidator.java             # Validates input nodes and routes before the graph is built
    │
    ├── service/
    │   └── RoutePlanner.java              # Orchestrates a single routing request end to end
    │
    └── io/
        └── RoutePrinter.java              # Formats and prints a PathResult to the console
```

## How to Compile

This is a plain Java project — no Maven or Gradle involved, just `javac`. Requires **JDK 16+** (the algorithm code uses a Java `record`). Run these commands from the project root (the folder containing `src/`).

**Linux / macOS:**
```bash
javac -d out $(find src -name "*.java")
```

**Windows (Command Prompt):**
```bat
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
```

Both commands compile all `.java` files under `src/` into an `out/` directory, mirroring the package structure.

> If you're using VS Code with the Java Extension Pack, the included `.vscode/settings.json` is already configured (source path `src`, output path `bin`) — you can just open the folder and use the built-in Run/Debug on `App.java` instead of the commands above.

## How to Run

The app reads its CSV input using paths relative to the project root (`src/com/emergencyroute/data/...`), so **run it from the project root directory**, not from inside `src/` or `out/`.

```bash
java -cp out com.emergencyroute.App
```

This will:
1. Load `nodes.csv`, `roads_valid.csv`, and `routing_queries.csv`.
2. Validate the network.
3. Compute the fastest route for every query in `routing_queries.csv`.
4. Print each result — path taken, sequence of locations, roads used, total distance (km), and total time (hours) — to the console.

To try your own network, edit those three CSV files (or point `App.java` at different file paths) and re-run.

## Requirements

- JDK 16 or later
- No external libraries or build tools required