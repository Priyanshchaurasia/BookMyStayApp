// Version 9.0 - Error Handling & Validation

import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
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
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0); // intentionally 0
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int current = inventory.get(roomType);

        if (current <= 0) {
            throw new InvalidBookingException("No availability for " + roomType);
        }

        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Validator (NEW - Core UC9)
class InvalidBookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }
    }
}

// Booking Service with Validation
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation r) {

        try {
            // Step 1: Validate input (Fail-Fast)
            InvalidBookingValidator.validate(r, inventory);

            // Step 2: Allocate (only if valid)
            inventory.decrement(r.getRoomType());

            System.out.println("✅ Booking Successful!");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + r.getRoomType());

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("❌ Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 9.0) =====");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Test cases (valid + invalid)
        Reservation r1 = new Reservation("Priyansh", "Single Room"); // valid
        Reservation r2 = new Reservation("", "Double Room");         // invalid name
        Reservation r3 = new Reservation("Amit", "Luxury Room");     // invalid type
        Reservation r4 = new Reservation("Neha", "Suite Room");      // no availability

        // Process bookings
        service.processBooking(r1);
        service.processBooking(r2);
        service.processBooking(r3);
        service.processBooking(r4);

        // Final inventory state
        inventory.displayInventory();

        System.out.println("\n===== System Running Safely After Errors =====");
    }
}
