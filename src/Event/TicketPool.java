package Event;

import java.util.Queue;

//Manages the ticket pool shared between vendors and customers.

public class TicketPool {
    public static Queue<String> tickets;// Queue to store tickets
    public static int maxCapacity, totalTicketsLimit;// Maximum number of tickets the pool can hold and Total limit of tickets that can be added
    public static int totalTicketsAdded ;// Tracks the total number of tickets added to the pool
    public static int totalTicketsRetrieved; // Tracks the total number of tickets retrieved by customers
    public static String eventName;// Name of the event for which tickets are being managed

//    Initializes the TicketPool with the given parameters.
    public TicketPool(int maxCapacity, int totalTicketsLimit, String eventName, int totalTicketsAdded, int totalTicketsRetrieved, Queue<String> tickets) {
        TicketPool.maxCapacity = maxCapacity;
        TicketPool.totalTicketsLimit = totalTicketsLimit;
        TicketPool.eventName = eventName;
        TicketPool.totalTicketsAdded = totalTicketsAdded;
        TicketPool.totalTicketsRetrieved = totalTicketsRetrieved;
        TicketPool.tickets = tickets;
    }

    public synchronized void addTickets(String vendorId) {
        while (tickets.size() >= maxCapacity || TicketPool.totalTicketsAdded >= totalTicketsLimit) {// Wait if the pool is full or the ticket limit is reached
            try {
                wait(); // Wait until there is space to add more tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Restore interrupt status
            }
        }
        String ticketId = "TICKET-" + (TicketPool.totalTicketsAdded + 1);// Generate a ticket ID and add it to the pool
        tickets.add(ticketId);
        TicketPool.totalTicketsAdded++;
        System.out.println("\u001B[34m" + vendorId + " added " + ticketId + " for Event: " + eventName + "\u001B[0m");
        notifyAll(); // Notify other threads that tickets have been added
    }

    public synchronized void removeTickets(String customerId) {
        while (tickets.isEmpty()) { // Wait if there are no tickets to retrieve
            try {
                wait();// Wait until tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Restore interrupt status
            }
        }
        // Remove a ticket from the pool
        String ticketId = tickets.poll();
        TicketPool.totalTicketsRetrieved++;
        System.out.println(customerId + " retrieved " + ticketId + " from Event: " + eventName + ". Tickets Remaining: " + tickets.size());

        // Check if all tickets have been processed, and exit if so
        if (TicketPool.totalTicketsAdded >= totalTicketsLimit && TicketPool.totalTicketsRetrieved >= totalTicketsLimit) {
            System.out.println("\u001B[32m" + "\n\t***** All tickets processed. Exiting system... *****" + "\u001B[0m");
            System.exit(0);
        }
        notifyAll(); // Notify other threads that tickets have been removed
    }
}



