import java.util.HashMap;
import java.util.Map;

/**
 * ===============================================================
 * CLASS – RoomInventory
 * ===============================================================
 *
 * @author Developer
 * @version 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int count) {
        inventory.put(type, count);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/**
 * ===============================================================
 * MAIN CLASS – UseCase3InventorySetup
 * ===============================================================
 *
 * @author Developer
 * @version 3.1
 */
public class UseCase3 {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay Application      ");
        System.out.println("        Hotel Booking System v3.1     ");
        System.out.println("======================================");

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        inventory.displayInventory();

        System.out.println("======================================");
        System.out.println("Application finished.");
    }
}