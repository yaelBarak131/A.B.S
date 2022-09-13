package users;

import java.util.*;

/*
Adding and retrieving users is synchronized and in that manner - these actions are thread safe
Note that asking if a user exists (isUserExists) does not participate in the synchronization and it is the responsibility
of the user of this class to handle the synchronization of isUserExists with other methods here on it's own
 */
public class UserManager {

    private final Set<String> customersSet;
    private final List<String> admin;


    public UserManager() {
        customersSet = new HashSet<>();
        admin = new ArrayList<>();
    }

    public synchronized void addCustomer(String username) {
        customersSet.add(username);
    }
    // no need to remove customer so delete this method
    public synchronized void removeUser(String username) {
        customersSet.remove(username);
    }
    public synchronized void addAdmin(String username) {
        if (this.admin.size() == 0)
            this.admin.add(username);
    }


    public synchronized Set<String> getCustomers() {
        return Collections.unmodifiableSet(customersSet);
    }

    public synchronized List<String> getAdmin() {
        return Collections.unmodifiableList(admin);
    }

    public boolean doesCustomerExist(String username) {
        return customersSet.contains(username);
    }

    public boolean doesAdminExist(String username) {
        return this.admin.size() == 0;
    }

}
