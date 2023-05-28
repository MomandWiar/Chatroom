public class Count implements Runnable {

    private final int id;

    public Count(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            if (i == 3 && this.id == 1) {
                throw new RuntimeException("Whoops, something went wrong!");
            }

            System.out.println(i + " belongs to " + this.id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
    }
}
