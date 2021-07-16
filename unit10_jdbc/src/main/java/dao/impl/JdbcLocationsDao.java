package dao.impl;

import dao.LocationsDao;
import entity.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.ExceptionHelper.printAndThrowSQLException;

public class JdbcLocationsDao implements LocationsDao {
    private static final String LOCATION = "SELECT * FROM locations";

    private final Connection connection;

    public JdbcLocationsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Location> readALl() {
        List<Location> locationList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(LOCATION);
            while (res.next()) {
                Location location = new Location();
                location.setId(res.getInt(1));
                location.setName(res.getString(2));
                locationList.add(location);
            }
        } catch (SQLException e) {
            printAndThrowSQLException(e);
        }
        return locationList;
    }
}
