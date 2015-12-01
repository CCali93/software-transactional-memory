public class TimeTracker {
    private static long startTime; // System time to represent 8am
    private long firstStart; // Time that the first meeting of the day has started
    private long lastStart; // Time that the last meeting of the day has started


    /**
     * Gets the number of milliseconds since the start of the day
     *
     * @return time since the start of the day in milliseconds
     */
    public static synchronized long getCurrentTime() {
        return (System.currentTimeMillis() - startTime);
    }

    /**
     * Sets start time when simulation begins
     */
    public void startDay() {
        startTime = System.currentTimeMillis();
    }


}