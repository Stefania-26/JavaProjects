import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Till {
    private List<Cinema> cinemaList;
    Queue<Customer> queue = new LinkedList<>();

    Till(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    synchronized Ticket buyTicket(String cinemaName){
            for (Cinema cinema : cinemaList
                    ) {
                if (cinema.name.equals(cinemaName)) {
                    if (cinema.ticketsCount > 0) {
                        synchronized (cinema) {
                            cinema.ticketsCount--;
                        }
                        System.out.print("Customer-" + Thread.currentThread().getName().split("-")[1]+": "+"Bought ticket for " + cinema.name + ", " + cinema.ticketsCount + " tickets remained.\n");
                        return new Ticket(cinema.name);
                    } else {
                        System.out.print("Customer-" + Thread.currentThread().getName().split("-")[1]+": "+"No tickets for "  + cinema.name + ", amazing.\n");
                        return null;
                    }
                }

            }
            return null;
    }

    synchronized void returnTicket(Ticket t){
            for (Cinema cinema : cinemaList
                    ) {
                if (cinema.name.equals(t.getCinemaName())) {
                    synchronized (cinema) {
                        cinema.ticketsCount++;
                    }
                    System.out.print("Customer-" + Thread.currentThread().getName().split("-")[1]+": "+"returned ticket for " + cinema.name + ", " + cinema.ticketsCount + " tickets remained.\n");
                    return;
                }
            }
    }

    List<Cinema> getCinemaList(){
        return cinemaList;
    }
}
