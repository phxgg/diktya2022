package Server;

import java.net.ServerSocket;

public class MessagingServer {
    private final int port;
    private ServerSocket serverSocket;
    private final CommandHandler commandHandler;

    public MessagingServer(ApplicationContext context, int port) {
        this.port = port;
        this.commandHandler = new CommandHandler(context);
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                new ClientHandler(commandHandler, this.serverSocket.accept()).start();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void stop() {
        try {
            this.serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
