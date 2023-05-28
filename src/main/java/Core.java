import java.util.Scanner;

public class Core {

    public static void main(String[] args) {
        // Create a MultiServer instance
        MultiServer server = new MultiServer();

        // Start the server on port 8080 in a separate thread
        Thread serverThread = new Thread(() -> server.start(8080));
        serverThread.start();

        // Read input from console to stop the server
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("Stop")) {
            server.stop();
        }

        // Wait for the server thread to complete
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
