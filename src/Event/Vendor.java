package Event;

public class Vendor implements Runnable {
    private final TicketPool pool;
    private final int releaseRate;
    private final String vendorId;

    public Vendor(TicketPool pool, int releaseRate, String vendorId) {
        this.pool = pool;
        this.releaseRate = releaseRate;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {  // Check if the thread is interrupted
            pool.addTickets(vendorId);  // Add a ticket to the pool
            sleep(releaseRate * 1000);  // Wait for the release interval
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Properly handle interruption
        }
    }
}
