import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.System.currentTimeMillis;

public class Customer implements Runnable {
    private List<Ticket> tickets;
    private List<Till> tills;
    private long deadlineIn;
    private long launchTime;

    Customer(List<Till> tills, long deadlineIn) {
        this.tills = tills;
        this.tickets = new ArrayList<>();
        this.deadlineIn = deadlineIn;
        this.launchTime = currentTimeMillis();
    }

    @Override
    public void run() {
        Random rand = new Random();
        while(currentTimeMillis() < launchTime + deadlineIn) {
            Till t = tills.get(rand.nextInt(tills.size()));
            t.queue.add(this);
            while (this!=t.queue.peek()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (tickets.isEmpty()) {
                List<Cinema> cinemas = t.getCinemaList();
                Ticket ticket = t.buyTicket(cinemas.get(rand.nextInt(cinemas.size())).name);
                if(ticket!=null)
                    tickets.add(ticket);
            } else {
                int guess = rand.nextInt(2);
                switch (guess) {
                    case 0:
                        List<Cinema> cinemas = t.getCinemaList();
                        Ticket ticket = t.buyTicket(cinemas.get(rand.nextInt(cinemas.size())).name);
                        if(ticket!=null)
                            tickets.add(ticket);
                        break;
                    case 1:
                        t.returnTicket(tickets.remove(rand.nextInt(tickets.size())));
                        break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.queue.remove(this);
        }
    }
}
