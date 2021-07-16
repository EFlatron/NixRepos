package impl;

import entity.Route;

import java.util.List;
import java.util.stream.Collectors;

public class DijkstraAlg {
    private final List<Node> nodes;
    private final List<Route> routes;

    public DijkstraAlg(List<Node> nodes, List<Route> routes) {
        this.nodes = nodes;
        this.routes = routes;
    }

    public int minCost(Node first, Node last) {
        first.setWeight(0);
        findShortestPath(last);
        return last.getWeight();
    }

    private void findShortestPath(Node lastNode) {
        while (true) {
            Node thisNote = getMinWeightNode();
            if (thisNote.equals(lastNode)) return;
            List<Route> routesOfNode = getRoutesOfNode(thisNote);
            for (Route r : routesOfNode) {
                Node nextNode = getNextNode(r);
                if (thisNote.getWeight() + r.getCost() < nextNode.getWeight()) {
                    nextNode.setWeight(thisNote.getWeight() + r.getCost());
                    nextNode.setFromNode(thisNote);
                }
            }
            thisNote.setVisited(true);
        }
    }

    private Node getMinWeightNode() {
        return nodes
                .stream()
                .filter(c -> !c.isVisited())
                .min(Node::compareTo)
                .orElseThrow();
    }

    private List<Route> getRoutesOfNode(Node node) {
        return routes
                .stream()
                .filter(n -> n.getFromId() == node.getLocationId())
                .collect(Collectors.toList());
    }

    private Node getNextNode(Route route) {
        for (Node n : nodes) {
            if (n.getLocationId() == route.getToId()) {
                return n;
            }
        }
        throw new RuntimeException("Route is not complete.");
    }
}
