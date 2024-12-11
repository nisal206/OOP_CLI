package Event;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void run(){
        Scanner input = new Scanner(System.in);
        Configuration config = new Configuration(); // Setup configuration

        File file = new File("Configure.txt");
        if (file.exists()) {
            System.out.print("\nDo you want to load the previous configuration (Enter 'Yes' or 'No'): ");
            String answer = input.next().trim().toLowerCase();
            if ("yes".equals(answer)) {
                config.read();
            } else {
                config.setup();
            }
        } else {
            config.setup();
        }

        // Initialize the ticket pool
        // Initialize the ticket pool
        TicketPool pool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets(), config.getEventName(), 0, 0, new LinkedList<>() {
        });
        Customer.pool = pool;
        Vendor.pool = pool;
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        int ch = 0;
        while (true) {
            if (ch == 0){
                System.out.print("\nEnter 'S' to start ticket operations, 'E' to stop vendors/customers, 'R' to reset system or 'X' to exit: ");
            }
            String command = input.next().trim().toLowerCase();

            if ("s".equals(command)) {
                if (vendorThreads.isEmpty() && customerThreads.isEmpty()) {
                    // Create and start vendor threads
                    for (int i = 0; i < config.getVendorCount(); i++) {
                        Thread vendorThread = new Thread(new Vendor(config.getTicketReleaseRate(), "Vendor-" + (i + 1)));
                        vendorThreads.add(vendorThread);
                        vendorThread.start();
                    }

                    // Create and start customer threads
                    for (int i = 0; i < config.getCustomerCount(); i++) {
                        Thread customerThread = new Thread(new Customer(config.getCustomerRetrievalRate(), "Customer-" + (i + 1)));
                        customerThreads.add(customerThread);
                        customerThread.start();
                    }
                    ch = 1;
                    System.out.println("\u001B[32m" + "\n\t***** Ticket system started. *****\n" + "\u001B[0m");
                } else {
                    System.out.println("\u001B[31m" + "\nThe system is already running!" + "\u001B[0m");
                }
            } else if ("e".equals(command)) {
                // Stop only vendor and customer threads
                stopThreads(vendorThreads, "Vendor");
                stopThreads(customerThreads, "Customer");
                vendorThreads.clear();
                customerThreads.clear();
                System.out.println("\u001B[32m" + "\n\t***** Ticket system stopped. *****" + "\u001B[0m");
                ch = 0;
                TicketPool newpool = new TicketPool(TicketPool.maxCapacity, TicketPool.totalTicketsLimit, TicketPool.eventName, TicketPool.totalTicketsAdded, TicketPool.totalTicketsRetrieved, TicketPool.tickets);
                Customer.pool = newpool;
                Vendor.pool = newpool;

            } else if ("r".equals(command)) {
                // Exit the program
                System.out.println("\u001B[32m" + "\n\t***** Exiting the system... *****" + "\u001B[0m");
                stopThreads(vendorThreads, "Vendor");
                stopThreads(customerThreads, "Customer");
                vendorThreads.clear();
                customerThreads.clear();
                run();

            }else if ("x".equals(command)) {
                // Exit the program completely
                stopThreads(vendorThreads, "Vendor");
                stopThreads(customerThreads, "Customer");
                System.out.println("\u001B[32m" + "\n\t***** Exiting the system... *****" + "\u001B[0m");
                System.exit(0); // Exit the program

            } else {
                System.out.println("\u001B[31m" + "\nInvalid command. Enter 'S' to start ticket operations, 'E' to stop vendors/customers, 'R' to reset system or 'X' to exit: " + "\u001B[0m");
            }
        }
    }

    // Stop a specific list of threads
    private static void stopThreads(List<Thread> threads, String threadType) {
        for (Thread thread : threads) {
            thread.interrupt(); // Interrupt each thread
        }
    }

    public static void main(String[] args) {
        System.out.println("\u001B[35m" + "\n\t\t***** Real-Time Event Ticketing System *****" + "\u001B[0m");
        run();
    }
}
