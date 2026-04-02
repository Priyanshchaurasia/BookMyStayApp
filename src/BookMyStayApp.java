// Version 8.0 - Booking History & Reporting

import java.util.*;

// Reservation (Enhanced for history tracking)
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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// Booking History (Core UC8)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings (read-only style)
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history);
    }
}

// Report Service (NEW)
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n===== Booking History =====");

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n===== Booking Summary Report =====");

        Map<String, Integer> countByRoom = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            countByRoom.put(type, countByRoom.getOrDefault(type, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : countByRoom.entrySet()) {
            System.out.println(entry.getKey() + " → Total Bookings: " + entry.getValue());
        }

        System.out.println("Total Reservations: " + reservations.size());
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 8.0) =====");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings (from UC6)
        Reservation r1 = new Reservation("SI1", "Priyansh", "Single Room");
        Reservation r2 = new Reservation("SI2", "Amit", "Single Room");
        Reservation r3 = new Reservation("SU3", "Neha", "Suite Room");

        // Add to history (chronological order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Report Service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\n===== Reporting Complete (Read-Only) =====");
    }
}
