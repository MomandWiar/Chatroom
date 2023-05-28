import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServer implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Initializing server on port 1233..");
            ServerSocket server = new ServerSocket(1233);

            System.out.println("Awaiting connection..");
            Socket socket = server.accept();
            System.out.println("Connection made.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Connection has been made with the Server");
            out.println("Choose a nickname");
            out.println("Hallo " + in.readLine());

            String inputLine;
            while((inputLine = in.readLine()) != null) {
                if (inputLine.equals("exit")) {
                    break;
                }
                out.println(inputLine);
            }

            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
