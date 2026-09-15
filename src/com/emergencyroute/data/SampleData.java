package com.emergencyroute.data;

import java.util.ArrayList;
import java.util.List;

import com.emergencyroute.model.Node;
import com.emergencyroute.model.Route;

public class SampleData {
    public static List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();

        nodes.add(new Node("N01", 12.97160, 77.59460));
        nodes.add(new Node("N02", 12.97250, 77.59620));
        nodes.add(new Node("N03", 12.97400, 77.59500));
        nodes.add(new Node("N04", 12.97550, 77.59700));
        nodes.add(new Node("N05", 12.97700, 77.59900));
        nodes.add(new Node("N06", 12.97850, 77.59650));
        nodes.add(new Node("N07", 12.98000, 77.59400));
        nodes.add(new Node("N08", 12.97600, 77.59200));
        nodes.add(new Node("N09", 12.97350, 77.59100));
        nodes.add(new Node("N10", 12.97050, 77.59250));
        nodes.add(new Node("N11", 12.97900, 77.60000));
        nodes.add(new Node("N12", 12.98300, 77.59800));

        return nodes;
    }

    public static List<Route> getRoutes() {
        List<Route> routes = new ArrayList<>();

        // Valid dataset created for testing purposes
        routes.add(new Route("R01", "N01", "N02", 0.20, "residential", 30, false, true));
        routes.add(new Route("R02", "N02", "N03", 0.25, "secondary", 40, false, true));
        routes.add(new Route("R03", "N03", "N04", 0.30, "primary", 50, true, true));
        routes.add(new Route("R04", "N04", "N05", 0.28, "primary", 50, false, true));
        routes.add(new Route("R05", "N05", "N06", 0.32, "secondary", 40, false, true));
        routes.add(new Route("R06", "N06", "N07", 0.30, "tertiary", 35, false, true));
        routes.add(new Route("R07", "N07", "N08", 0.35, "residential", 30, false, true));
        routes.add(new Route("R08", "N08", "N09", 0.28, "residential", 30, false, true));
        routes.add(new Route("R09", "N09", "N10", 0.30, "secondary", 40, false, true));
        routes.add(new Route("R10", "N10", "N01", 0.25, "primary", 50, false, true));
        routes.add(new Route("R11", "N02", "N08", 0.40, "residential", 20, false, true));
        routes.add(new Route("R12", "N03", "N06", 0.45, "primary", 50, false, true));
        routes.add(new Route("R13", "N05", "N11", 0.22, "secondary", 40, true, true));
        routes.add(new Route("R14", "N11", "N12", 0.40, "primary", 50, false, true));
        routes.add(new Route("R15", "N06", "N12", 0.55, "tertiary", 35, false, true));
        routes.add(new Route("R16", "N04", "N08", 0.60, "secondary", 40, false, true));
        routes.add(new Route("R17", "N01", "N03", 0.35, "residential", 15, false, true));
        routes.add(new Route("R18", "N07", "N11", 0.50, "primary", 50, false, true));
        routes.add(new Route("R19", "N02", "N05", 0.60, "residential", 20, false, true));
        routes.add(new Route("R20", "N03", "N02", 0.10, "residential", 20, false, true));
        routes.add(new Route("R21", "N09", "N12", 0.20, "residential", 20, false, true));
        routes.add(new Route("R22", "N10", "N11", 0.10, "secondary", 40, false, true));
        routes.add(new Route("R23", "N08", "N10", 0.30, "residential", 30, false, true));
        routes.add(new Route("R24", "N04", "N05", 0.28, "primary", 50, false, true));

        // routes.add(new Route("R01", "N01", "N02", 0.20, "residential", 30, false, true));
        // routes.add(new Route("R02", "N02", "N03", 0.25, "secondary", 40, false, true));
        // routes.add(new Route("R03", "N03", "N04", 0.30, "primary", 50, true, true));
        // routes.add(new Route("R04", "N04", "N05", 0.28, "primary", 50, false, true));
        // routes.add(new Route("R05", "N05", "N06", 0.32, "secondary", 40, false, true));
        // routes.add(new Route("R06", "N06", "N07", 0.30, "tertiary", 35, false, true));
        // routes.add(new Route("R07", "N07", "N08", 0.35, "residential", 30, false, true));
        // routes.add(new Route("R08", "N08", "N09", 0.28, "residential", 30, false, true));
        // routes.add(new Route("R09", "N09", "N10", 0.30, "secondary", 40, false, true));
        // routes.add(new Route("R10", "N10", "N01", 0.25, "primary", 50, false, true));
        // routes.add(new Route("R11", "N02", "N08", 0.40, "residential", 20, false, true));
        // routes.add(new Route("R12", "N03", "N06", 0.45, "primary", 50, false, true));
        // routes.add(new Route("R13", "N05", "N11", 0.22, "secondary", 40, true, true));
        // routes.add(new Route("R14", "N11", "N12", 0.40, "primary", 50, false, true));
        // routes.add(new Route("R15", "N06", "N12", 0.55, "tertiary", 35, false, true));
        // routes.add(new Route("R16", "N04", "N08", 0.60, "secondary", 40, false, true));
        // routes.add(new Route("R17", "N01", "N03", 0.35, "residential", 15, false, true));
        // routes.add(new Route("R18", "N07", "N11", 0.50, "primary", 50, false, true));
        // routes.add(new Route("R19", "N02", "N05", 0.60, "residential", 20, false, true));
        // routes.add(new Route("R20", "N03", "N03", 0.10, "residential", 20, false, true));
        // routes.add(new Route("R21", "N09", "N99", 0.20, "residential", 20, false, true));
        // routes.add(new Route("R22", "N10", "N11", -0.10, "secondary", 40, false, true));
        // routes.add(new Route("R23", "N08", "N10", 0.30, "residential", 0, false, true));
        // routes.add(new Route("R24", "N04", "N05", 0.28, "primary", 50, false, true));

        return routes;
    }
}
