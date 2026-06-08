package group11_Project.data;

import group11_Project.model.LedgerEntry;
import java.io.File; // New
import java.io.IOException; // New

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class Database {

	private static final String LEDGER_FILE = "ledger.txt";
	private static final String USERS_FILE = "users.txt"; // New

    // Save all ledger entries
    public static void saveLedger(List<LedgerEntry> ledger) {

        try (PrintWriter writer =
                new PrintWriter(new FileWriter(LEDGER_FILE))) {

            for (LedgerEntry entry : ledger) {

                writer.println(
                    entry.getDatetime() + "|" +
                    entry.getItem() + "|" +
                    entry.getQuantity() + "|" +
                    entry.getAmount() + "|" +
                    entry.getMethod() + "|" +
                    entry.getType() + "|" +
                    entry.getStatus()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void ensureFileExists(String fileName) { // New (Start Here)

        try {

            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end here
    
    // New (Start here)
    public static void saveUsers(List<String[]> users) {

        ensureFileExists(USERS_FILE);

        try(PrintWriter pw =
            new PrintWriter(
                new FileWriter(USERS_FILE))) {

            for(String[] user : users) {

                pw.println(
                    user[0] + "|" +
                    user[1] + "|" +
                    user[2] + "|" +
                    user[3]
                );
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    } // End here
    
    // New (Start here)
    public static void loadUsers(List<String[]> users) {

        users.clear();

        ensureFileExists(USERS_FILE);

        try(BufferedReader br =
            new BufferedReader(
                new FileReader(USERS_FILE))) {

            String line;

            while((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if(data.length == 4) {
                    users.add(data);
                }
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    } // New

    // Load all ledger entries
    public static void loadLedger(List<LedgerEntry> ledger) {

        ledger.clear();

        File file = new File(LEDGER_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                if (parts.length == 7) {

                    LedgerEntry entry =
                        new LedgerEntry(
                            LocalDateTime.parse(parts[0]),
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            parts[4],
                            parts[5],
                            parts[6]
                        );

                    ledger.add(entry);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}