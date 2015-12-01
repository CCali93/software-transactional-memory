public enum TimeHelp {
    DAY(1000);

    private final int msTime;

    TimeHelp(int msTime) {
        this.msTime = msTime;
    }

    /**
     * Gets time in ms
     *
     * @return - the time in ms (as an int) that is represented in the firm
     */
    public int ms() {
        return msTime;
    }

}
