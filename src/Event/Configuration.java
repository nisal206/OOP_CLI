package Event;

import java.util.Scanner;

public class Configuration {
    private String eventName;
    private int totalTickets;
    private int ticketReleaseInterval;
    private int customerRetrievalInterval;
    private int maxTicketCapacity;

    /**
     * Configures system parameters by gathering input from the user.
     */
    public void setup() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nEnter event name: ");
        eventName = input.nextLine();

        while (true) {
            System.out.print("\nEnter total ticket count: ");
            try {
                totalTickets = Integer.parseInt(input.nextLine());
                if (totalTickets > 0) break;
                else System.out.println("Total ticket count must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }

        while (true) {
            System.out.print("\nEnter ticket release rate (seconds): ");
            try {
                ticketReleaseInterval = Integer.parseInt(input.nextLine());
                if (ticketReleaseInterval > 0) break;
                else System.out.println("Release interval must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }

        while (true) {
            System.out.print("\nEnter customer retrieval rate (seconds): ");
            try {
                customerRetrievalInterval = Integer.parseInt(input.nextLine());
                if (customerRetrievalInterval > 0) break;
                else System.out.println("Retrieval interval must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }

        while (true) {
            System.out.print("\nEnter max ticket capacity (pool size): ");
            try {
                maxTicketCapacity = Integer.parseInt(input.nextLine());
                if (maxTicketCapacity > 0 && maxTicketCapacity <= totalTickets) break;
                else System.out.println("Max ticket capacity must be positive and <= total tickets.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }
    }

    public String getEventName() {
        return eventName;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseInterval() {
        return ticketReleaseInterval;
    }

    public int getCustomerRetrievalInterval() {
        return customerRetrievalInterval;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}

