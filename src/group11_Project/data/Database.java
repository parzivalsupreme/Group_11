package group11_Project.data;

import group11_Project.model.LedgerEntry;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class Database {

	private static final String LEDGER_FILE = "ledger.txt";

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