import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;

public class ConsoleInput {
    public static void input() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Have 10 horses, pick your horse (1-10)");
        int horseNumber;
        String horse = "horse ";
        try {
            horseNumber = Integer.parseInt(reader.readLine());
            if (horseNumber > HorseRace.horseCount || horseNumber < 1) {
                throw new RuntimeException("Input error");
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Input error");
        }
        System.out.println("The race starts");
        Queue<String> horseQueue = HorseRace.race();

        horse += String.valueOf(horseNumber);
        if (horseQueue.element().equals(horse)) {
            System.out.println("You horse win race :)");
        } else {
            System.out.println("You horse lose race :(");
        }
        int place = 0;
        for (String s : horseQueue) {
            place++;
            if (s.equals(horse)) {
                System.out.println(horse + " is at position " + place + " in the queue.");
                break;
            }
        }
    }
}
