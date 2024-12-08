package Event;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("\u001B[35m"+"\n\t\t***** Real-Time Event Ticketing System *****"+"\u001B[0m");

        // Setup configuration
        Configuration config = new Configuration();
        config.setup();

        // Initialize the ticket pool
        TicketPool pool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets(), config.getEventName());

        // Prepare threads
        int vendorCount = 5;
        int customerCount = 10;
        List<Thread> threads = new ArrayList<>();

        // Create vendor threads
        for (int i = 0; i < vendorCount; i++) {
            threads.add(new Thread(new Vendor(pool, config.getTicketReleaseInterval(), "Vendor-" + (i + 1))));
        }

        // Create customer threads
        for (int i = 0; i < customerCount; i++) {
            threads.add(new Thread(new Customer(pool, config.getCustomerRetrievalInterval(), "Customer-" + (i + 1))));
        }

        System.out.println("\nEnter 'start' to begin ticket operations or 'stop' to end.");

        // Start or stop threads based on user input
        while (true) {
            System.out.print("\nEnter command: ");
            String command = new java.util.Scanner(System.in).nextLine().trim().toLowerCase();

            if ("start".equals(command)) {
                threads.forEach(Thread::start);
                System.out.println("\u001B[32m"+"\n\t***** Ticket system started. *****\n"+"\u001B[0m");
                break;
            } else if ("stop".equals(command)) {
                threads.forEach(Thread::interrupt);
                System.out.println("\u001B[32m"+"\n\t***** Stopping ticket system... *****"+"\u001B[0m");
                break;
            } else {
                System.out.println("\u001B[31m"+"\nInvalid command. Type 'start' or 'stop'."+"\u001B[0m");
            }
        }
    }
}
