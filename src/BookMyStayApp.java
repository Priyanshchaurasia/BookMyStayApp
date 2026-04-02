// Version 11.0 - Concurrent Booking Simulation with Thread Safety

import java.util.*;

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

// Thread-Safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // Critical Section (synchronized)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("\n===== Final Inventory =====");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

// Shared Booking Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    // synchronized add
    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    // synchronized retrieval
    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingRequestQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingRequestQueue queue, RoomInventory inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // Critical section for queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getNextRequest();
            }

            if (r != null) {
                process(r);
            }
        }
    }

    private void process(Reservation r) {

        // Critical section for inventory
        synchronized (inventory) {

            System.out.println(Thread.currentThread().getName() +
                    " processing " + r.getGuestName());

            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println("✅ " + r.getGuestName() +
                        " booked " + r.getRoomType());
            } else {
                System.out.println("❌ " + r.getGuestName() +
                        " failed (No availability)");
            }
        }
    }
}

// Main Class
public class BookMyStayApp 
{

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 11.0) =====");

        // Shared resources
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulate concurrent requests
        queue.addRequest(new Reservation("Priyansh", "Single Room"));
        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Neha", "Single Room")); // may fail
        queue.addRequest(new Reservation("Riya", "Double Room"));
        queue.addRequest(new Reservation("Karan", "Double Room")); // may fail

        // Multiple threads (guests)
        Thread t1 = new BookingProcessor(queue, inventory, "Thread-1");
        Thread t2 = new BookingProcessor(queue, inventory, "Thread-2");
        Thread t3 = new BookingProcessor(queue, inventory, "Thread-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.displayInventory();

        System.out.println("\n===== Concurrent Processing Completed Safely =====");
    }
}
