import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Cinema> cinemas = new ArrayList<>();
    private static List<Till> tills = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        cinemas.add(new Cinema("Los Alamos", 20));
        cinemas.add(new Cinema("Space Plaza", 20));
        tills.add(new Till(cinemas));
        tills.add(new Till(cinemas));
        long deadline = 1_000;
        for (int i = 0; i < 50; i++) {
            Customer customer = new Customer(tills, deadline);
            Thread thread = new Thread(customer);
            thread.start();
            Thread.sleep(500);
        }

    }
}
