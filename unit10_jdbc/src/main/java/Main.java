
import dao.ConnectorsDao;
import dao.impl.JdbcConnectorsDao;
import dao.LocationsDao;
import dao.ProblemsDao;
import dao.RoutesDao;
import dao.SolutionsDao;
import dao.impl.JdbcLocationsDao;
import dao.impl.JdbcProblemsDao;
import dao.impl.JdbcRoutesDao;
import dao.impl.JdbcSolutionsDao;
import util.ShortestPath;
import util.shortestPath.ShortestPathImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConnectorsDao connector = new JdbcConnectorsDao();
        try (Connection connection = connector.getConnection()) {
            LocationsDao locationsDao = new JdbcLocationsDao(connection);
            RoutesDao routesDao = new JdbcRoutesDao(connection);
            ProblemsDao problemsDao = new JdbcProblemsDao(connection);
            SolutionsDao solutionsDao = new JdbcSolutionsDao(connection);
            ShortestPath pathService = new ShortestPathImpl(locationsDao, routesDao, problemsDao, solutionsDao);
            pathService.calculateTheProblems();
            System.out.println("Complete");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
