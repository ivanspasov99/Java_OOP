package bg.sofia.uni.fmi.mjt.christmas;

public class Christmas {

    private WorkShop workshop;

    /**
     * The number of kids that are going to send wishes to Santas's workshop.
     **/
    private int numberOfKids;

    /**
     * Christmas will start in {@code christmasTime} milliseconds.
     **/
    private int christmasTime;

    public Christmas(WorkShop workshop, int numberOfKids, int christmasTime) {
        this.workshop = workshop;
        this.numberOfKids = numberOfKids;
        this.christmasTime = christmasTime;
    }

    public static void main(String[] args) {
        Christmas christmas = new Christmas(new WorkShop(), 100, 2000);
        christmas.celebrate();
    }

    public void celebrate() {
        // Start kids
        Thread[] kids = new Thread[numberOfKids];
        for (int i = 0; i < kids.length; i++) {
            kids[i] = new Thread(new Kid(workshop));
            kids[i].start();
        }
        // Wait for the kids threads to finish
        for (Thread kid : kids) {
            try {
                kid.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public WorkShop getWorkshop() {
        return workshop;
    }
}