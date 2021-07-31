package services;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class AddingOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger("logs");

    public void addOperation(String name, String email, String password) {
        Configuration config = new Configuration().configure();

        try (SessionFactory sessionFactory = config.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Query query = session.createQuery("select u from User u where name = :name and email = :email and password = :password");
                query.setParameter("name", name);
                query.setParameter("email", email);
                query.setParameter("password", password);
                query.setMaxResults(1);
                User user = (User) query.getSingleResult();
                if (user == null) {
                    LOGGER.error("It has not been possible to log in. Email: " + email);
                    throw new RuntimeException("Wrong input");
                } else {
                    System.out.println("You are logged in");
                    LOGGER.info("System signed in. Email: " + email);
                }

                Scanner sc = new Scanner(System.in);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                Operation operation = new Operation();
                System.out.println("Choose an account id:");
                List<Account> accounts = user.getAccounts();
                accounts.stream()
                        .map(account -> "id:" + account.getId() + ", balance:" + account.getAmount())
                        .forEach(System.out::println);
                int index = sc.nextInt();
                LOGGER.info("id of account: " + index + ", size of list: " + accounts.size());
                Account account = accounts.get(index - 1);
                System.out.println("Choose the category of operation:\n" +
                                   "1 -> income\n" +
                                   "2 -> expense");
                String category = sc.next();
                switch (category) {
                    case "1" -> {
                        System.out.println("Input value of income:");
                        Long amount = sc.nextLong();
                        LOGGER.info("inputted value: " + amount);
                        System.out.println("Give a description of the income:");
                        String description = reader.readLine();
                        LOGGER.info("description: " + description);

                        Income income = new Income();
                        income.setDescription(description);
                        income.setCategories(Category.Categories.INCOME);
                        session.persist(income);

                        operation.setDifference(amount);
                        operation.setTime(Instant.now());
                        operation.setCategory(income);
                        operation.setAccount(account);
                        session.persist(operation);
                    }
                    case "2" -> {
                        System.out.println("Input value of expense:");
                        Long amount = sc.nextLong();
                        LOGGER.info("inputted value: " + amount);
                        System.out.println("Give a description of the expense:");
                        String description = reader.readLine();
                        LOGGER.info("description: " + description);

                        Expense expense = new Expense();
                        expense.setDescription(description);
                        expense.setCategories(Category.Categories.EXPENSE);
                        session.persist(expense);

                        operation.setDifference(amount);
                        operation.setTime(Instant.now());
                        operation.setCategory(expense);
                        operation.setAccount(account);
                        session.persist(operation);
                    }
                    default -> System.out.println("Wrong input");
                }
                session.getTransaction().commit();
                System.out.println("Operation has been added. Current account amount: " + account.getAmount());
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
}
