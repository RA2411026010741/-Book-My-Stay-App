/**
 * ===============================================================
 * ABSTRACT CLASS – Room
 * ===============================================================
 * Represents a generalized hotel room.
 *
 * @author Developer
 * @version 2.0
 */
abstract class Room {

    protected String type;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price);
    }
}

/**
 * ===============================================================
 * CLASS – SingleRoom
 * ===============================================================
 *
 * @version 2.0
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

/**
 * ===============================================================
 * CLASS – DoubleRoom
 * ===============================================================
 *
 * @version 2.0
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

/**
 * ===============================================================
 * CLASS – SuiteRoom
 * ===============================================================
 *
 * @version 2.0
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 350.0);
    }
}

/**
 * ===============================================================
 * MAIN CLASS – UseCase2RoomInitialization
 * ===============================================================
 * Use Case 2: Basic Room Types & Static Availability
 *
 * @author Developer
 * @version 2.1
 */
public class UseCase2 {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("======================================");
        System.out.println("        Book My Stay Application      ");
        System.out.println("        Hotel Booking System v2.1     ");
        System.out.println("======================================");

        System.out.println("\nSingle Room Details");
        single.displayDetails();
        System.out.println("Available : " + singleAvailability);

        System.out.println("\nDouble Room Details");
        doubleRoom.displayDetails();
        System.out.println("Available : " + doubleAvailability);

        System.out.println("\nSuite Room Details");
        suite.displayDetails();
        System.out.println("Available : " + suiteAvailability);

        System.out.println("\nApplication finished.");
    }
}