package entity;

import java.util.Objects;

public class Route {
    private int id;
    private int fromId;
    private int toId;
    private int cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int from_id) {
        this.fromId = from_id;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int to_id) {
        this.toId = to_id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && fromId == route.fromId && toId == route.toId && cost == route.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromId, toId, cost);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", cost=" + cost +
                '}';
    }
}
