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
        start.countDown();

        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return;
    }
}
