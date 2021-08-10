package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static services.HelloThreadService.helloThread;
import static services.PrimeNumbersService.primeNumbers;

public class ConsoleController {

    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("""
                Choose operation:\s
                1 -> HelloThread
                2 -> PrimeNumbers
                0 -> Exit""");
        try {
            int input = Integer.parseInt(reader.readLine());

            switch (input) {
                case 1 -> helloThread();
                case 2 -> primeNumbers();
                case 0 -> System.exit(0);
                default -> System.out.println("Error input");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Wrong input");
        }
    }
}
