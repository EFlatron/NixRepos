package dao.impl;

import dao.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionFactory implements ConnectionFactory {
    public Connection getConnection() throws SQLException {
        Properties props = loadProperties();
        String url = props.getProperty("url");
        String username = props.getProperty("user");
        String password = props.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return props;
    }
}
