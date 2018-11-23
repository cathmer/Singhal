package mutual_exclusion;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CriticalSection {


    public void executeCS(int processId) {
//        int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        int randomNum = 10;
        System.out.println("Process " + processId + " is executing Critical Section with delay of " + randomNum + " seconds!");
        try {
            TimeUnit.SECONDS.sleep(randomNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
