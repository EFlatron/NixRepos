package entity;

import java.util.Objects;

public class Problem {
    private int id;
    private int fromId;
    private int toId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return id == problem.id && fromId == problem.fromId && toId == problem.toId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromId, toId);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                '}';
    }
}
