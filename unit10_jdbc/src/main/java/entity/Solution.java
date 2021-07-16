package entity;

import java.util.Objects;

public class Solution {
    private int problemId;
    private int cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return problemId == solution.problemId && cost == solution.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemId, cost);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "problemId=" + problemId +
                ", cost=" + cost +
                '}';
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
