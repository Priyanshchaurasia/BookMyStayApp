// Version 3.1 - Centralized Inventory Management using HashMap

import java.util.HashMap;
import java.util.Map;

// Abstract Room Class (Same Domain Model)
abstract class Room {
    private String roomType;
    private int beds;
    private double size;
    private double price;

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 120.0, 2000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 200.0, 3500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0, 6000.0);
    }
}

// Inventory Class (NEW - Core of UC3)
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor → Initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
        System.out.println();
    }
}

// Main Class
public class BookMyStayApp
    {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 3.1) =====\n");

        // Room objects (Domain)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Inventory initialization (Centralized)
        RoomInventory inventory = new RoomInventory();

        // Display Room Details + Availability from HashMap
        System.out.println("---- Single Room ----");
        single.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(single.getRoomType()));
        System.out.println();

        System.out.println("---- Double Room ----");
        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(doubleRoom.getRoomType()));
        System.out.println();

        System.out.println("---- Suite Room ----");
        suite.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(suite.getRoomType()));
        System.out.println();

        // Show full inventory
        inventory.displayInventory();

        // Demonstrate update
        System.out.println("Updating Suite Room availability to 1...\n");
        inventory.updateAvailability("Suite Room", 1);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("===== Application Terminated =====");
    }
}
