package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final CommandHandler commandHandler;
    private final Socket clientSocket;

    public ClientHandler(CommandHandler commandHandler, Socket socket) {
        this.clientSocket = socket;
        this.commandHandler = commandHandler;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String request;
            while ((request = in.readLine()) != null) {

                // Handle the request
                String response = commandHandler.handle(request);
                out.println(response);
            }

            in.close();
            out.close();
            this.clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
