package Server;

public class Server {

    public static void main(String[] args) {
        if (!(args.length >= 1)) {
            System.out.println("Usage: java -jar Server.jar <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        ApplicationContext context = new ApplicationContext();

        MessagingServer server = new MessagingServer(context, port);
        server.start();
    }
}