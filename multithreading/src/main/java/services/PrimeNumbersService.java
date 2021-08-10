package services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class PrimeNumbersService implements Callable<Integer> {
    List<Integer> list;

    public PrimeNumbersService(List<Integer> list) {
        this.list = list;
    }

    public Integer call() {
        int count = 0;
        for (Integer i : list) {
            boolean isPrime = i != 1 && i != 0;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                count++;
            }
        }
        System.out.println(Thread.currentThread().getName() + " -> prime numbers count = " + count);
        return count;
    }

    public static void primeNumbers() {
        int primeCount = 0;

        List<Integer> numbers = new ArrayList<>();
        System.out.println("We have numbers from 1 to 100");
        for (int i = 0; i < 101; i++) {
            numbers.add(i);
        }

        Callable<Integer> firstCall = new PrimeNumbersService(numbers.subList(0, numbers.size() / 2));
        FutureTask<Integer> firstFutureTask = new FutureTask<>(firstCall);
        new Thread(firstFutureTask).start();
        Callable<Integer> secondCall = new PrimeNumbersService(numbers.subList(numbers.size() / 2, numbers.size()));
        FutureTask<Integer> secondFutureTask = new FutureTask<>(secondCall);
        new Thread(secondFutureTask).start();

        try {
            primeCount += firstFutureTask.get();
            primeCount += secondFutureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Total prime numbers count = " + primeCount);
    }
}