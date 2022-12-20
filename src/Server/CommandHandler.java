package Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {
    private final ApplicationContext context;

    public CommandHandler(ApplicationContext context) {
        this.context = context;
    }

    public String handle(String args) {
        String[] commandParts = args.split(" ");

        int command = Integer.parseInt(commandParts[0]);

        if (command == Commands.CREATE_ACCOUNT.label) {
            String username = commandParts[1];
            return createAccount(username);
        } else if (command == Commands.SHOW_ACCOUNTS.label) {
            Integer authToken = Integer.parseInt(commandParts[1]);
            return showAccounts(authToken);
        } else if (command == Commands.SEND_MESSAGE.label) {
            Integer authToken = Integer.parseInt(commandParts[1]);
            String recipient = commandParts[2];

            // Delete the first 3 parts of the commandParts array
            String[] messageParts = new String[commandParts.length - 3];
            System.arraycopy(commandParts, 3, messageParts, 0, commandParts.length - 3);

            // Concat the remaining commandParts into a string with spaces from index 3
            String message = String.join(" ", messageParts);

            return sendMessage(authToken, recipient, message);
        } else if (command == Commands.SHOW_INBOX.label) {
            Integer authToken = Integer.parseInt(commandParts[1]);
            return showInbox(authToken);
        } else if (command == Commands.READ_MESSAGE.label) {
            Integer authToken = Integer.parseInt(commandParts[1]);
            int messageId = Integer.parseInt(commandParts[2]);
            return readMessage(authToken, messageId);
        } else if (command == Commands.DELETE_MESSAGE.label) {
            Integer authToken = Integer.parseInt(commandParts[1]);
            int messageId = Integer.parseInt(commandParts[2]);
            return deleteMessage(authToken, messageId);
        } else {
            return "Invalid command";
        }
    }

    private boolean validateAuthToken(Integer authToken) {
        for (Account account : context.getAccountManager()) {
            if (account.getAuthToken().equals(authToken)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateUsername(String username) {
        for (Account account : context.getAccountManager()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private Account getAccountByAuthToken(Integer authToken) {
        for (Account account : context.getAccountManager()) {
            if (account.getAuthToken().equals(authToken)) {
                return account;
            }
        }
        return null;
    }

    private Account getAccountByUsername(String username) {
        for (Account account : context.getAccountManager()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    private String createAccount(String username) {
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            return "Invalid username";
        }

        Integer authToken = Utils.get6DigitRandomInteger();

        for (Account account : this.context.getAccountManager()) {
            if (account.getAuthToken().equals(authToken)) {
                authToken = Utils.get6DigitRandomInteger();
            }

            if (account.getUsername().equals(username)) {
                return "Sorry, the user already exists";
            }
        }

        Account account = new Account(username);
        account.setAuthToken(authToken);

        context.getAccountManager().add(account);

        return authToken.toString();
    }

    private String showAccounts(Integer authToken) {
        if (!validateAuthToken(authToken)) {
            return "Invalid Auth Token";
        }

        StringBuilder accounts = new StringBuilder();

        int i = 1;
        for (Account account : context.getAccountManager()) {
            accounts.append(String.format("%d. %s<NL>", i, account.getUsername()));
            i++;
        }

        return accounts.toString();
    }

    private String sendMessage(Integer authToken, String recipient, String message) {
        if (!validateAuthToken(authToken)) {
            return "Invalid Auth Token";
        }

        if (!validateUsername(recipient)) {
            return "User does not exist";
        }

        Account sender = getAccountByAuthToken(authToken);
        assert sender != null;

        if (sender.getUsername().equals(recipient)) {
            return "You cannot send a message to yourself";
        }

        Account receiver = getAccountByUsername(recipient);
        assert receiver != null;

        Message messageObject = new Message(sender.getUsername(), recipient, message);
        receiver.addMessage(messageObject);

        return "OK";
    }

    private String showInbox(Integer authToken) {
        if (!validateAuthToken(authToken)) {
            return "Invalid Auth Token";
        }

        Account account = getAccountByAuthToken(authToken);
        assert(account != null);

        StringBuilder inbox = new StringBuilder();

        int i = 0;
        for (Message message : account.getMessageBox()) {
            inbox.append(String.format("%d. from: %s%s<NL>", i, message.getSender(), (message.isRead() ? "" : "*")));
            i++;
        }

        return inbox.toString();
    }

    private String readMessage(Integer authToken, int messageId) {
        if (!validateAuthToken(authToken)) {
            return "Invalid Auth Token";
        }

        Account account = getAccountByAuthToken(authToken);
        assert(account != null);

        try {
            Message message = account.getMessageBox().get(messageId);
            message.setRead(true);

            return String.format("(%s) %s<NL>", message.getSender(), message.getBody());
        } catch (IndexOutOfBoundsException e) {
            return "Message ID does not exist";
        }
    }

    private String deleteMessage(Integer authToken, int messageId) {
        if (!validateAuthToken(authToken)) {
            return "Invalid Auth Token";
        }

        Account account = getAccountByAuthToken(authToken);
        assert(account != null);

        try {
            account.getMessageBox().remove(messageId);
            return "OK";
        } catch (IndexOutOfBoundsException e) {
            return "Message does not exist";
        }
    }
}
