package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectorsDao {
    Connection getConnection() throws SQLException;
}
