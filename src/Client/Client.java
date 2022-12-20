package Client;

public class Client {
    public static void main(String[] args) {
        if (!(args.length >= 2)) {
            System.out.println("Usage: java -jar Client.jar <host> <port>");
            return;
        }

        // Get args
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // Rest of the args are server arguments
        String[] serverArgs = new String[args.length - 2];
        System.arraycopy(args, 2, serverArgs, 0, args.length - 2);

        // Concat serverArgs into a string with spaces
        String serverArgsString = String.join(" ", serverArgs);

        MessagingClient client = new MessagingClient(host, port);
        client.startConnection();

        String response = client.request(serverArgsString);
        System.out.print(response.replaceAll("<NL>", "\n"));

        // After the response, close the connection
        client.stopConnection();
    }
}