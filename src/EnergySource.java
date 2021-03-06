/***
 * Excerpted from "Programming Concurrency on the JVM",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/vspcon for more book information.
***/

import akka.stm.Ref;
import akka.stm.Atomic;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnergySource {
  private String sourceName;

  private final long MAXLEVEL = 6;
  final Ref<Long> level = new Ref<Long>(MAXLEVEL);
  final Ref<Long> usageCount = new Ref<Long>(0L);
  final Ref<Boolean> keepRunning = new Ref<Boolean>(true);
  private static final ScheduledExecutorService replenishTimer =
    Executors.newScheduledThreadPool(10);


  private EnergySource() {}
  
  private void init() {   
    replenishTimer.schedule(new Runnable() {
      public void run() { 
        replenish();
        if (keepRunning.get()) replenishTimer.schedule(this, 3, TimeUnit.SECONDS);
      }
    }, 3, TimeUnit.SECONDS);
  }
  
  public static EnergySource create(String sourceName) {
    final EnergySource energySource = new EnergySource();
    energySource.sourceName = sourceName;
    energySource.init();
    return energySource;
  }

  public void stopEnergySource() {
    keepRunning.swap(false);
    replenishTimer.shutdown();
  }

  public long getUnitsAvailable() { return level.get(); }

  public long getUsageCount() { return usageCount.get(); }

    public boolean useEnergy(final long units, Thread actor) {
        return  new Atomic<Boolean>() {
            public Boolean atomically() {
                long currentLevel = level.get();

                String spaces, name;

                if (actor instanceof FatAlbert) {
                    spaces = "            ";
                    name = "Fat Albert";
                } else if (actor instanceof WillyWonka) {
                    spaces = "        ";
                    name = "Willy Wonka";
                } else {
                    spaces = "    ";
                    name = "Cookie Monster";
                }

                if(units > 0 && currentLevel >= units) {
                    level.swap(currentLevel - units);
                    usageCount.swap(usageCount.get() + 1);

                    System.out.printf("%s %s got a %s\n", spaces, name, sourceName);

                    return true;
                } else {
                    System.out.printf("%s %s couldn't get a %s\n", spaces, name, sourceName);
                    return false;
                }
            }
        }.execute();
    }

    private void replenish() {
      new Atomic() {
          public Object atomically() {
              long currentLevel = level.get();
              if (currentLevel < MAXLEVEL) level.swap(MAXLEVEL);
              return null;
          }
      }.execute();
    }
}
