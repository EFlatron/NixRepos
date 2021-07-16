package util.shortestPath;

import dao.LocationsDao;
import dao.ProblemsDao;
import dao.RoutesDao;
import dao.SolutionsDao;
import entity.Location;
import entity.Problem;
import entity.Route;
import entity.Solution;
import util.ShortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShortestPathImpl implements ShortestPath {
    private final LocationsDao locationsDao;
    private final RoutesDao routesDao;
    private final ProblemsDao problemsDao;
    private final SolutionsDao solutionsDao;
    private final List<Location> locations;
    private final List<Route> routes;
    private final List<Problem> problems;

    public ShortestPathImpl(LocationsDao locationsDao, RoutesDao routesDao, ProblemsDao problemsDao, SolutionsDao solutionsDao) {
        this.locationsDao = locationsDao;
        this.routesDao = routesDao;
        this.problemsDao = problemsDao;
        this.solutionsDao = solutionsDao;
        locations = readAllLocations();
        routes = readAllRoutes();
        problems = readAllProblems();
    }

    private int calculateTheCost(Problem p) {
        List<Node> nodes = createNodes();
        DijkstraAlg dijkstraAlg = new DijkstraAlg(nodes, routes);
        Node start = getNodeByLocationId(nodes, p.getFromId());
        Node end = getNodeByLocationId(nodes, p.getToId());
        return dijkstraAlg.minCost(start, end);
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

    private List<Location> readAllLocations() {
        return locationsDao.readALl();
    }

    private List<Route> readAllRoutes() {
        return routesDao.readALl();
    }

    private List<Problem> readAllProblems() {
        return problemsDao.readALl();
    }

    private void createSolutions(List<Solution> solutions) {
        solutionsDao.createSolutions(solutions);
    }
}
