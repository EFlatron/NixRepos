package dao.impl;

import dao.AbstractJdbcDao;
import dao.RoutesDao;
import entity.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoutesDao extends AbstractJdbcDao implements RoutesDao {
    private final Connection connection;

    private static final String ROUTES = "SELECT * FROM routes";

    public JdbcRoutesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Route> readALl() {
        List<Route> routeList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(ROUTES);
            while (res.next()) {
                Route route = new Route();
                route.setId(res.getInt(1));
                route.setFromId(res.getInt(2));
                route.setToId(res.getInt(3));
                route.setCost(res.getInt(4));
                routeList.add(route);
            }
        } catch (SQLException e) {
            printAndThrowSQLException(e);
        }
        return routeList;
    }
}
