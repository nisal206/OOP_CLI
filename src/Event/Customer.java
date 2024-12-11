package Event;

public class Customer implements Runnable {
    public static TicketPool pool;// Shared ticket pool where customers retrieve tickets
    private final int retrievalRate;// Time interval between retrieving tickets (in seconds)
    private final String customerId;// Identifier for this customer

    public Customer(int retrievalRate, String customerId) {
        this.retrievalRate = retrievalRate;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {// Keep running until the thread is stopped
            pool.removeTickets(customerId); // Retrieve a ticket from the pool
            try {
                Thread.sleep(retrievalRate*1000);// Wait for the specified time before retrieving another ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Stop the thread if interrupted
            }
        }
    }

}


