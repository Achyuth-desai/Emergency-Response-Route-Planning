package com.emergencyroute.io;

import com.emergencyroute.algorithm.PathResult;
import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;

/** Handles route result output. */
public class RoutePrinter {
    public void printPathResult(PathResult pathResult) {
        System.out.println();
        for (int index = 0; index < pathResult.getRoutes().size(); index++) {
            Node fromNode = pathResult.getNodes().get(index);
            Route route = pathResult.getRoutes().get(index);
            Node toNode = pathResult.getNodes().get(index + 1);

            System.out.print(fromNode.getNodeId() + " ---[" + route.getRoadId() + "]---> ");
            if (index == pathResult.getRoutes().size() - 1) {
                System.out.println(toNode.getNodeId());
            }
        }
        System.out.println();

        System.out.print("Sequence of locations : ");
        for (int index = 0; index < pathResult.getNodes().size(); index++) {
            if (index > 0) {
                System.out.print(" ---> ");
            }
            System.out.print(pathResult.getNodes().get(index).getNodeId());
        }
        System.out.println();

        System.out.print("Route Taken : ");
        for (int index = 0; index < pathResult.getRoutes().size(); index++) {
            if (index > 0) {
                System.out.print(" ---> ");
            }
            System.out.print(pathResult.getRoutes().get(index).getRoadId());
        }
        System.out.println();

        System.out.println("Total distance: " + pathResult.getTotalDistance() + " km");
        System.out.println("Total time: " + pathResult.getTotalTime() + " hours");
    }
}
