import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 12/1/15.
 */
public class FatAlbert extends Thread {
    private CountDownLatch start;
    private EnergySource vendingCookies, vendingCandy;

    public FatAlbert(CountDownLatch start, EnergySource vendingCookies, EnergySource vendingCandy) {
        this.start = start;
        this.vendingCookies = vendingCookies;
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
            int nRequests = random.nextInt(3) + 2;

            for(int i = 0; i < nRequests; i++) {
                boolean gotACookie = vendingCookies.useEnergy(1, this),
                        gotCandy = vendingCandy.useEnergy(1, this);

                if (gotACookie && gotCandy) {
                    System.out.println("Hey, hey hey!");
                } else if (gotACookie) {
                    System.out.println("At least I got a Cookie");
                } else if (gotCandy) {
                    System.out.println("At least I got a Candy Bar");
                } else {
                    System.out.println("No food for me today");
                }

                try {
                    Thread.sleep(random.nextInt(TimeHelp.DAY.ms() / nRequests) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
