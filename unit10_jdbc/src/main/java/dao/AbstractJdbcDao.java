package dao;

import java.sql.SQLException;

public abstract class AbstractJdbcDao {

    protected void printAndThrowSQLException(SQLException e) {
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("Error Code: " + e.getErrorCode());
        System.out.println("Message: " + e.getMessage());
        throw new RuntimeException(e);
    }
}
