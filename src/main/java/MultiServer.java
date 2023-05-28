import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * MultiServer class represents a server that accepts client connections and handles them concurrently.
 */
public class MultiServer {
    private ServerSocket serverSocket;
    private boolean shutDownRequested;
    private int nextClientId;
    private List<PrintWriter> clientWriters;

    /**
     * Starts the server on the specified port and begins accepting client connections.
     *
     * @param port the port to listen for client connections
     */
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Initializing server on port " + port);
            System.out.println("Awaiting connection");

            clientWriters = new ArrayList<>();
            acceptClients();
        } catch (IOException e) {
            System.err.println("Error initializing the server: " + e.getMessage());
        }
    }

    /**
     * Accepts incoming client connections and creates a new thread to handle each client.
     */
    private void acceptClients() {
        try {
            while (!shutDownRequested) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(out);

                new ClientHandler(clientSocket, out, ++nextClientId).start();
            }

            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error accepting clients: " + e.getMessage());
        }
    }

    /**
     * Stops the server, closes all client connections, and releases resources.
     */
    public void stop() {
        shutDownRequested = true;

        // Close all client sockets
        for (PrintWriter writer : clientWriters) {
            writer.println("Server is shutting down. Connection closed.");
            writer.flush();
            writer.close();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing the server socket: " + e.getMessage());
        }
    }

    /**
     * ClientHandler class represents a thread that handles communication with a single client.
     */
    public class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final PrintWriter clientWriter;
        private final int clientId;

        public ClientHandler(Socket socket, PrintWriter writer, int id) {
            this.clientSocket = socket;
            this.clientWriter = writer;
            this.clientId = id;
        }

        @Override
        public void run() {
            try {
                handleClient();
            } catch (IOException e) {
                handleException("Error handling client " + clientId + ": " + e.getMessage());
            } finally {
                closeClientSocket();
            }
        }

        /**
         * Handles the client's communication by reading and processing messages.
         *
         * @throws IOException if there is an error in the communication
         */
        private void handleClient() throws IOException {
            System.out.println("Client " + clientId + " connected");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            clientWriter.println("Choose a nickname");
            String nickname = in.readLine();
            broadcastMessage(nickname + " has joined");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("exit")) {
                    break;
                }
                broadcastMessage(nickname + ": " + inputLine);
            }

            removeClientHandler(this);
            broadcastMessage(nickname + " has left");
            System.out.println("Client " + clientId + " closed connection");
        }

        /**
         * Broadcasts a message to all connected clients.
         *
         * @param message the message to broadcast
         */
        private void broadcastMessage(String message) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }

        /**
         * Removes the ClientHandler instance from the clientWriters list.
         *
         * @param handler the ClientHandler instance to remove
         */
        private synchronized void removeClientHandler(ClientHandler handler) {
            clientWriters.remove(handler.clientWriter);
        }

        /**
         * Handles an exception occurred during client communication.
         *
         * @param message the error message
         */
        private void handleException(String message) {
            System.err.println(message);
        }

        /**
         * Closes the client socket.
         */
        private void closeClientSocket() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket for client " + clientId + ": " + e.getMessage());
            }
        }
    }
}
