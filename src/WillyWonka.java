import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 12/1/15.
 */
public class WillyWonka extends Thread {
    private CountDownLatch start;
    private EnergySource vendingCandy;

    public WillyWonka(CountDownLatch start, EnergySource vendingCandy) {
        this.start = start;
        this.vendingCandy = vendingCandy;
    }

    public void run() {
        Random random = new Random();

        start.countDown();

        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(TimeTracker.getCurrentTime() < (TimeHelp.DAY.ms() * 15)) {
            int candyBarTime = random.nextInt(TimeHelp.DAY.ms() + 1);

            try {
                Thread.sleep(candyBarTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (vendingCandy.getUnitsAvailable() > 0) {
                vendingCandy.useEnergy(1);
                System.out.println("The Candy Man Can");
            } else {
                System.out.println("Violet - you're turning violet");
            }

            try {
                Thread.sleep(TimeHelp.DAY.ms() - candyBarTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
