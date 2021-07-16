package impl;

import dao.LocationsDao;
import dao.ProblemsDao;
import dao.RoutesDao;
import dao.SolutionsDao;
import entity.Location;
import entity.Problem;
import entity.Route;
import entity.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShortestPathFinderImpl implements ShortestPathFinder {
    private final SolutionsDao solutionsDao;
    private final List<Location> locations;
    private final List<Route> routes;
    private final List<Problem> problems;

    public ShortestPathFinderImpl(LocationsDao locationsDao, RoutesDao routesDao, ProblemsDao problemsDao, SolutionsDao solutionsDao) {
        this.solutionsDao = solutionsDao;
        locations = locationsDao.readALl();
        routes = routesDao.readALl();
        problems = problemsDao.readALl();
    }

    public void calculateTheProblems() {
        List<Solution> solutions = new ArrayList<>();
        for (Problem p : problems) {
            int cost = calculateTheCost(p);
            Solution solution = new Solution();
            solution.setProblemId(p.getId());
            solution.setCost(cost);
            solutions.add(solution);
        }
        createSolutions(solutions);
    }

    private int calculateTheCost(Problem p) {
        List<Node> nodes = createNodes();
        DijkstraAlg dijkstraAlg = new DijkstraAlg(nodes, routes);
        Node start = getNodeByLocationId(nodes, p.getFromId());
        Node end = getNodeByLocationId(nodes, p.getToId());
        return dijkstraAlg.minCost(start, end);
    }

    private List<Node> createNodes() {
        return locations
                .stream()
                .map(Node::new)
                .collect(Collectors.toList());
    }

    private Node getNodeByLocationId(List<Node> nodeList, int locationId) {
        for (Node n : nodeList) {
            if (n.getLocationId() == locationId) {
                return n;
            }
        }
        throw new RuntimeException();
    }

    private void createSolutions(List<Solution> solutions) {
        solutionsDao.createSolutions(solutions);
    }
}
