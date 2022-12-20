package Server;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private Integer authToken;
    private ArrayList<Message> messageBox;

    public Account(String username) {
        this.username = username;
        this.messageBox = new ArrayList<Message>();
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getAuthToken() {
        return this.authToken;
    }

    public List<Message> getMessageBox() {
        return this.messageBox;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(Integer authToken) {
        this.authToken = authToken;
    }

    public void addMessage(Message message) {
        this.messageBox.add(message);
    }

    public void removeMessage(Message message) {
        this.messageBox.remove(message);
    }
}
