package Event;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\u001B[35m" + "\n\t\t***** Real-Time Event Ticketing System *****" + "\u001B[0m");
        Scanner input = new Scanner(System.in);
        Configuration config = new Configuration(); // Setup configuration

        File file = new File("Configure.txt");
        if (file.exists()) {
            System.out.print("\nDo you want to load previous configuration (Enter 'Yes' or 'No'): ");
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
        TicketPool pool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets(), config.getEventName());
        List<Thread> threads = new ArrayList<>();

        // Create vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            threads.add(new Thread(new Vendor(pool, config.getTicketReleaseRate(), "Vendor-" + (i + 1))));
        }

        // Create customer threads
        for (int i = 0; i < config.getCustomerCount(); i++) {
            threads.add(new Thread(new Customer(pool, config.getCustomerRetrievalRate(), "Customer-" + (i + 1))));
        }

        // Start the system when 'S' is pressed and stop when 'E' is pressed
        while (true) {
            System.out.print("\nEnter 'S' to start ticket operations or 'E' to stop and end the program: ");
            String command = input.next().trim().toLowerCase();

            if ("s".equals(command)) {
                // Start all threads
                threads.forEach(Thread::start);
                System.out.println("\u001B[32m" + "\n\t***** Ticket system started. *****" + "\u001B[0m");

                // Start a new thread to listen for the 'E' command to stop the system
                new Thread(() -> {
                    while (true) {
                        String stopCommand = input.next().trim().toLowerCase();
                        if ("e".equals(stopCommand)) {
                            stopSystem(threads);
                            break;  // Exit after stopping the system
                        }
                    }
                }).start();  // Start the listener thread for stopping the system

                break;  // Exit after starting the system
            } else if ("e".equals(command)) {
                stopSystem(threads);
                break;  // Exit the while loop after stopping the system
            } else {
                System.out.println("\u001B[31m" + "\nInvalid command. Enter 'S' to start or 'E' to stop." + "\u001B[0m");
            }
        }
    }

    // Stop all threads immediately
    private static void stopSystem(List<Thread> threads) {
        System.out.println("\u001B[32m" + "\n\t***** Stopping ticket system... *****" + "\u001B[0m");
        for (Thread thread : threads) {
            thread.interrupt();  // Interrupt each thread
        }
    }
}
