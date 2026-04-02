// Version 6.0 - Room Allocation with Uniqueness & Inventory Sync

import java.util.*;

// Reservation (from UC5)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // dequeue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Core UC6)
class BookingService {

    private RoomInventory inventory;

    // Track allocated rooms
    private Map<String, Set<String>> allocatedRooms;
    private Set<String> allRoomIds;

    private int idCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        allRoomIds = new HashSet<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 2).toUpperCase() + idCounter++;
        } while (allRoomIds.contains(id));

        allRoomIds.add(id);
        return id;
    }

    // Process booking
    public void processReservation(Reservation r) {

        String type = r.getRoomType();

        System.out.println("\nProcessing request for " + r.getGuestName());

        // Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("❌ No rooms available for " + type);
            return;
        }

        // Generate unique ID
        String roomId = generateRoomId(type);

        // Allocate room
        allocatedRooms.putIfAbsent(type, new HashSet<>());
        allocatedRooms.get(type).add(roomId);

        // Update inventory immediately
        inventory.decrement(type);

        // Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + type);
        System.out.println("Assigned Room ID: " + roomId);
    }

    public void displayAllocations() {
        System.out.println("\n===== Allocated Rooms =====");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 6.0) =====");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Priyansh", "Single Room"));
        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Neha", "Single Room")); // will fail
        queue.addRequest(new Reservation("Riya", "Suite Room"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        // Show final state
        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\n===== Processing Complete =====");
    }
}
