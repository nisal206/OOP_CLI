package Event;

import java.io.*;
import java.nio.file.*;
import java.util.*;


public class Configuration {
    // Static variables for storing configuration details
    public static  String eventName;// The name of the event
    public static int vendorCount;// Number of vendors
    public static int customerCount;// Number of customers
    public static int totalTickets;// Total number of tickets available
    public static int ticketReleaseRate;// Rate at which tickets are released (in seconds)
    public static int customerRetrievalRate;// Rate at which customers retrieve tickets (in seconds)
    public static int maxTicketCapacity;// Maximum number of tickets allowed in the pool at any time

    public void read(){
        Scanner input = new Scanner(System.in);
        List<Map<String, Integer>> configurations = new ArrayList<>();// List to store all configurations
        Map<Integer, String> eventNames = new HashMap<>();// Event names dictionary
        String[] keys = {"Event Name","Number of Vendors","Number of Customers","Total Ticket count","Ticket Release Rate", "Customer Retrieval Rate", "Maximum Ticket Capacity"};
        try(BufferedReader reader = new BufferedReader(new FileReader("Configure.txt"))){
            String line;
            int ch = 0;// Counter for the number of configurations
            while ((line = reader.readLine()) != null){
                ch ++;// Increment the counter for each configuration
                String[] values = line.split(",");// Split the line into configuration values
                if (values.length != keys.length) {
                    continue; // If the line does not have the expected number of values, skip it
                }

                // Store event name and other attributes
                String eventName = values[0].trim();// First value is the event name
                eventNames.put(ch, eventName);// Add event name to the map with its index

                Map<String, Integer> dictionary = new  LinkedHashMap<>(); // Create a map for the configuration
                for (int i = 1; i < values.length; i++) {
                    dictionary.put(keys[i], Integer.parseInt(values[i].trim())); // Add to dictionary
                }

                configurations.add(dictionary);  // Add the configuration map to the list
                String dictionaryString = dictionary.toString()
                        .replace("{", "") // Remove opening curly brace
                        .replace("}", ""); // Remove closing curly brace

                System.out.println("\u001B[33m"+"\n" + ch + ".    "+"Event Name=" + eventName +", "+ dictionaryString+"\u001B[0m");
            }
            if(configurations.isEmpty()){// If no configurations were found in the file, prompt user to set up a new one
                System.out.println("\u001B[31m"+"\n\tOOp File Empty"+"\u001B[0m");
                setup();// Call setup to allow user to create a new configuration
                return;

            }
            int cho;
            while (true){
                System.out.print("\n Please enter configuration number do you want load (press 1 for configuration 1): ");
                String choice = input.next();// Read the user's choice
                try {
                    cho = Integer.parseInt(choice);// Convert the choice to an integer
                    break;// Exit loop if conversion is successful
                }catch (Exception e){
                    System.out.println("\u001B[31m"+"Please enter valid input."+"\u001B[0m");
                }
            }
            // Load the selected configuration
            if (cho > 0 && cho <= configurations.size()) {
                Map<String, Integer> selectedConfig = configurations.get(cho - 1);// Get the selected configuration
                this.eventName = eventNames.get(cho); // Retrieve the corresponding event name
                vendorCount = selectedConfig.get("Number of Vendors"); // Set the number of vendors
                customerCount = selectedConfig.get("Number of Customers");// Set the number of customers
                totalTickets = selectedConfig.get("Total Ticket count");// Set the total ticket count
                ticketReleaseRate = selectedConfig.get("Ticket Release Rate");// Set the ticket release rate
                customerRetrievalRate = selectedConfig.get("Customer Retrieval Rate");// Set the customer retrieval rate
                maxTicketCapacity = selectedConfig.get("Maximum Ticket Capacity");// Set the maximum ticket pool size


                System.out.println("\u001B[32m"+"\n\tConfiguration " + cho + " has been loaded."+"\u001B[0m");
            } else { // Invalid configuration number entered by user
                System.out.println("\u001B[31m"+"\n\tInvalid configuration number."+"\u001B[0m");
                read();// Restart the process
            }

        }catch (IOException e){
            System.out.print("\u001B[31m"+"An error occurred."+"\u001B[0m");// Handle any file-related errors
        }
    }

    public void setup() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nEnter event name: "); // Prompt for event name
        eventName = input.nextLine();

        while (true) {
            System.out.print("\nEnter the number of vendors: ");
            try {
                vendorCount = Integer.parseInt(input.nextLine());
                if (vendorCount > 0) break;
                else System.out.println("\u001B[31m"+"\n\tVendor count must be positive."+"\u001B[0m");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m"+"\n\tInvalid input. Enter a positive integer."+"\u001B[0m");
            }
        }

        while (true) {
            System.out.print("\nEnter the number of customers: ");
            try {
                this.customerCount = Integer.parseInt(input.nextLine());
                if (customerCount > 0) break;
                else System.out.println("\u001B[31m"+"\n\tCustomer count must be positive."+"\u001B[0m");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m"+"\n\tInvalid input. Enter a positive integer."+"\u001B[0m");
            }
        }

        while (true) {
            System.out.print("\nEnter total ticket count: ");
            try {
                totalTickets = Integer.parseInt(input.nextLine());
                if (totalTickets > 0) break;
                else System.out.println("\u001B[31m"+"\n\tTotal ticket count must be positive."+"\u001B[0m");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m"+"\n\tInvalid input. Enter a positive integer."+"\u001B[0m");
            }
        }

        while (true) {
            System.out.print("\nEnter ticket release rate (seconds): ");
            try {
                ticketReleaseRate = Integer.parseInt(input.nextLine());
                if (ticketReleaseRate > 0) break;
                else System.out.println("\u001B[31m"+"\n\tRelease interval must be positive."+"\u001B[0m");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m"+"\n\tInvalid input. Enter a positive integer."+"\u001B[0m");
            }
        }

        while (true) {
            System.out.print("\nEnter customer retrieval rate (seconds): ");
            try {
                customerRetrievalRate = Integer.parseInt(input.nextLine());
                if (customerRetrievalRate > 0) break;
                else System.out.println("\u001B[31m"+"\n\tRetrieval interval must be positive."+"\u001B[0m");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m"+"\n\tInvalid input. Enter a positive integer."+"\u001B[0m");
            }
        }

        while (true) {
            System.out.print("\nEnter max ticket capacity (pool size): ");
            try {
                maxTicketCapacity = Integer.parseInt(input.nextLine());
                if (maxTicketCapacity > 0 && maxTicketCapacity <= totalTickets) break;
                else System.out.println("\u001B[31m"+"\n\tMax ticket capacity must be positive and <= total tickets."+"\u001B[0m");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m"+"\n\tInvalid input. Enter a positive integer."+"\u001B[0m");
            }
        }
    write(); // Save the new configuration to the file
    }

    public void write(){
        int ch = 0;// Counter to keep track of existing configurations in the file
        try(BufferedReader reader = new BufferedReader(new FileReader("Configure.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ch ++;// Count the number of lines (configurations) in the file
            }
        }catch (IOException e){
            // Ignore file read errors during this step
        }
        // If there are already 5 configurations, remove the oldest one
        String filePath = "Configure.txt"; // Path to the file
        if (ch == 5){
            try{
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                // Remove the first line
                if (!lines.isEmpty()) {
                    lines.remove(0);// Remove the first line
                }
                Files.write(Paths.get(filePath), lines); // Write the updated lines back to the file
            } catch (IOException e) {
                System.out.println("\u001B[31m"+"\n\tAn error occurred while processing the file."+"\u001B[0m");
            }
        }
        // Append the new configuration to the file
        try (FileWriter writer = new FileWriter("Configure.txt", true)) {
            writer.write(eventName+","+vendorCount+","+customerCount+","+totalTickets+","+ticketReleaseRate+","+customerRetrievalRate+","+maxTicketCapacity+"\n");
            System.out.println("\u001B[32m"+"\n\tConfigurations saved successfully."+"\u001B[0m");
        } catch (IOException e) {
            System.out.println("\u001B[31m"+"\n\tAn error occurred while appending to the file."+"\u001B[0m");
        }
    }
}
