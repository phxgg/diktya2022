package Server;

import java.util.ArrayList;

/**
 * This class will hold any global variables that need to be accessed by multiple classes.
 */
public class ApplicationContext {
    private ArrayList<Account> accountManager;

    public ApplicationContext() {
        this.accountManager = new ArrayList<Account>();
    }

    public ArrayList<Account> getAccountManager() {
        return this.accountManager;
    }
}