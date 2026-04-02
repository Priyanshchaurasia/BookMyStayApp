// Version 4.0 - Room Search with Read-Only Access

import java.util.*;

// Abstract Room Class
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

// Concrete Rooms
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

// Inventory (Same as UC3)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // purposely unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return Collections.unmodifiableMap(inventory); // defensive
    }
}

// Search Service (NEW)
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Read-only search method
    public void searchAvailableRooms(List<Room> rooms) {

        System.out.println("===== Available Rooms =====\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Validation: only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

// Main Class
public class BookMyStayApp
    {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 4.0) =====\n");

        // Room objects (Domain)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Inventory (State Holder)
        RoomInventory inventory = new RoomInventory();

        // Search Service (Read-only layer)
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search (NO state change)
        searchService.searchAvailableRooms(rooms);

        System.out.println("===== Search Completed (No State Modified) =====");
    }
}
