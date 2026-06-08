package group11_Project.data;

import group11_Project.model.LedgerEntry;
import group11_Project.data.Database; // New
import java.util.ArrayList;
import java.util.List;

public class AppData {
	private String currentUser; // New
	private String currentRole; // New
	
    private final List<LedgerEntry> ledger = new ArrayList<>();
    private final List<String[]> users = new ArrayList<>();

    public String getCurrentUser() { // New (Start here)
	    return currentUser;
	}

	public void setCurrentUser(String currentUser) {
	    this.currentUser = currentUser;
	}

	public String getCurrentRole() {
	    return currentRole;
	}

	public void setCurrentRole(String currentRole) {
	    this.currentRole = currentRole;
	} // End here
    
    public AppData() {
    	Database.loadUsers(users); // New (Start here)
        Database.loadLedger(ledger);

        if(users.isEmpty()) {

            users.add(new String[]{
                "Administrator",
                "admin",
                "admin123",
                "Admin",
                "Full Access"
            });

            Database.saveUsers(users);
        } // End here
    }

    public List<LedgerEntry> getLedger() { return ledger; }
    public List<String[]> getUsers()     { return users; }
}
