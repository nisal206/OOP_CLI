package Event;

public class Customer implements Runnable {
    private final TicketPool pool;
    private final int retrievalIntervalSeconds;
    private final String customerId;

    public Customer(TicketPool pool, int retrievalIntervalSeconds, String customerId) {
        this.pool = pool;
        this.retrievalIntervalSeconds = retrievalIntervalSeconds;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            pool.retrieveTicket(customerId); // Retrieve a ticket from the pool
            sleep(retrievalIntervalSeconds * 1000); // Wait for the retrieval interval
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
