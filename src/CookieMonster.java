import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 12/1/15.
 */
public class CookieMonster extends Thread {
    private CountDownLatch start;
    private EnergySource vendingCookie;

    public CookieMonster(CountDownLatch start, EnergySource vendingCookie) {
        this.start = start;
        this.vendingCookie = vendingCookie;
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
