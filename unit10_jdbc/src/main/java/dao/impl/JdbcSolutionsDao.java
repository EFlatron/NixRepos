package dao.impl;

import dao.SolutionsDao;
import entity.Solution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static util.ExceptionHelper.printAndThrowSQLException;

public class JdbcSolutionsDao implements SolutionsDao {
    private final Connection connection;

    private static final String SOLUTION = "INSERT INTO solutions (problem_id, cost) VALUES (?, ?)";

    public JdbcSolutionsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createSolutions(List<Solution> solutions) {
        try (PreparedStatement ps = connection.prepareStatement(SOLUTION)) {
            for (Solution solution : solutions) {
                ps.setInt(1, solution.getProblemId());
                ps.setInt(2, solution.getCost());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            printAndThrowSQLException(e);
        }
    }

}
