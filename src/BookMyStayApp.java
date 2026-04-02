// Version 5.0 - Booking Request Queue using FIFO

import java.util.*;

// Reservation Class (Represents a booking request)
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

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Request Queue (Core of UC5)
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all queued requests (without removing)
    public void displayQueue() {
        System.out.println("\n===== Booking Request Queue =====");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayRequest();
        }
    }

    // Peek next request (FIFO head)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// Main Class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 5.0) =====\n");

        // Initialize queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate booking requests (arrival order matters)
        Reservation r1 = new Reservation("Priyansh", "Single Room");
        Reservation r2 = new Reservation("Amit", "Suite Room");
        Reservation r3 = new Reservation("Neha", "Double Room");

        // Add requests to queue (FIFO order)
        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        // Display queue (order preserved)
        requestQueue.displayQueue();

        // Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = requestQueue.peekNext();
        if (next != null) {
            next.displayRequest();
        }

        System.out.println("\n===== Requests Stored (No Allocation Done Yet) =====");
    }
}
