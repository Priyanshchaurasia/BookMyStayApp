// Version 12.0 - Data Persistence & System Recovery

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// System State (Serializable Wrapper)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service (Core UC12)
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("✅ System state saved successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("✅ System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("⚠ No saved state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("❌ Error loading state: " + e.getMessage());
        }

        // Return empty default state if failure
        return new SystemState(new HashMap<>(), new ArrayList<>());
    }
}

// Main Class
public class BookMyStayApp
    {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 12.0) =====");

        // Step 1: Load previous state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory = state.inventory;
        List<Reservation> bookings = state.bookings;

        // If first run, initialize
        if (inventory.isEmpty()) {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
        }

        // Step 2: Simulate booking
        Reservation r1 = new Reservation("SI1", "Priyansh", "Single Room");
        bookings.add(r1);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        // Display current state
        System.out.println("\n===== Current Bookings =====");
        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\n===== Current Inventory =====");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }

        // Step 3: Save state before shutdown
        PersistenceService.save(new SystemState(inventory, bookings));

        System.out.println("\n===== System Ready for Restart =====");
    }
}
