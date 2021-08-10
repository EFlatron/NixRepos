package services;

import java.util.ArrayList;
import java.util.List;

public class HelloThreadService extends Thread {

    public void run() {
        System.out.println("Hello " + currentThread().getName());
    }

    public static void helloThread() {
        List<HelloThreadService> threadList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            threadList.add(new HelloThreadService());
        }

        for (int i = 49; i > -1; i--) {
            threadList.get(i).start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}