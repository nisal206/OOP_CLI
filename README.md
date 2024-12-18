# Real-Time Event Ticketing System

## Overview

The Real-Time Event Ticketing System is a Java-based CLI application that simulates a real-time ticket booking system. It includes functionalities for managing ticket vendors, customers, and the ticket pool while ensuring smooth operations through configuration, threading, and dynamic system control.

## Features

- Load or create system configurations.
- Manage tickets dynamically with configurable limits.
- Start, stop, and reset ticket operations in real time.
- Vendor threads release tickets, and customer threads retrieve them concurrently.
- Robust CLI commands for controlling the system.

---

## Setup Instructions

### Prerequisites

1. Java Development Kit (JDK) 8 or later installed.
2. An Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse (optional).
3. Terminal or Command Prompt for running the application.

### File Structure

Ensure the following file structure is set up:

```
ProjectDirectory/
  src/
    Event/
      Main.java
      Configuration.java
      TicketPool.java
      Vendor.java
      Customer.java
  Configure.txt (optional; stores previous configuration)
```

### Configuration File (`Configure.txt`)

- This file stores previous configurations for the system.
- If unavailable, the application prompts you to set up a new configuration.

### Steps to Run

1. Compile the code:

   ```bash
   javac -d bin src/Event/*.java
   ```

2. Run the application:
   ```bash
   java -cp bin Event.Main
   ```

---

## CLI Usage Guidelines

The application runs interactively through the CLI, providing commands for control and management.

### Startup Options

- **Load Configuration**: If `Configure.txt` exists, load it.
- **Create Configuration**: Set up new parameters for ticket capacity, vendors, customers, etc.

### Commands

| Command | Description                                                     |
| ------- | --------------------------------------------------------------- |
| `S`     | Start ticket operations (initialize vendors and customers).     |
| `E`     | Stop vendor and customer threads while retaining configuration. |
| `R`     | Reset the system (reinitialize configuration and threads).      |
| `X`     | Exit the application completely.                                |

### Example Workflow

1. Launch the application.
2. Configure the system (or load existing configuration).
3. Use `S` to start operations.
4. Use `E` to stop ongoing operations if needed.
5. Use `R` to reset and reconfigure the system.
6. Use `X` to exit the application.

---

## Troubleshooting Information

### Common Issues

1. **Invalid Commands**

   - Error: "Invalid command. Enter 'S', 'E', 'R', or 'X'."
   - **Solution**: Ensure valid input without typos or extra spaces.

2. **File Not Found**

   - Error: "Configure.txt does not exist."
   - **Solution**: Allow the system to create a new configuration.

3. **Thread Interruption**

   - Error: Threads stop abruptly or show unexpected behavior.
   - **Solution**: Ensure all threads are managed properly using `E` or `R` commands.

4. **System Overload**
   - Issue: System lags or stops responding due to high thread count.
   - **Solution**: Reduce vendor or customer counts in the configuration.

### Debugging Tips

- Use `System.out.println` statements to log thread activities.
- Review `Configure.txt` to ensure configuration values are correct.
- Check for Java exceptions in the terminal output and trace back to the code.

---

## Credits

- **Developer:** Nisal Rukshan
