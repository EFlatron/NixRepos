
import dao.ConnectionFactory;
import dao.impl.JdbcConnectionFactory;
import dao.LocationsDao;
import dao.ProblemsDao;
import dao.RoutesDao;
import dao.SolutionsDao;
import dao.impl.JdbcLocationsDao;
import dao.impl.JdbcProblemsDao;
import dao.impl.JdbcRoutesDao;
import dao.impl.JdbcSolutionsDao;
import impl.ShortestPathFinder;
import impl.ShortestPathFinderImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory connector = new JdbcConnectionFactory();
        try (Connection connection = connector.getConnection()) {
            System.out.println("Start");
            LocationsDao locationsDao = new JdbcLocationsDao(connection);
            RoutesDao routesDao = new JdbcRoutesDao(connection);
            ProblemsDao problemsDao = new JdbcProblemsDao(connection);
            SolutionsDao solutionsDao = new JdbcSolutionsDao(connection);
            ShortestPathFinder pathService = new ShortestPathFinderImpl(locationsDao, routesDao, problemsDao, solutionsDao);
            pathService.calculateTheProblems();
            System.out.println("Complete");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
