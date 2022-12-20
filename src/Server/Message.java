package Server;

public class Message {
    private boolean isRead;
    private String sender;
    private String recipient;
    private String body;

    public Message(String sender, String receiver, String body) {
        this.isRead = false;
        this.sender = sender;
        this.recipient = receiver;
        this.body = body;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public String getSender() {
        return this.sender;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getBody() {
        return this.body;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
