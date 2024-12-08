package Event;

public class Vendor implements Runnable {
    private final TicketPool pool;
    private final int releaseIntervalSeconds;
    private final String vendorId;

    public Vendor(TicketPool pool, int releaseIntervalSeconds, String vendorId) {
        this.pool = pool;
        this.releaseIntervalSeconds = releaseIntervalSeconds;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            pool.addTickets(vendorId); // Add a ticket to the pool
            sleep(releaseIntervalSeconds * 1000); // Wait for the release interval
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
