package Event;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages the ticket pool shared between vendors and customers.
 */
public class TicketPool {
    public static Queue<String> tickets;
    public static int maxCapacity, totalTicketsLimit;
    public static int totalTicketsAdded ;
    public static int totalTicketsRetrieved; // Track tickets retrieved
    public static String eventName;

    public TicketPool(int maxCapacity, int totalTicketsLimit, String eventName, int totalTicketsAdded, int totalTicketsRetrieved, Queue<String> tickets) {
        TicketPool.maxCapacity = maxCapacity;
        TicketPool.totalTicketsLimit = totalTicketsLimit;
        TicketPool.eventName = eventName;
        TicketPool.totalTicketsAdded = totalTicketsAdded;
        TicketPool.totalTicketsRetrieved = totalTicketsRetrieved;
        TicketPool.tickets = tickets;
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
        TicketPool.totalTicketsAdded++;
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
        TicketPool.totalTicketsRetrieved++;
        System.out.println(customerId + " retrieved " + ticketId + " from Event: " + eventName + ". Tickets Remaining: " + tickets.size());

        if (TicketPool.totalTicketsAdded >= totalTicketsLimit && TicketPool.totalTicketsRetrieved >= totalTicketsLimit) {
            System.out.println("\u001B[32m" + "\n\t***** All tickets processed. Exiting system... *****" + "\u001B[0m");
            System.exit(0);
        }

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
