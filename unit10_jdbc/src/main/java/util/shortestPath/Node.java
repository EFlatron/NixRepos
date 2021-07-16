package util.shortestPath;

import entity.Location;

import java.util.Objects;

public class Node implements Comparable<Node> {
    private Location location;
    private int weight = 200_000;
    private Node fromNode;
    private boolean isVisited;

    public Node(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getLocationId() {
        return location.getId();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public void setFromNode(Node fromNode) {
        this.fromNode = fromNode;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return location.equals(node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(weight, node.getWeight());
    }

    @Override
    public String toString() {
        return "Node{" +
                "location=" + location +
                ", weight=" + weight +
                ", fromNode=" + fromNode +
                ", isVisited=" + isVisited +
                '}';
    }
}
