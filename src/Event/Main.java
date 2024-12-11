package Event;

import java.util.*;
import java.io.File;

public class Main {

    public static void run(){
        Scanner input = new Scanner(System.in);
        Configuration config = new Configuration(); // Configuration setup object

        File file = new File("Configure.txt"); // Check if the configuration file exists to determine the flow
        if (file.exists()) {
            while (true){
                System.out.print("\nDo you want to load the previous configuration (Enter 'Yes' or 'No'): ");
                String answer = input.next().trim().toLowerCase();
                if ("yes".equals(answer)) {
                    config.read();// Load configuration from file
                    break;
                } else if ("no".equals(answer)) {
                    config.setup();// Prompt user to set up a new configuration
                    break;
                }else{
                    System.out.println("\u001B[31m"+"\n\tInvalid command"+"\u001B[0m");
                }
            }
        } else {
            config.setup(); // If the configuration file does not exist, prompt setup
        }

        // Initialize the ticket pool with configuration values
        TicketPool pool = new TicketPool(Configuration.maxTicketCapacity, Configuration.totalTickets, Configuration.eventName, 0, 0, new LinkedList<>() {
        });
        Customer.pool = pool;// Share the ticket pool with Customer threads
        Vendor.pool = pool;// Share the ticket pool with Vendor threads
        List<Thread> vendorThreads = new ArrayList<>();// Vendor thread list
        List<Thread> customerThreads = new ArrayList<>();// Customer thread list

        int ch = 0;// Command state indicator
        while (true) {
            if (ch == 0) {
                System.out.print("\nEnter 'S' to start ticket operations, 'E' to stop vendors/customers, 'R' to reset system or 'X' to exit: ");
            }
            String command = input.next().trim().toLowerCase();

            if ("s".equals(command)) {
                if (vendorThreads.isEmpty() && customerThreads.isEmpty()) {
                    // Create and start vendor threads
                    System.out.println("\u001B[32m" + "\n\t***** Ticket system started. *****\n" + "\u001B[0m");
                    for (int i = 0; i < Configuration.vendorCount; i++) {
                        Thread vendorThread = new Thread(new Vendor(Configuration.ticketReleaseRate, "Vendor-" + (i + 1)));
                        vendorThreads.add(vendorThread);
                        vendorThread.start();
                    }

                    // Create and start customer threads
                    for (int i = 0; i < Configuration.customerCount; i++) {
                        Thread customerThread = new Thread(new Customer(Configuration.customerRetrievalRate, "Customer-" + (i + 1)));
                        customerThreads.add(customerThread);
                        customerThread.start();
                    }
                    ch = 1; // Update command state

                } else {
                    System.out.println("\u001B[31m" + "\n\tThe system is already running!" + "\u001B[0m");
                }

            } else if ("e".equals(command)) {
                // Stop only vendor and customer threads
                stopThreads(vendorThreads, "Vendor");
                stopThreads(customerThreads, "Customer");
                vendorThreads.clear();
                customerThreads.clear();
                System.out.println("\u001B[32m" + "\n\t***** Ticket system stopped. *****" + "\u001B[0m");
                ch = 0;
                // Reset the ticket pool state
                TicketPool newpool = new TicketPool(TicketPool.maxCapacity, TicketPool.totalTicketsLimit, TicketPool.eventName, TicketPool.totalTicketsAdded, TicketPool.totalTicketsRetrieved, TicketPool.tickets);
                Customer.pool = newpool;
                Vendor.pool = newpool;

            } else if ("r".equals(command)) {
                // Reset the entire system
                System.out.println("\u001B[32m" + "\n\t***** Exiting the system... *****" + "\u001B[0m");
                stopThreads(vendorThreads, "Vendor");
                stopThreads(customerThreads, "Customer");
                vendorThreads.clear();
                customerThreads.clear();
                run();// Restart the system setup

            }else if ("x".equals(command)) {
                // Exit the program completely
                stopThreads(vendorThreads, "Vendor");
                stopThreads(customerThreads, "Customer");
                System.out.println("\u001B[32m" + "\n\t***** Exiting the system... *****" + "\u001B[0m");
                System.exit(0); // Terminate the program

            } else {
                System.out.println("\u001B[31m" + "\nInvalid command. Enter 'S' to start, 'E' to stop, or 'X' to exit." + "\u001B[0m"); // Handle invalid commands
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
        run();// Start the main program loop
    }


}
