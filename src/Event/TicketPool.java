package Event;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages the ticket pool shared between vendors and customers.
 */
public class TicketPool {
    private final Queue<String> tickets = new LinkedList<>();
    private final int maxCapacity, totalTicketsLimit;
    private int totalTicketsAdded = 0;
    private final String eventName;

    public TicketPool(int maxCapacity, int totalTicketsLimit, String eventName) {
        this.maxCapacity = maxCapacity;
        this.totalTicketsLimit = totalTicketsLimit;
        this.eventName = eventName;
    }

    /**
     * Adds tickets to the pool, ensuring it respects capacity and total ticket limits.
     */
    public synchronized void addTickets(String vendorId) {
        while (tickets.size() >= maxCapacity || totalTicketsAdded >= totalTicketsLimit) {
            waitSafely();
        }
        String ticketId = "TICKET-" + (totalTicketsAdded + 1);
        tickets.add(ticketId);
        totalTicketsAdded++;
        System.out.println("\u001B[34m" + vendorId + " added " + ticketId + " for Event: " + eventName + "\u001B[0m");
        notifyAll();
    }

    /**
     * Retrieves a ticket from the pool, ensuring tickets are available.
     */
    public synchronized void retrieveTicket(String customerId) {
        while (tickets.isEmpty()) {
            waitSafely();
        }
        String ticketId = tickets.poll();
        System.out.println(customerId + " retrieved " + ticketId + " from Event: " + eventName +
                ". Tickets Remaining: " + tickets.size());
        notifyAll();
    }

    // Helper to handle wait safely
    private void waitSafely() {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
