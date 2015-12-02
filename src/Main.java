import java.util.concurrent.CountDownLatch;

/**
 * Created by curtis on 12/1/15.
 */
public class Main {
    public static void main(String[] args) {
        EnergySource vendingCookies = EnergySource.create("Cookie"),
            vendingCandy = EnergySource.create("Candy Bar");

        CountDownLatch programStart = new CountDownLatch(3);
        TimeTracker timeTracker = new TimeTracker();

        Thread[] actors = {
            new WillyWonka(programStart, vendingCandy),
            new CookieMonster(programStart, vendingCookies),
            new FatAlbert(programStart, vendingCookies, vendingCandy)
        };

        for(Thread actor : actors) {
            actor.start();
        }

        try {
            programStart.await();
            timeTracker.startDay();

            for(Thread actor : actors) {
                actor.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        vendingCookies.stopEnergySource();
        vendingCandy.stopEnergySource();

    }
}
