import java.util.*;

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

    public synchronized boolean allocateRoom(String roomType) {
        if (inventory.getOrDefault(roomType, 0) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingProcessor implements Runnable {
    private Queue<BookingRequest> queue;
    private InventoryService inventoryService;

    public BookingProcessor(Queue<BookingRequest> queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    return;
                }
                request = queue.poll();
            }

            boolean success = inventoryService.allocateRoom(request.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " confirmed booking for " + request.customerName +
                        " (" + request.roomType + ")");
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed booking for " + request.customerName +
                        " (" + request.roomType + ")");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        Queue<BookingRequest> queue = new LinkedList<>();
        InventoryService inventoryService = new InventoryService();

        queue.offer(new BookingRequest("Alice", "Single"));
        queue.offer(new BookingRequest("Bob", "Single"));
        queue.offer(new BookingRequest("Charlie", "Single"));
        queue.offer(new BookingRequest("David", "Double"));
        queue.offer(new BookingRequest("Eve", "Double"));
        queue.offer(new BookingRequest("Frank", "Suite"));
        queue.offer(new BookingRequest("Grace", "Suite"));

        Thread t1 = new Thread(new BookingProcessor(queue, inventoryService), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventoryService), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(queue, inventoryService), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventoryService.display();
    }
}
