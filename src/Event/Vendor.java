package Event;

public class Vendor implements Runnable {
    public static TicketPool pool;// Shared ticket pool where tickets are added by vendors
    private final int releaseRate; // Time interval between adding tickets (in seconds)
    private final String vendorId;// Identifier for this vendor

    public Vendor(int releaseRate, String vendorId) {
        this.releaseRate = releaseRate;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) { // Keep running until the thread is stopped
            pool.addTickets(vendorId); // Add a ticket to the pool
            try {
                Thread.sleep(releaseRate*1000);// Wait for the specified time before adding another ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Stop the thread if interrupted
            }
        }
    }
}


