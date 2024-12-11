package Event;

public class Customer implements Runnable {
    public static TicketPool pool;
    private final int retrievalRate;
    private final String customerId;

    public Customer(int retrievalRate, String customerId) {
        this.retrievalRate = retrievalRate;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {  // Check if the thread is interrupted
            pool.retrieveTicket(customerId);  // Retrieve a ticket from the pool
            sleep(retrievalRate * 1000);  // Wait for the retrieval interval
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
