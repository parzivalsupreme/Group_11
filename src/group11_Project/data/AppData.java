package group11_Project.data;

import group11_Project.model.LedgerEntry;
import group11_Project.data.Database; // New
import java.util.ArrayList;
import java.util.List;

public class AppData {
    private final List<LedgerEntry> ledger = new ArrayList<>();
    private final List<String[]> users = new ArrayList<>();

    public AppData() {
        users.add(new String[]{"Admin User", "admin", "Admin", "Full Access"});
        Database.loadLedger(ledger); // For the Database
    }

    public List<LedgerEntry> getLedger() { return ledger; }
    public List<String[]> getUsers()     { return users; }
}
