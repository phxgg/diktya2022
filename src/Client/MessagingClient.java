package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessagingClient {
    private String host;
    private int port;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public MessagingClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startConnection() {
        try {
            this.clientSocket = new Socket(host, port);
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public String request(String body) {
        try {
            this.out.println(body);
            String res = this.in.readLine();
            return res;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public void stopConnection() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
