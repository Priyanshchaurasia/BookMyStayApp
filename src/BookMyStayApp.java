// Version 10.0 - Booking Cancellation & Inventory Rollback

import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public void display() {
        System.out.println("ID: " + reservationId + " | Guest: " + guestName + " | Room: " + roomType);
    }
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 0);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// Booking History (Track confirmed bookings)
class BookingHistory {
    private Map<String, Reservation> confirmed = new HashMap<>();

    public void add(Reservation r) {
        confirmed.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return confirmed.get(id);
    }

    public void remove(String id) {
        confirmed.remove(id);
    }

    public boolean exists(String id) {
        return confirmed.containsKey(id);
    }

    public void display() {
        System.out.println("\n===== Active Bookings =====");
        for (Reservation r : confirmed.values()) {
            r.display();
        }
    }
}

// Cancellation Service (Core UC10)
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelReservation(String reservationId) {

        System.out.println("\nProcessing cancellation for ID: " + reservationId);

        // Validate existence
        if (!history.exists(reservationId)) {
            System.out.println("❌ Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation r = history.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increment(r.getRoomType());

        // Remove from active bookings
        history.remove(reservationId);

        System.out.println("✅ Cancellation Successful!");
        System.out.println("Room released for type: " + r.getRoomType());
    }

    public void displayRollbackStack() {
        System.out.println("\n===== Rollback Stack (Recent Cancellations) =====");
        System.out.println(rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 10.0) =====");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService(inventory, history);

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("SI1", "Priyansh", "Single Room");
        Reservation r2 = new Reservation("SU2", "Amit", "Suite Room");

        history.add(r1);
        history.add(r2);

        history.display();

        // Perform cancellations
        cancelService.cancelReservation("SI1"); // valid
        cancelService.cancelReservation("XX9"); // invalid
        cancelService.cancelReservation("SI1"); // duplicate cancel

        // Display final state
        history.display();
        inventory.displayInventory();
        cancelService.displayRollbackStack();

        System.out.println("\n===== System State Restored Successfully =====");
    }
}
