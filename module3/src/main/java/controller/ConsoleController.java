package controller;

import services.AddingOperations;
import services.WriteToCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class ConsoleController {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name;
        String email;
        String password;
        try {
            System.out.println("Enter your user name");
            name = reader.readLine();
            System.out.println("Enter your email:");
            email = reader.readLine();
            System.out.println("Enter your password:");
            password = reader.readLine();
        } catch (IOException e) {
            System.out.println("Input error");
            throw new RuntimeException(e);
        }
        while (true) {
            System.out.println("Choose an action:\n" +
                               "1 -> add new operation\n" +
                               "2 -> export operation for a certain period\n" +
                               "0 -> exit");
            String input = scanner.next();
            switch (input) {
                case "1" -> {
                    AddingOperations adding = new AddingOperations();
                    adding.addOperation(name, email, password);
                }
                case "2" -> {
                    WriteToCSV writeToCSV = new WriteToCSV();
                    writeToCSV.exportInCSV(name, email, password);
                }
                case "0" -> System.exit(0);
                default -> System.out.println("Wrong input");
            }
        }
    }
}
