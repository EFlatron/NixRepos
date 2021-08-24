import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HorseRace {
    public static final int horseCount = 10;
    public static Queue<String> race() {
        ExecutorService executor = Executors.newFixedThreadPool(horseCount);
        List<Future<?>> futureList = new ArrayList<>();

        for (int i = 0; i < horseCount; i++) {
            futureList.add(executor.submit(new HorseThread(i + 1)));
        }
        for (Future<?> future : futureList) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        System.out.println(new HorseThread().getHorsesQueue());
        return new HorseThread().getHorsesQueue();
    }
}
