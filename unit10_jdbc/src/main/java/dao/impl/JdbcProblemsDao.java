package dao.impl;

import dao.ProblemsDao;
import entity.Problem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.ExceptionHelper.printAndThrowSQLException;

public class JdbcProblemsDao implements ProblemsDao {
    private final Connection connection;

    private static final String PROBLEMS = "SELECT * FROM problems";

    public JdbcProblemsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Problem> readALl() {
        List<Problem> problemList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(PROBLEMS);
            while (res.next()) {
                Problem problem = new Problem();
                problem.setId(res.getInt(1));
                problem.setFromId(res.getInt(2));
                problem.setToId(res.getInt(3));
                problemList.add(problem);
            }
        } catch (SQLException e) {
            printAndThrowSQLException(e);
        }
        return problemList;
    }
}
