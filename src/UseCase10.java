import java.util.*;

class Reservation {
    String reservationId;
    String customerName;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String customerName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingService {
    private Map<String, Reservation> reservations;
    private Set<String> allocatedRoomIds;
    private Stack<String> rollbackStack;
    private InventoryService inventoryService;
    private int counter = 100;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        reservations = new HashMap<>();
        allocatedRoomIds = new HashSet<>();
        rollbackStack = new Stack<>();
    }

    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 1).toUpperCase() + (++counter);
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }

    public String confirmBooking(String customerName, String roomType) {
        if (!inventoryService.isAvailable(roomType)) {
            System.out.println("Booking failed for " + customerName);
            return null;
        }

        String roomId = generateRoomId(roomType);
        allocatedRoomIds.add(roomId);
        inventoryService.decrement(roomType);

        String reservationId = "R" + counter;
        Reservation reservation = new Reservation(reservationId, customerName, roomType, roomId);
        reservations.put(reservationId, reservation);

        System.out.println("Booking Confirmed: " + reservationId + " -> " + roomId);
        return reservationId;
    }

    public void cancelBooking(String reservationId) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Invalid reservation ID " + reservationId);
            return;
        }

        Reservation reservation = reservations.remove(reservationId);

        rollbackStack.push(reservation.roomId);
        allocatedRoomIds.remove(reservation.roomId);
        inventoryService.increment(reservation.roomType);

        System.out.println("Booking Cancelled: " + reservationId + " Room Released: " + reservation.roomId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack:");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        String r1 = bookingService.confirmBooking("Alice", "Single");
        String r2 = bookingService.confirmBooking("Bob", "Double");
        String r3 = bookingService.confirmBooking("Charlie", "Suite");

        bookingService.cancelBooking(r2);
        bookingService.cancelBooking("R999");

        bookingService.showRollbackStack();
        inventoryService.display();
    }
}
