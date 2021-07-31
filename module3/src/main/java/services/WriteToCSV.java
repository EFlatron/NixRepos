package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import static services.PropertyLoader.loadProperties;

public class WriteToCSV {

    private static final Logger LOGGER = LoggerFactory.getLogger("logs");

    public void exportInCSV(String name, String email, String password) {
        Scanner scanner = new Scanner(System.in);
        Properties props = loadProperties();
        String url = props.getProperty("url");

        try (Connection connection = DriverManager.getConnection(url, props)) {
            ResultSet resSet;
            int userId;
            try (PreparedStatement ps = connection.prepareStatement("select * from users where name = ? and email = ? and password = ?")) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                resSet = ps.executeQuery();
                if (resSet.next()) {
                    System.out.println("You are logged in");
                    LOGGER.info("System signed in. Email: " + email);
                } else {
                    LOGGER.error("It has not been possible to log in. Email: " + email);
                    throw new RuntimeException("Wrong input");
                }
                userId = resSet.getInt("id");
            }
            try (PreparedStatement ps = connection.prepareStatement("select * from accounts where user_id = ?")) {
                ps.setInt(1, userId);
                resSet = ps.executeQuery();
                System.out.println("List of accounts:");
                while (resSet.next()) {
                    System.out.println("id:" + resSet.getInt("id") + ", amount: " + resSet.getLong("amount"));
                }
                System.out.println("Choose the account id");
                int index = scanner.nextInt();
                System.out.println("Choose the type of sorting by time\n" +
                                   "1 -> ascending\n" +
                                   "2 -> descending");
                String choice = scanner.next();
                switch (choice) {
                    case "1" -> writer(connection, index, 1);
                    case "2" -> writer(connection, index, 2);
                    default -> System.out.println("Wrong input");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while try find need user and his accounts");
            throw new RuntimeException(e);
        }
    }

    private void writer(Connection connection, int index, int wayOfSorting) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String query = "select operations.id, operations.time, operations.difference, operations.account_id, categories.categories, categories.description from operations " +
                       "join categories on categories.id=operations.category_id " +
                       "where operations.account_id = ? and operations.time between ? and ?";
        System.out.println("Enter the time from which you want to see operations (format dd/mm/yyyy hh:mm) ");
        String from, to;
        Date dateFrom, dateTo;
        Instant iFrom, iTo;
        try {
            from = reader.readLine();
            LOGGER.info("From date: " + from);
            dateFrom = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(from);
            iFrom = dateFrom.toInstant();
            System.out.println("Input time to which you want to see operations (format dd/mm/yyyy hh:mm)");
            to = reader.readLine();
            LOGGER.info("To date: " + to);
            dateTo = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(to);
            iTo = dateTo.toInstant();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        if (wayOfSorting == 1) {
            query += " order by operations.time asc";
        } else
            query += " order by operations.time desc";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, index);
            ps.setTimestamp(2, Timestamp.from(iFrom));
            ps.setTimestamp(3, Timestamp.from(iTo));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("operations.csv", false))) {
                ResultSet resSet = ps.executeQuery();
                writer.write("operation id, time, amount, category, description\n");
                while (resSet.next()) {
                    writer.write(
                            resSet.getInt("id") + "," +
                            resSet.getTimestamp("time") + "," +
                            resSet.getLong("difference") + "," +
                            resSet.getString("categories") + "," +
                            resSet.getString("description") + "\n");
                }
            }
        } catch (SQLException | IOException e) {
            LOGGER.error("Error when try to write data in csv file");
            throw new RuntimeException(e);
        }
    }
}
