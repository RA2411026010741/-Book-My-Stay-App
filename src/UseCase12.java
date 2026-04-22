import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String reservationId;
    String customerName;
    String roomType;

    public Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class SystemState implements Serializable {
    List<Reservation> reservations;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> reservations, Map<String, Integer> inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("State saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving state");
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("State loaded successfully");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return new SystemState(new ArrayList<>(), new HashMap<>());
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistenceService = new PersistenceService();

        SystemState state = persistenceService.load();

        if (state.inventory.isEmpty()) {
            state.inventory.put("Single", 2);
            state.inventory.put("Double", 2);
            state.inventory.put("Suite", 1);
        }

        state.reservations.add(new Reservation("R101", "Alice", "Single"));
        state.reservations.add(new Reservation("R102", "Bob", "Double"));

        state.inventory.put("Single", state.inventory.get("Single") - 1);
        state.inventory.put("Double", state.inventory.get("Double") - 1);

        System.out.println("\nCurrent Reservations:");
        for (Reservation r : state.reservations) {
            System.out.println(r.reservationId + " | " + r.customerName + " | " + r.roomType);
        }

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> e : state.inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        persistenceService.save(state);
    }
}
