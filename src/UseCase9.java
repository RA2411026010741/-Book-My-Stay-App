import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
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

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("Inventory cannot go negative for: " + roomType);
        }
        inventory.put(roomType, count - 1);
    }
}

class BookingService {
    private Queue<BookingRequest> queue;
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        queue = new LinkedList<>();
    }

    public void addRequest(BookingRequest request) {
        queue.offer(request);
    }

    public void processBookings() {
        while (!queue.isEmpty()) {
            BookingRequest request = queue.poll();

            try {
                if (request.customerName == null || request.customerName.trim().isEmpty()) {
                    throw new InvalidBookingException("Customer name cannot be empty");
                }

                inventoryService.validateRoomType(request.roomType);
                inventoryService.validateAvailability(request.roomType);

                inventoryService.decrement(request.roomType);

                System.out.println("Booking Confirmed");
                System.out.println("Customer: " + request.customerName);
                System.out.println("Room Type: " + request.roomType);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        bookingService.addRequest(new BookingRequest("Alice", "Single"));
        bookingService.addRequest(new BookingRequest("", "Double"));
        bookingService.addRequest(new BookingRequest("Bob", "Deluxe"));
        bookingService.addRequest(new BookingRequest("Charlie", "Suite"));
        bookingService.addRequest(new BookingRequest("David", "Suite"));

        bookingService.processBookings();
    }
}
