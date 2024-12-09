package Event;

import java.io.*;
import java.nio.file.*;
import java.util.*;


public class Configuration {
    private String eventName;
    private int vendorCount;
    private int customerCount;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    /**
     * Configures system parameters by gathering input from the user.
     */
    public void read(){
        Scanner input = new Scanner(System.in);
        List<Map<String, Integer>> configurations = new ArrayList<>();
        Map<Integer, String> eventNames = new HashMap<>();// Event names dictionary
        String[] keys = {"Event Name","Number of Vendors","Number of Customers","Total Ticket count","Ticket Release Rate", "Customer Retrieval Rate", "Maximum Ticket Capacity"};
        try(BufferedReader reader = new BufferedReader(new FileReader("Configure.txt"))){
            String line;
            int ch = 0;
            while ((line = reader.readLine()) != null){
                ch += 1;
                String[] values = line.split(",");
                if (values.length != keys.length) {
                    continue;
                }

                // Store event name and other attributes
                String eventName = values[0].trim();
                eventNames.put(ch, eventName); // Add to event name dictionary

                Map<String, Integer> dictionary = new  LinkedHashMap<>(); // Initialize dictionary once
                for (int i = 1; i < values.length; i++) {
                    dictionary.put(keys[i], Integer.parseInt(values[i].trim())); // Add to dictionary
                }

                configurations.add(dictionary); // Add to list of configurations
                String dictionaryString = dictionary.toString()
                        .replace("{", "") // Remove opening curly brace
                        .replace("}", ""); // Remove closing curly brace

                System.out.println("\n" + ch + ".    "+"Event Name=" + eventName +", "+ dictionaryString);
            }
            if(configurations.isEmpty()){
                System.out.println("\n\tOOp File Empty");
                setup();
                return;
            }
            int cho;
            while (true){
                System.out.print("\n Please enter configuration number do you want load (press 1 for configuration 1): ");
                String choice = input.next();
                try {
                   cho = Integer.parseInt(choice);
                    break;
                }catch (Exception e){
                    System.out.println("Please enter valid input.");
                }
            }
            if (cho > 0 && cho <= configurations.size()) {
                Map<String, Integer> selectedConfig = configurations.get(cho - 1);
                eventName = eventNames.get(cho);
                vendorCount = selectedConfig.get("Number of Vendors");
                customerCount = selectedConfig.get("Number of Customers");
                totalTickets = selectedConfig.get("Total Ticket count");
                ticketReleaseRate = selectedConfig.get("Ticket Release Rate");
                customerRetrievalRate = selectedConfig.get("Customer Retrieval Rate");
                maxTicketCapacity = selectedConfig.get("Maximum Ticket Capacity");


                System.out.println("\nConfiguration " + cho + " has been loaded.");
            } else {
                System.out.println("\n\tInvalid configuration number.");
                read();
            }

        }catch (IOException e){
            System.out.print("An error occurred. - 1");
        }
    }

    public void setup() {
        Scanner input = new Scanner(System.in);

        System.out.print("\nEnter event name: ");
        eventName = input.nextLine();

        while (true) {
            System.out.print("\nEnter the number of vendors: ");
            try {
                vendorCount = Integer.parseInt(input.nextLine());
                if (vendorCount > 0) break;
                else System.out.println("Vendor count must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }

        while (true) {
            System.out.print("\nEnter the number of customers: ");
            try {
                customerCount = Integer.parseInt(input.nextLine());
                if (customerCount > 0) break;
                else System.out.println("Customer count must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }

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
                ticketReleaseRate = Integer.parseInt(input.nextLine());
                if (ticketReleaseRate > 0) break;
                else System.out.println("Release interval must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a positive integer.");
            }
        }

        while (true) {
            System.out.print("\nEnter customer retrieval rate (seconds): ");
            try {
                customerRetrievalRate = Integer.parseInt(input.nextLine());
                if (customerRetrievalRate > 0) break;
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
    write();
    }

    public void write(){
        int ch = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader("Configure.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ch += 1;
            }
        }catch (IOException e){
//            System.out.print("An error occurred.-2");
        }

        String filePath = "Configure.txt"; // Path to the file
        if (ch == 5){
            try{
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                // Remove the first line
                if (!lines.isEmpty()) {
                    lines.remove(0);
                }

                // Write the remaining lines back to the file
                Files.write(Paths.get(filePath), lines);
            } catch (IOException e) {
                System.out.println("An error occurred while processing the file.");
            }
        }

        try (FileWriter writer = new FileWriter("Configure.txt", true)) { // true enables append mode
            writer.write(eventName+","+vendorCount+","+customerCount+","+totalTickets+","+ticketReleaseRate+","+customerRetrievalRate+","+maxTicketCapacity+"\n");
            System.out.println("\n\tConfigurations saved successfully. ");
        } catch (IOException e) {
            System.out.println("An error occurred while appending to the file.");
        }
    }


    public String getEventName() {
        return eventName;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}
