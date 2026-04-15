import java.util.*;

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

    public void display() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    public void showAllRequests() {
        System.out.println("\nBooking Requests in Order:\n");
        for (Reservation r : queue) {
            r.display();
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double"));
        bookingQueue.addRequest(new Reservation("Diana", "Suite"));

        bookingQueue.showAllRequests();
    }
}
