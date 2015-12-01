import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 12/1/15.
 */
public class CookieMonster extends Thread {
    private CountDownLatch start;
    private EnergySource vendingCookies;

    public CookieMonster(CountDownLatch start, EnergySource vendingCookies) {
        this.start = start;
        this.vendingCookies = vendingCookies;
    }

    public void run() {
        final int COOKIE_INTERVAL = 500;

        start.countDown();

        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(TimeTracker.getCurrentTime() < (TimeHelp.DAY.ms() * 15)) {
            if (vendingCookies.useEnergy(1)) {
                System.out.println("Me love cookies");
            } else {
                System.out.println("Me hungry");
            }

            try {
                Thread.sleep(COOKIE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
