package Server;

public enum Commands {
    CREATE_ACCOUNT(1),
    SHOW_ACCOUNTS(2),
    SEND_MESSAGE(3),
    SHOW_INBOX(4),
    READ_MESSAGE(5),
    DELETE_MESSAGE(6);

    public final int label;

    Commands(int label) {
        this.label = label;
    }
}
