package main;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FillDataBase {
    public static void main(String[] args) {
        Configuration config = new Configuration().configure();
        try (SessionFactory sessionFactory = config.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            FillDataBase fill = new FillDataBase();
            try {
                session.getTransaction().begin();
                fill.fill(session);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public void fill(Session session) {
        User user = new User();
        user.setName("root");
        user.setEmail("root@gmail.com");
        user.setPassword("root");
        session.persist(user);

        Account account = new Account();
        account.setUser(user);
        session.persist(account);

        Income income = new Income();
        income.setDescription("award");
        income.setCategories(Category.Categories.INCOME);
        session.persist(income);

        Operation operation = new Operation();
        operation.setDifference(100L);
        operation.setCategory(income);
        operation.setAccount(account);
        session.persist(operation);

        Expense expense = new Expense();
        expense.setDescription("rent payment");
        expense.setCategories(Category.Categories.EXPENSE);
        session.persist(expense);

        operation = new Operation();
        operation.setDifference(10L);
        operation.setCategory(expense);
        operation.setAccount(account);
        session.persist(operation);
        System.out.println("DataBase filled in");
    }
}
